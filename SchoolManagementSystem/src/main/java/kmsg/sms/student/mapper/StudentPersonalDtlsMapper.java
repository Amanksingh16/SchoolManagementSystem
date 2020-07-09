package kmsg.sms.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.student.model.Personal;

public class StudentPersonalDtlsMapper implements RowMapper<Personal> {

	@Override
	public Personal mapRow(ResultSet rs, int arg1) throws SQLException {
		Personal model = new Personal();
		model.setStudentId(    	rs.getInt("student_id"));
		model.setStudent(    	rs.getString("student"));
		model.setDob(    		rs.getString("dob"));
		model.setAddress1(		rs.getString("address1"));
		model.setAddress2( 		rs.getString("address2"));
		model.setStateId(   	rs.getInt("state_id"));
		model.setState(    		rs.getString("state"));
		model.setCityId(    	rs.getInt("city_id"));
		model.setCity(    		rs.getString("city"));
		model.setPin(    		rs.getString("pin"));
		model.setAdmissionDt(   rs.getString("admission_dt"));
		model.setEnrollmentNo(  rs.getString("enrollment_no"));

		return model;
	}

}
