package kmsg.sms.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.student.model.StudentSibling;

public class StudentSiblingsMapper implements RowMapper<StudentSibling> {

	@Override
	public StudentSibling mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentSibling model = new StudentSibling();
		
		model.setSiblingId(	rs.getInt("student_sibling_id"));
		model.setSibling(   rs.getString("sibling"));
		model.setGender(   	rs.getString("gender"));
		model.setAge(   	rs.getInt("age"));
		model.setClsId(   	rs.getInt("cls_id"));
		model.setCls(   	rs.getString("cls"));
		model.setSchool(   	rs.getString("school"));
		
		return model;
	}

}
