package kmsg.sms.teacher.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.common.TaskStatus;
import kmsg.sms.teacher.model.TasksModel;

public class TasksMapper implements RowMapper<TasksModel>
{
	@Override
	public TasksModel mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		TasksModel model = new TasksModel();
		model.setTaskId(rs.getInt("task_id"));
		model.setTask(rs.getString("task"));
		model.setTaskDate(rs.getString("task_date"));
		model.setDueDate(rs.getString("due_date"));
		model.setStatus(TaskStatus.getTaskStatus(rs.getInt("status")));
		model.setWingId(rs.getInt("wing_id"));
		model.setWing(rs.getString("wing"));
		return model;
	}
}
