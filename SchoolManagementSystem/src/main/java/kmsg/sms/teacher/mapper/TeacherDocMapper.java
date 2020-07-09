package kmsg.sms.teacher.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.teacher.model.TeacherDocModel;

public class TeacherDocMapper implements RowMapper<TeacherDocModel> {

	@Override
	public TeacherDocModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return new TeacherDocModel(rs.getInt("teacher_id"),rs.getInt("doc_type_id"),rs.getString("doc_path"));
	}

}
