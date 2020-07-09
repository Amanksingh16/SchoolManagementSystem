package kmsg.sms.student.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.Constants;
import kmsg.sms.student.daoint.StudentDao;
import kmsg.sms.student.mapper.StudentDtlsMapper;
import kmsg.sms.student.mapper.StudentMapper;
import kmsg.sms.student.mapper.StudentParentsMapper;
import kmsg.sms.student.mapper.StudentSiblingsMapper;
import kmsg.sms.student.model.SchoolStudent;
import kmsg.sms.student.model.Student;
import kmsg.sms.student.model.StudentDtls;
import kmsg.sms.student.model.StudentParents;
import kmsg.sms.student.model.StudentSibling;

@Repository
public class StudentDaoImpl implements StudentDao {

	@Autowired
	JdbcTemplate template;

	@Override
	public Map<String, Object> selectAllStudents(int schoolId) {
		Map<String,Object> data = new HashMap<>();
		
		String SQL =
			"SELECT"
				+ "  s.student_id"
				+ ", s.student"
				+ ", s.gender"
				+ ", s.age"
				+ ", c.class_id cls_id"
				+ ", c.class cls"
				+ ", s.section"
				+ ", s.blocked"
			+ " FROM org_student s"
			+ " LEFT JOIN mst_class c ON s.cls_id = c.class_id"
			+ " ORDER BY s.student" ;
				
		try {
			List<Student> lstStudents = template.query( SQL, new Object[]{}, new StudentMapper());
			data.put( Constants.STATUS, Constants.SUCCESS) ;
			data.put( "lstStudents", lstStudents);
			return data;
		}
		catch(Exception e) {
			e.printStackTrace();
			data.put( Constants.STATUS,Constants.FAILURE );
			data.put( Constants.MESSAGE, "DB Error occured in getting Students List. Contact system admin or try after sometime" ) ;
			return data;
		}
	}

	@Override
	public Map<String, Object> selectStudentDtls( SchoolStudent model) {
		Map<String,Object> data = new HashMap<>();
		
		String SQL =
			"SELECT"
				+ " s.student_id"
				+ ", s.student"
				+ ", DATE_FORMAT( s.dob, '%d-%m-%Y') dob"
				+ ", s.gender"
				+ ", s.address1"
				+ ", s.address2"
				+ ", s.state_id"
				+ ", st.state"
				+ ", s.city_id"
				+ ", ct.city"
				+ ", s.pin"
				+ ", DATE_FORMAT( s.admission_dt, '%d-%m-%Y') admission_dt"
				+ ", s.enrollment_no"
				+ ", s.blocked"
				
				+ ", s.med_height"
				+ ", s.med_weight"
				+ ", s.med_blood_group"
				+ ", s.med_diseases"
				+ ", s.med_eyes"
				+ ", s.med_ears"
				+ ", s.med_allergic_to"
				+ ", s.his_school"
				+ ", s.his_cls_id"
				+ ", c.class his_cls"
				+ ", s.his_city_id"
				+ ", hct.city his_city"
				+ ", s.his_state_id"
				+ ", hst.state his_state"
				+ ", s.his_result"
				+ ", s.his_board"
			+ " FROM org_student s"
			+ " LEFT JOIN mst_state hst ON s.his_state_id = hst.state_id"
			+ " LEFT JOIN mst_city hct ON s.his_city_id = hct.city_id"
			+ " LEFT JOIN mst_class c ON s.his_cls_id = c.class_id"
			+ " LEFT JOIN mst_city ct ON s.city_id = ct.city_id"
			+ " LEFT JOIN mst_state st ON s.state_id = st.state_id"
			+ " WHERE student_id = ?" ;

		try {
			StudentDtls studentDtls = template.queryForObject( SQL, new Object[]{model.getStudentId()}, new StudentDtlsMapper());
			data.put( Constants.STATUS, Constants.SUCCESS) ;
			data.put( "studentDtls", studentDtls);
			return data;
		}
		catch(Exception e) {
			e.printStackTrace();
			data.put( Constants.STATUS,Constants.FAILURE );
			data.put( Constants.MESSAGE, "DB Error occured in getting Students Personal data. Contact system admin or try after sometime" ) ;
			return data;
		}
	}
	
