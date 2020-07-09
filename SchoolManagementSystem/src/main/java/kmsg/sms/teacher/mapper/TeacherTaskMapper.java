package kmsg.sms.teacher.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.common.TaskTeacherStatus;
import kmsg.sms.teacher.model.TeacherTaskModel;

public class TeacherTaskMapper implements RowMapper<TeacherTaskModel>
{
	@Override
	public TeacherTaskModel mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		TeacherTaskModel model = new TeacherTaskModel();
		model.setTaskId(rs.getInt("task_id"));
		model.setTeacherId(rs.getInt("teacher_id"));
		model.setTeacher(rs.getString("teacher"));
		model.setTeacherTaskId(rs.getInt("teacher_task_id"));
		model.setStatus(TaskTeacherStatus.getTaskStatus(rs.getInt("status")));
		return model;
	}
}
