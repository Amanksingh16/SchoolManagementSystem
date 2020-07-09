package kmsg.sms.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.student.model.Person;
import kmsg.sms.student.model.StudentParents;

public class StudentParentsMapper implements RowMapper<StudentParents> {

	@Override
	public StudentParents mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentParents model = new StudentParents();
		Person father = new Person();
		
		father.setName(    	rs.getString("f_name"));
		father.setAddress1(	rs.getString("f_address1"));
		father.setAddress2( rs.getString("f_address2"));
		father.setStateId(  rs.getInt("f_state_id"));
		father.setState(    rs.getString("f_state"));
		father.setCityId(   rs.getInt("f_city_id"));
		father.setCity(    	rs.getString("f_city"));
		father.setPin(    	rs.getString("f_pin"));
		father.setPhoneNo(  rs.getString("f_phone_no"));
		father.seteMail(  	rs.getString("f_e_mail"));

		Person mother = new Person();
		
		mother.setName(    	rs.getString("m_name"));
		mother.setAddress1(	rs.getString("m_address1"));
		mother.setAddress2( rs.getString("m_address2"));
		mother.setStateId(  rs.getInt("m_state_id"));
		mother.setState(    rs.getString("m_state"));
		mother.setCityId(   rs.getInt("m_city_id"));
		mother.setCity(    	rs.getString("m_city"));
		mother.setPin(    	rs.getString("m_pin"));
		mother.setPhoneNo(  rs.getString("m_phone_no"));
		mother.seteMail(  	rs.getString("m_e_mail"));

		Person guardian = new Person();
		
		guardian.setName(    	rs.getString("g_name"));
		guardian.setAddress1(	rs.getString("g_address1"));
		guardian.setAddress2( rs.getString("g_address2"));
		guardian.setStateId(  rs.getInt("g_state_id"));
		guardian.setState(    rs.getString("g_state"));
		guardian.setCityId(   rs.getInt("g_city_id"));
		guardian.setCity(    	rs.getString("g_city"));
		guardian.setPin(    	rs.getString("g_pin"));
		guardian.setPhoneNo(  rs.getString("g_phone_no"));
		guardian.seteMail(  	rs.getString("g_e_mail"));

		model.setFather(father);
		model.setMother(mother);
		model.setGuardian(guardian);
		
		return model;
	}

}
