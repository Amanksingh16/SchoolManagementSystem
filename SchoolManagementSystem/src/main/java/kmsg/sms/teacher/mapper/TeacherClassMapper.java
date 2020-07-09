package kmsg.sms.teacher.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.teacher.model.TeacherClassModel;

public class TeacherClassMapper implements RowMapper<TeacherClassModel> {

	@Override
	public TeacherClassModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return new TeacherClassModel(rs.getInt("teacher_id"),rs.getInt("class_id"));
	}

}
