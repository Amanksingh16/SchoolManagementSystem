package kmsg.sms.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.student.model.Student;

public class StudentMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int arg1) throws SQLException {
		Student model = new Student();
		model.setStudentId(	rs.getInt("student_id"));
		model.setStudent(   rs.getString("student"));
		model.setGender(    rs.getString("gender"));
		model.setAge(    	rs.getInt("age"));
		model.setClsId(    	rs.getInt("cls_id"));
		model.setCls(    	rs.getString("cls"));
		model.setSection(   rs.getString("section"));
		model.setBlocked(   rs.getBoolean("blocked"));

		return model;
	}

}
