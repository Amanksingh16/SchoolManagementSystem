package kmsg.sms.teacher.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.common.TaskStatus;
import kmsg.sms.common.TaskTeacherStatus;
import kmsg.sms.teacher.daoint.TasksDaoInt;
import kmsg.sms.teacher.mapper.TasksMapper;
import kmsg.sms.teacher.mapper.TeacherTaskMapper;
import kmsg.sms.teacher.model.TasksModel;
import kmsg.sms.teacher.model.TeacherTaskModel;

@Repository
public class TasksDaoImpl implements TasksDaoInt, SMSLogger
{
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public Map<String, Object> insertNewTask(TasksModel model) {
		final String SQL = "INSERT INTO "+schoolId+"_tasks (task,task_date,due_date,status,wing_id) VALUES(?,STR_TO_DATE(?,'%d-%m-%Y'),STR_TO_DATE(?,'%d-%m-%Y'),?,?)";

	    int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			 count = template.update (
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement ps = conn.prepareStatement(
													SQL,
													Statement.RETURN_GENERATED_KEYS
																	);
						ps.setString (	1, model.getTask());
						ps.setString (	2, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
						ps.setString (	3, model.getDueDate());
						ps.setInt    (	4, TaskStatus.PENDING);
						ps.setInt	 (	5, model.getWingId());
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertTask : Duplicate Key insertTask:" + model.getTask());
			return SvcStatus.GET_FAILURE("Task already exist");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertTask :Exception occured in insertTask:" + model.getTask() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Task. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put("taskId", holder.getKey().intValue());
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Task could not be added. Contact System Admin");
		}

	}

	public int insertTeacherTask(TeacherTaskModel teacherTaskModel) 
	{
		String sql = " INSERT INTO "+schoolId+"_teacher_tasks (task_id, teacher_id, status) VALUES(?,?,?)";
		return template.update(sql, new Object[] {teacherTaskModel.getTaskId(), teacherTaskModel.getTeacherId(), TaskTeacherStatus.ASSIGNED});
	}

	public Map<String, Object> updateTask(TasksModel model) 
	{
		final String SQL = "UPDATE "
				+ schoolId+"_tasks SET task = ?"
				+ ", due_date = STR_TO_DATE(?,'%d-%m-%Y')"
				+ ", wing_id = ?"
				+ " WHERE task_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
					 model.getTask(),
					 model.getDueDate(),
					 model.getWingId(),
					 model.getTaskId()
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateTask : Duplicate Key for this task:" + model.getTask());
			return SvcStatus.GET_FAILURE("Task already exist");
		}
		catch(Exception e) {
			logger.error("updateTask :Exception occured in updateTask:" + model.getTask() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating task. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Task Updated");
			return data;
		}
		else {
			logger.error("updateTask : Failed to update Task :" +  model.getTask());
			return SvcStatus.GET_FAILURE("Task could not be updated. Contact System Admin");
		}

	}

	public int deleteTeacherTasks(int taskId) {
		String sql = " DELETE FROM "+schoolId+"_teacher_tasks WHERE task_id = ? AND status = ?";
		return template.update(sql, new Object[] {taskId, TaskTeacherStatus.ASSIGNED});
	}

	public Map<String, Object> listTasks() 
	{
		Map<String, Object> map = new HashMap<>();
		
		String SQL = " SELECT task_id,"
				+ " DATE_FORMAT(task_date,'%d-%m-%Y') AS task_date,"
				+ " DATE_FORMAT(due_date,'%d-%m-%Y') AS due_date,"
				+ " task,"
				+ " status,"
				+ " w.wing_id,"
				+ " w.wing"
				+ " FROM "+schoolId+"_tasks t"
				+ " JOIN "+schoolId+"_wings w ON t.wing_id = w.wing_id";
		
		List<TasksModel> list = new ArrayList<>();
		try {
			list = template.query(SQL ,new TasksMapper());
			if(list.size() == 0)
			{
				logger.error("selectTasks: No Tasks Found");
				map.put(SvcStatus.MSG, "No Tasks Found");
				map.put(SvcStatus.STATUS, SvcStatus.FAILURE);
				return map;
			}
		}
		catch(Exception e)
		{
			logger.error("selectTasks: Error Occurred "+e);
			map.put(SvcStatus.MSG, "Error occured in getting tasks");
			map.put(SvcStatus.STATUS, SvcStatus.FAILURE);
			return map;
		}
		map.put("lstTasks",list);
		map.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		return map;
	}
	
	public Map<String, Object> listTaskTeachers(int taskId) 
	{
		Map<String, Object> map = new HashMap<>();
		
		String SQL = " SELECT teacher_task_id,"
				+ " task_id,"
				+ " t.teacher_id,"
				+ " t.teacher,"
				+ " status"
				+ " FROM "+schoolId+"_teacher_tasks tt"
				+ " JOIN teacher t ON t.teacher_id = tt.teacher_id"
				+ " WHERE tt.task_id = ?";
		
		List<TeacherTaskModel> list = new ArrayList<>();
		try {
			list = template.query(SQL ,new Object[] {taskId},new TeacherTaskMapper());
			if(list.size() == 0)
			{
				logger.error("selectTeacherTasks: No Teachers Found for this task");
				map.put(SvcStatus.MSG, "No Teachers Found for this task");
				map.put(SvcStatus.STATUS, SvcStatus.FAILURE);
				return map;
			}
		}
		catch(Exception e)
		{
			logger.error("selectTeacherTasks: Error Occurred "+e);
			map.put(SvcStatus.MSG, "Error occured in getting teacher tasks");
			map.put(SvcStatus.STATUS, SvcStatus.FAILURE);
			return map;
		}
		map.put("lstTeacherTasks",list);
		map.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		return map;
	}
}
