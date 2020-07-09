package kmsg.sms.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.student.model.Personal;
import kmsg.sms.student.model.StudentDtls;
import kmsg.sms.student.model.StudentHistory;
import kmsg.sms.student.model.StudentMedicalDtls;

public class StudentDtlsMapper implements RowMapper<StudentDtls> {

	@Override
	public StudentDtls mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentDtls model = new StudentDtls();
		
		Personal personal = new Personal();
		personal.setStudentId(    	rs.getInt("student_id"));
		personal.setStudent(    	rs.getString("student"));
		personal.setDob(    		rs.getString("dob"));
		personal.setGender(    		rs.getString("gender"));
		personal.setAddress1(		rs.getString("address1"));
		personal.setAddress2( 		rs.getString("address2"));
		personal.setStateId(   		rs.getInt("state_id"));
		personal.setState(    		rs.getString("state"));
		personal.setCityId(    		rs.getInt("city_id"));
		personal.setCity(    		rs.getString("city"));
		personal.setPin(    		rs.getString("pin"));
		personal.setAdmissionDt(   	rs.getString("admission_dt"));
		personal.setEnrollmentNo(  	rs.getString("enrollment_no"));
		personal.setBlocked(  		rs.getBoolean("blocked"));

		StudentHistory history = new StudentHistory();
		history.setSchool(rs.getString("his_school"));
		history.setClsId( rs.getInt("his_cls_id"));
		history.setCls(rs.getString("his_cls"));
		history.setResult(rs.getString("his_result"));
		history.setCityId(rs.getInt("his_city_id"));
		history.setCity(rs.getString("his_city"));
		history.setStateId(rs.getInt("his_state_id"));
		history.setState(rs.getString("his_state"));
		history.setBoard(rs.getString("his_board"));

		StudentMedicalDtls medical = new StudentMedicalDtls();
		medical.setHeight(		rs.getFloat("med_height"));
		medical.setWeight(		rs.getFloat("med_weight"));
		medical.setBloodGroup(	rs.getString("med_blood_group"));
		medical.setDiseases(	rs.getString("med_diseases"));
		medical.setEyes(		rs.getString("med_eyes"));
		medical.setEars(		rs.getString("med_ears"));
		medical.setAllergicTo(	rs.getString("med_allergic_to"));
		
		model.setPersonal(personal);
		model.setHistory(history);
		model.setMedical(medical);
		
		return model;
	}

}
