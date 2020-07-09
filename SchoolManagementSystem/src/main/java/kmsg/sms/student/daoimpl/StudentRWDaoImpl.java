package kmsg.sms.student.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.student.daoint.StudentRWDao;
import kmsg.sms.student.model.Person;
import kmsg.sms.student.model.Personal;
import kmsg.sms.student.model.SchoolSibling;
import kmsg.sms.student.model.StudentHistory;
import kmsg.sms.student.model.StudentMedicalDtls;
import kmsg.sms.student.model.StudentParents;
import kmsg.sms.student.model.StudentSibling;

@Repository
public class StudentRWDaoImpl implements StudentRWDao, SMSLogger {

	@Autowired
	JdbcTemplate template;

	@Override
	public Map<String, Object> insertStudent( Personal personal){

		String SQL = "INSERT INTO org_student ("
				+ "student"
				+ ",gender"
				+ ",age"
				+ ",dob"
				+ ",address1"
				+ ",address2"
				+ ",state_id"
				+ ",city_id"
				+ ",pin"
				+ ",admission_dt"
				+ ",enrollment_no"
				+ ",blocked"
			+ " ) VALUES ("
				+ " ?, ?, ?, STR_TO_DATE( ?, '%d-%m-%Y')"
				+ ",?, ?, ?, ?"
				+ ",?, STR_TO_DATE(?, '%d-%m-%Y'), ?, ?"
			+ ");";
		
	    int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = template.update (
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement ps = conn.prepareStatement(
													SQL,
													Statement.RETURN_GENERATED_KEYS
												);
						int ctr=1;
						ps.setString(	ctr++, personal.getStudent());
						ps.setString(	ctr++, personal.getGender());
						ps.setInt(		ctr++, personal.getAge());
						ps.setString(	ctr++, personal.getDob());
						ps.setString(	ctr++, personal.getAddress1());
						ps.setString(	ctr++, personal.getAddress2());
						ps.setInt(		ctr++, personal.getStateId());
						ps.setInt(		ctr++, personal.getCityId());
						ps.setString(	ctr++, personal.getPin());
						ps.setString(	ctr++, personal.getAdmissionDt());
						ps.setString(	ctr++, personal.getEnrollmentNo());
						return ps ;
					}
				}, holder ) ;
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertSection:Exception occured in insert Student:" + personal.getStudent() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Student. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put( "studentId", holder.getKey().intValue());
			data.put(SvcStatus.MSG, "Student added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("insertSection:No student added:" + personal.getStudent());
			return SvcStatus.GET_FAILURE("Student could not be added. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> updateStudent( Personal personal){

		String SQL =
			"UPDATE org_student SET"
				+ " student=?"
				+ ",gender=?"
				+ ",age=?"
				+ ",dob=STR_TO_DATE( ?, '%d-%m-%Y')"
				+ ",address1=?"
				+ ",address2=?"
				+ ",state_id=?"
				+ ",city_id=?"
				+ ",pin=?"
				+ ",admission_dt=STR_TO_DATE( ?, '%d-%m-%Y')"
				+ ",enrollment_no=?"
				+ ",blocked=?"
			+ " WHERE student_id = ? " ;
		
	    int count = 0;
		try {
			 count = template.update (SQL, new Object[] {
						  	personal.getStudent()
						 ,	personal.getGender()
						 ,	personal.getAge()
						 ,	personal.getDob()
						 ,	personal.getAddress1()
						 , 	personal.getAddress2()
						 ,	personal.getStateId()
						 ,	personal.getCityId()
						 ,	personal.getPin()
						 ,	personal.getAdmissionDt()
						 ,	personal.getEnrollmentNo()
						 ,	personal.isBlocked()
						 ,  personal.getStudentId()
			 		} );
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("updateStudent: Exception occured:" + personal.getStudentId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in update Student. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Student updated");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updateStudent: No student updated:" + personal.getStudent());
			return SvcStatus.GET_FAILURE("Student could not be updated. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> updateStudentParents( StudentParents parents){

		String SQL =
			"UPDATE org_student SET"
					+ " f_name=?"
					+ ", f_address1=?"
					+ ", f_address2=?"
					+ ", f_state_id=?"
					+ ", f_city_id=?"
					+ ", f_pin=?"
					+ ", f_e_mail=?"
					+ ", f_phone_no=?"
					
					+ ", m_name=?"
					+ ", m_address1=?"
					+ ", m_address2=?"
					+ ", m_state_id=?"
					+ ", m_city_id=?"
					+ ", m_pin=?"
					+ ", m_e_mail=?"
					+ ", m_phone_no=?"

					+ ", g_name=?"
					+ ", g_address1=?"
					+ ", g_address2=?"
					+ ", g_state_id=?"
					+ ", g_city_id=?"
					+ ", g_pin=?"
					+ ", g_e_mail=?"
					+ ", g_phone_no=?"
			+ " WHERE student_id = ? " ;
		
		Person f = parents.getFather();
		Person m = parents.getMother();
		Person g = parents.getGuardian();
		
	    int count = 0;
		try {
			 count = template.update (SQL, new Object[] {
					 	 f.getName()
					 	,f.getAddress1()
					 	,f.getAddress2()
					 	,f.getStateId()
					 	,f.getCityId()
					 	,f.getPin()
					 	,f.geteMail()
					 	,f.getPhoneNo()

					 	,m.getName()
					 	,m.getAddress1()
					 	,m.getAddress2()
					 	,m.getStateId()
					 	,m.getCityId()
					 	,m.getPin()
					 	,m.geteMail()
					 	,m.getPhoneNo()

					 	,g.getName()
					 	,g.getAddress1()
					 	,g.getAddress2()
					 	,g.getStateId()
					 	,g.getCityId()
					 	,g.getPin()
					 	,g.geteMail()
					 	,g.getPhoneNo()

					 	, parents.getStudentId()
			 		} );
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("updateParents: Exception occured:" + parents.getStudentId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in update Student Parents. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Student parents updated");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updateParents:No student parent updated:" + parents.getStudentId());
			return SvcStatus.GET_FAILURE("Student parents could not be updated. Contact System Admin");
		}
	}
	
	@Override
	public Map<String, Object> insertSibling( StudentSibling sibling){

		String SQL = "INSERT INTO org_student_sibling ("
		+ " student_id"
		+ ", sibling"
		+ ", gender"
		+ ", age"
		+ ", cls_id"
		+ ", school"
		+ " ) VALUES ("
		+ " ?, ?, ?, ?"
		+ ",? ,?"
		+ ");";
	
		int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = template.update (
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(
						SQL,
						Statement.RETURN_GENERATED_KEYS
					);
					int ctr=1;
					ps.setInt( ctr++, sibling.getStudentId());
					ps.setString( ctr++, sibling.getSibling());
					ps.setString( ctr++, sibling.getGender());
					ps.setInt( ctr++, sibling.getAge());
					ps.setInt( ctr++, sibling.getClsId());
					ps.setString( ctr++, sibling.getSchool());
					return ps ;
				}
			}, holder ) ;
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertSibling: Exception occured in insert Student sibling:" + sibling.getSibling() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Student sibling. Contact System Admin");
		}
	
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put( "studentSiblingId", holder.getKey().intValue());
			data.put(SvcStatus.MSG, "Sibling added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("insertSibling: No student sibling added:" + sibling.getSibling());
			return SvcStatus.GET_FAILURE("Student sibling could not be added. Contact System Admin");
		}
	}
	@Override
	public Map<String, Object> updateSibling( StudentSibling sibling){

		String SQL =
				"UPDATE org_student_sibling SET"
					+ " sibling=?"
					+ ", gender=?"
					+ ", age=?"
					+ ", cls_id=?"
					+ ", school=?"
				+ "WHERE student_sibling_id = ? ";
		
	    int count = 0;
		try {
			count = template.update ( SQL, new Object[] {
					sibling.getSibling()
					, sibling.getGender()
					, sibling.getAge()
					, sibling.getClsId()
					, sibling.getSchool()
					, sibling.getSiblingId()
			}) ;
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("updateSibling: Exception occured in update Student sibling:" + sibling.getSiblingId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in update Student sibling. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Sibling updated");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updateSibling: No student sibling added:" + sibling.getSiblingId());
			return SvcStatus.GET_FAILURE("Student sibling could not be updated. Contact System Admin");
		}
	}
	
	@Override
	public Map<String, Object> deleteSibling(SchoolSibling model){

		String SQL = "DELETE FROM org_student_sibling WHERE student_sibling_id = ? ";
		
	    int count = 0;
		try {
			count = template.update ( SQL, new Object[] { model.getSiblingId() }) ;
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("deleteSibling: Exception occured in delete Student sibling:" + model.getSiblingId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in update Student sibling. Contact System Admin");
		}
		
		if (count == 1 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Sibling deleted");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("deleteSibling: No sibling deleted:" + model.getSiblingId());
			return SvcStatus.GET_FAILURE("Student sibling could not be deleted. Contact System Admin");
		}
	}

	
	@Override
	public Map<String, Object> updateStudentHistory( StudentHistory history){

		String SQL =
			"UPDATE org_student SET"
					+ " his_school=?"
					+ ", his_cls_id=?"
					+ ", his_city_id=?"
					+ ", his_state_id=?"
					+ ", his_result=?"
					+ ", his_board=?"
			+ " WHERE student_id = ? " ;
		
	    int count = 0;
		try {
			 count = template.update (SQL, new Object[] {
					 	 history.getSchool()
					 	 , history.getClsId()
					 	 , history.getCityId()
					 	 , history.getStateId()
					 	 , history.getResult()
					 	 , history.getBoard()
					 	 , history.getStudentId()
			 		} );
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("updateStudentHistory: Exception occured:" + history.getStudentId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in update Student History. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Student history updated");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updateStudentHistory:No student history updated:" + history.getStudentId());
			return SvcStatus.GET_FAILURE("Student history could not be updated. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> updateStudentMedical( StudentMedicalDtls medical){

		String SQL =
			"UPDATE org_student SET"
					+ " med_height=?"
					+ ", med_weight=?"
					+ ", med_blood_group=?"
					+ ", med_diseases=?"
					+ ", med_eyes=?"
					+ ", med_ears=?"
					+ ", med_allergic_to=?"
			+ " WHERE student_id = ? " ;
		
	    int count = 0;
		try {
			 count = template.update (SQL, new Object[] {
					 medical.getHeight()
				 	 , medical.getWeight()
				 	 , medical.getBloodGroup()
				 	 , medical.getDiseases()
				 	 , medical.getEyes()
				 	 , medical.getEars()
				 	 , medical.getAllergicTo()
				 	 , medical.getStudentId()
		 		} );
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("updateStudentMedical: Exception occured:" + medical.getStudentId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in update Student Medical. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Student medical updated");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updateStudentMedical: No student medicalupdated:" + medical.getStudentId());
			return SvcStatus.GET_FAILURE("Student medical could not be updated. Contact System Admin");
		}
	}

}