	@Override
	public Map<String, Object> selectStudentParents( SchoolStudent model) {
		Map<String,Object> data = new HashMap<>();
		
		String SQL =
			"SELECT"
				+ " s.f_name"
				+ ", s.f_address1"
				+ ", s.f_address2"
				+ ", s.f_state_id"
			    + ", stf.state f_state"
				+ ", s.f_city_id"
				+ ", ctf.city f_city"
				+ ", s.f_pin"
				+ ", s.f_e_mail"
				+ ", s.f_phone_no"
				
				+ ", s.m_name"
				+ ", s.m_address1"
				+ ", s.m_address2"
				+ ", s.m_state_id"
				+ ", stf.state m_state"
				+ ", s.m_city_id"
				+ ", ctf.city m_city"
				+ ", s.m_pin"
				+ ", s.m_e_mail"
				+ ", s.m_phone_no"

				+ ", s.g_name"
				+ ", s.g_address1"
				+ ", s.g_address2"
				+ ", s.g_state_id"
				+ ", stf.state g_state"
				+ ", s.g_city_id"
				+ ", ctf.city g_city"
				+ ", s.g_pin"
				+ ", s.g_e_mail"
				+ ", s.g_phone_no"

			+ " FROM org_student s"
			+ " LEFT JOIN mst_state stf ON s.f_state_id = stf.state_id"
			+ " LEFT JOIN mst_city ctf ON s.f_city_id = ctf.city_id"
			+ " LEFT JOIN mst_state stm ON s.m_state_id = stm.state_id"
			+ " LEFT JOIN mst_city ctm ON s.m_city_id = ctm.city_id"
			+ " LEFT JOIN mst_state stg ON s.g_state_id = stg.state_id"
			+ " LEFT JOIN mst_city ctg ON s.g_city_id = ctg.city_id"
			+ " WHERE student_id = ? ";
				
		try {
			StudentParents studentParents = template.queryForObject( SQL, new Object[]{model.getStudentId()}, new StudentParentsMapper());
			
			data.put( Constants.STATUS, Constants.SUCCESS) ;
			data.put( "parents", studentParents);
			return data;
		}
		catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			data.put( Constants.STATUS,Constants.FAILURE );
			data.put( Constants.MESSAGE, "Student Parents details not found. Contact system admin or try after sometime" ) ;
			return data;
		}
		catch(Exception e) {
			e.printStackTrace();
			data.put( Constants.STATUS,Constants.FAILURE );
			data.put( Constants.MESSAGE, "DB Error occured in getting Parents details. Contact system admin or try after sometime" ) ;
			return data;
		}
	}
	@Override
	public Map<String, Object> selectStudentSiblings( SchoolStudent model) {
		Map<String,Object> data = new HashMap<>();
		
		String SQL =
			"SELECT"
				+ "  ss.student_sibling_id"
				+ ", ss.sibling"
				+ ", ss.gender"
				+ ", ss.age"
				+ ", c.class_id cls_id"
				+ ", c.class cls"
				+ ", ss.school"
			+ " FROM org_student_sibling ss"
			+ " LEFT JOIN mst_class c ON ss.cls_id = c.class_id"
			+ " WHERE ss.student_id = ? "
			+ " ORDER BY age DESC" ;
				
		try {
			List<StudentSibling> lstSiblings= template.query( SQL, new Object[]{model.getStudentId()}, new StudentSiblingsMapper());
			data.put( Constants.STATUS, Constants.SUCCESS) ;
			data.put( "lstSiblings", lstSiblings );
			return data;
		}
		catch(Exception e) {
			e.printStackTrace();
			data.put( Constants.STATUS,Constants.FAILURE );
			data.put( Constants.MESSAGE, "DB Error occured in getting Students List. Contact system admin or try after sometime" ) ;
			return data;
		}
	}


}
