package kmsg.sms.student.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SvcStatus;
import kmsg.sms.student.daoimpl.StudentDaoImpl;
import kmsg.sms.student.model.SchoolStudent;

@Component
public class StudentAdapter {

	@Autowired
	StudentDaoImpl dao;

	public Map<String, Object> getAllStudents(int schoolId) {
		return dao.selectAllStudents(schoolId);
	}

	public Map<String, Object> getDtls( String strSchoolStudent) {
		Map<String,Object> map = new HashMap<>();
		SchoolStudent model = new SchoolStudent();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strSchoolStudent, SchoolStudent.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS, SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in parameter");
			return map;
		}

		return dao.selectStudentDtls(model);
	}

	public Map<String, Object> getParents(String strSchoolStudent) {
		Map<String,Object> map = new HashMap<>();
		SchoolStudent model = new SchoolStudent();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strSchoolStudent, SchoolStudent.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS, SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in parameter");
			return map;
		}

		return dao.selectStudentParents(model);
	}

	public Map<String, Object> getSiblings(String strSchoolStudent) {
		Map<String,Object> map = new HashMap<>();
		SchoolStudent model = new SchoolStudent();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strSchoolStudent, SchoolStudent.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS, SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in parameter");
			return map;
		}

		return dao.selectStudentSiblings( model);
	}
}
