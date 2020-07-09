package kmsg.sms.teacher.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.teacher.model.ManageTeacher;

public class TeacherMapper implements RowMapper<ManageTeacher> {

	@Override
	public ManageTeacher mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new ManageTeacher(rs.getInt("teacher_id"),rs.getString("teacher"),rs.getString("gender"),rs.getString("birth_dt"),rs.getString("joining_dt"),
				rs.getBoolean("married"), rs.getInt("experience"), rs.getString("address_1"), rs.getString("address_2"), rs.getInt("city_id"), 
				rs.getInt("state_id"), rs.getString("mobile_no"),rs.getString("email_id"), rs.getString("emergency_phone_no")); 
	}

}
