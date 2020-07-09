package kmsg.sms.student.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SvcStatus;
import kmsg.sms.student.daoimpl.StudentRWDaoImpl;
import kmsg.sms.student.model.Personal;
import kmsg.sms.student.model.SchoolSibling;
import kmsg.sms.student.model.StudentHistory;
import kmsg.sms.student.model.StudentMedicalDtls;
import kmsg.sms.student.model.StudentParents;
import kmsg.sms.student.model.StudentSibling;

@Component
public class StudentRWAdapter {

	@Autowired
	StudentRWDaoImpl dao;

	public Map<String, Object> saveStudent(String strPersonal) {
		Map<String,Object> map = new HashMap<>();
		Personal model = new Personal();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(strPersonal, Personal.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving Personal Details");
			return map;
		}
		
		if(model.getStudentId() == 0) {
			return dao.insertStudent( model );
		}
		else {
			return dao.updateStudent( model );
		}
	}

	public Map<String, Object> saveStudentParents(String strStudentParents) {
		Map<String,Object> map = new HashMap<>();
		StudentParents model = new StudentParents();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(strStudentParents, StudentParents.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving Parents Details");
			return map;
		}
		return dao.updateStudentParents( model );
	}

	public Map<String, Object> saveStudentSibling(String strSibling) {
		Map<String,Object> map = new HashMap<>();
		StudentSibling model = new StudentSibling();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strSibling, StudentSibling.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving Sibling Section");
			return map;
		}
		
		if(model.getSiblingId() == 0) {
			return dao.insertSibling( model );
		}
		else {
			return dao.updateSibling( model );
		}
	}

	public Map<String, Object> removeStudentSibling(String strSchoolSibling) {
		Map<String,Object> map = new HashMap<>();
		SchoolSibling model = new SchoolSibling();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strSchoolSibling, SchoolSibling.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS, SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in removing sibling parameter");
			return map;
		}


		return dao.deleteSibling( model);
	}

	public Map<String, Object> saveStudentHistory(String strHistory) {
		Map<String,Object> map = new HashMap<>();
		StudentHistory model = new StudentHistory();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strHistory, StudentHistory.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving history Section");
			return map;
		}
		return dao.updateStudentHistory( model );
	}

	public Map<String, Object> saveStudentMedical(String strMedical) {
		Map<String,Object> map = new HashMap<>();
		StudentMedicalDtls model = new StudentMedicalDtls();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strMedical, StudentMedicalDtls.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving medical Section");
			return map;
		}
		return dao.updateStudentMedical( model );
	}

}
