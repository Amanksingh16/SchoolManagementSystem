package kmsg.sms.teacher.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.common.Util;
import kmsg.sms.teacher.daoint.TeacherDaoInt;
import kmsg.sms.teacher.mapper.TeacherDocMapper;
import kmsg.sms.teacher.mapper.TeacherMapper;
import kmsg.sms.teacher.model.ManageTeacher;
import kmsg.sms.teacher.model.SchoolTeacherClassModel;
import kmsg.sms.teacher.model.SchoolTeacherClassSubjectModel;
import kmsg.sms.teacher.model.TeacherClassModel;
import kmsg.sms.teacher.model.TeacherClassSubjectModel;
import kmsg.sms.teacher.model.TeacherDocModel;
import kmsg.sms.teacher.model.TeacherEducationModel;
import kmsg.sms.teacher.model.TeacherRole;

@Repository
public class TeacherDaoImpl implements TeacherDaoInt, SMSLogger{

	private int schoolId;
	
	@Autowired
	JdbcTemplate template;
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	

	@Override
	public Map<String, Object> selectTeacherRoles(int teacherId) {
		final String SQL = 
				"SELECT"
					+ " tr.teacher_role_id"
					+ ", tr.teacher_id"
					+ ", r.role_id"
					+ ", r.role_name"
					+ ", r.role_desc"
				+ " FROM " + schoolId + "_teacher_role t JOIN mst_roles r ON r.role_id = tr.role_id"
				+ " WHERE teacher_id = ?"
				+ " ORDER BY r.role_name" ;
		
		List<ManageTeacher> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {schoolId}, new TeacherMapper());
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectTeacher: Exception in selecting Teacher " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Teacher. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstTeacherRole",  list );
		return result ;	
	}
	
	@Override
	public Map<String, Object> insertTeacherRole(TeacherRole model) {

		final String SQL =
				"INSERT INTO " + schoolId+"_teacher_role("
					+ "teacher_id"
					+ ",role_id"
					+ ",assigned_dttm"
				+ ") VALUES (?,?,STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')";
	
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
						ps.setInt( ctr++, model.getTeacherId());
						ps.setInt( ctr++, model.getRoleId());
						ps.setString( ctr++, Util.Now());
						
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertTeacherRole: Duplicate Role for Teacher:" + model.getTeacherId());
			return SvcStatus.GET_FAILURE("Role already exist" +  model.getTeacherId());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertTeacherRole: Exception occured in insertTeacherRole:" + model.getTeacherId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Role for teacher. Contact System Admin");
		}
		
		if (count == 1 ) {
			Map<String, Object> data = new HashMap<>();
			data.put("teacherRoleId", holder.getKey().intValue());
			data.put(SvcStatus.MSG, "Role added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Teacher Role could not be added. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> saveTeacher(ManageTeacher model) {
		final String SQL = "INSERT INTO "
				+ " teacher (teacher_id,teacher,gender,birth_dt,joining_dt,married,experience,address_1,address_2,city_id,state_id,email_id,mobile_no,password,salt,emergency_phone_no,school_id) VALUES ("
						+ "?,?,?,STR_TO_DATE(?,'%d-%m-%Y'),STR_TO_DATE(?,'%d-%m-%Y'),?,?,?,?,?,?,?,?,?,?,?,?)";

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
						ps.setInt	 (	1, model.getTeacherId()			);
						ps.setString (	2, model.getTeacher()			);
						ps.setString (	3, model.getGender()			);
						ps.setString (	4, model.getDob()				);
						ps.setString (	5, model.getDoj()				);
						ps.setBoolean(	6, model.isMarried()			);
						ps.setInt	 (	7, model.getExperience()		);
						ps.setString (	8, model.getAddress1()			);
						ps.setString (	9, model.getAddress2()			);
						ps.setInt	 (	10, model.getCityId()			);
						ps.setInt	 (	11, model.getStateId()			);
						ps.setString (	12, model.getEmail()			);
						ps.setString (	13, model.getMobileNo()			);
						ps.setString (	14, null						);
						ps.setString (	15, null						);
						ps.setString (	16, model.getEmergencyContactNo());
						ps.setInt	 (	17, schoolId	);
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertTeacher : Duplicate Key insertTeacher:" + model.getTeacherId());
			return SvcStatus.GET_FAILURE("Teacher already exist for this school");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertTeacher :Exception occured in insertTeacher:" + model.getTeacherId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Teacher. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Teacher Added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Teacher could not be added. Contact System Admin");
		}

	}
	
	@Override
	public Map<String, Object> updateTeacher(ManageTeacher model) {

			final String SQL = "UPDATE teacher SET teacher = ?"
					+ ", gender = ?"
					+ ", birth_dt = ?"
					+ ", joining_dt = ?"
					+ ", married = ?"
					+ ", experience = ?"
					+ ", address_1 = ?"
					+ ", address_2 = ?"
					+ ", city_id = ?"
					+ ", state_id = ?"
					+ ", email_id = ?"
					+ ", mobile_no = ?"
					+ ", emergency_phone_no = ?"
					+ " WHERE teacher_id = ? AND school_id = ?";

		    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
						 model.getTeacher(),
						 model.getGender(),
						 model.getDob(),
						 model.getDoj(),
						 model.isMarried(),
						 model.getExperience(),
						 model.getAddress1(),
						 model.getAddress2(),
						 model.getCityId(),
						 model.getStateId(),
						 model.getEmail(),
						 model.getMobileNo(),
						 model.getEmergencyContactNo(),
						 model.getTeacherId(),
						 schoolId
					} );
			}
			catch(DuplicateKeyException e) {
				logger.error("updateTeacher : Duplicate Key for this Teacher:" + model.getTeacher());
				return SvcStatus.GET_FAILURE("Teacher already exist for this class");
			}
			catch(Exception e) {
				logger.error("updateTeacher :Exception occured in updateTeacher:" + model.getTeacher() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating Teacher. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Teacher Updated");
				return data;
			}
			else {
				logger.error("updateTeacher : Failed to update Section :" +  model.getTeacher());
				return SvcStatus.GET_FAILURE("Teacher could not be updated. Contact System Admin");
			}


	}
	
	@Override
	public Map<String, Object> getTeacherList() {
		final String SQL = 
				"SELECT"
				+ " teacher_id"
				+ ", teacher"
				+ ", gender"
				+ ", birth_dt"
				+ ", joining_dt"
				+ ", married"
				+ ", experience"
				+ ", address_1"
				+ ", address_2"
				+ ", city_id"
				+ ", state_id"
				+ ", email_id"
				+ ", mobile_no"
				+ ", emergency_phone_no"
				+ " FROM teacher WHERE school_id = ?" ;
		
		List<ManageTeacher> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {schoolId}, new TeacherMapper());
			if(list.size() == 0)
			{
				logger.error("selectTeacher: No Teacher found");
				return SvcStatus.GET_FAILURE("No Teacher found. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectTeacher: Exception in selecting Teacher " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Teacher. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstTeacher",  list );
		return result ;	
	
	}
	
	@Override
	public ManageTeacher getTeacher(int teacherId) {

		final String SQL = 
				"SELECT"
				+ " teacher_id"
				+ ", teacher"
				+ ", gender"
				+ ", birth_dt"
				+ ", joining_dt"
				+ ", married"
				+ ", experience"
				+ ", address_1"
				+ ", address_2"
				+ ", city_id"
				+ ", state_id"
				+ ", email_id"
				+ ", mobile_no"
				+ ", emergency_phone_no"
				+ " FROM teacher"
				+ " WHERE teacher_id = ? AND school_id = ?" ;
		
		try {
			return template.queryForObject(SQL, new Object[] {teacherId, schoolId}, new TeacherMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}	
	}

	@Override
	public Map<String, Object> getTeacherDocsList(int teacherId) {

			final String SQL = 
				" SELECT teacher_id"
				+ ", doc_type_id"
				+ ", doc_path"
				+ " FROM "
				+ schoolId+"_teacher_doc"
				+ " WHERE teacher_id = ?";
		
		List<TeacherDocModel> list = new ArrayList<>();
		try {
	
			 
			list = template.query(SQL, new Object[] {teacherId},new TeacherDocMapper());
		} 
		catch (EmptyResultDataAccessException e) {
			System.out.println(e);
			logger.error("selectTeacherDoc: No TeacherDoc found for teacherId:"+teacherId);
			return SvcStatus.GET_FAILURE("No TeacherDoc found. Contact System admin");
		}
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectTeacherDoc: Exception in selecting TeacherDoc " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting TeacherDoc. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstTeacherDoc",  list );
		return result ;	
	}
	@Override
	public Map<String, Object> getTeacherClassList(int teacherId) {
			
			final String SQL = 
				" SELECT class_id"
				+ " FROM "
				+ schoolId+"_teacher_class"
				+ " WHERE teacher_id = ?";
		
		List<Integer> list = new ArrayList<>();
		try {
	
			 
			list = template.queryForList(SQL, new Object[] {teacherId}, Integer.class);
		} 
		catch (EmptyResultDataAccessException e) {
			System.out.println(e);
			logger.error("selectTeacherClass: No TeacherClass found for teacherId:"+teacherId);
			return null;
		}
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectTeacherDoc: Exception in selecting TeacherClass " + e);
			return null;
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstTeacherClass",  list );
		return result ;	
	
	}
	
	@Override
	public Map<String, Object> getTeacherSubjectList(SchoolTeacherClassModel model) 
	{
		int teacherId = model.getTeacherId();
		int classId = model.getClassId();
		
		final String SQL = 
				"SELECT"
			+ " tcs.subject_id"
			+ " FROM " 
			+ schoolId +"_teacher_class_subject tcs"
			+ " JOIN "
			+ schoolId +"_teacher_class tc ON tc.teacher_class_id=tcs.teacher_class_id"
			+ " WHERE teacher_id = ? AND class_id=?";
	
		List<Integer> list = new ArrayList<>();
		try {
		
			 
			list = template.queryForList(SQL, new Object[] {teacherId,classId},Integer.class);
		} 
		catch (EmptyResultDataAccessException e) {
			System.out.println(e);
			logger.error("selectTeacherSubject: No Subject found for teacherId:"+teacherId+", classId:"+classId);
			return SvcStatus.GET_FAILURE("No Subject found. Contact System admin");
		}
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectTeacherSubject: Exception in selecting TeacherSubject " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting TeacherSubject. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstSubject",  list );
		return result ;	
	
	}
	
	@Override
	public Map<String, Object> saveDocument(TeacherDocModel model) {

		final String SQL = "INSERT INTO " + schoolId+"_teacher_doc (teacher_id,doc_type_id,doc_path) VALUES (?,?,?)";
	
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
						ps.setInt( 1, model.getTeacherId());
						ps.setInt( 2, model.getDocTypeId());
						ps.setString( 3, model.getDocPath());
						
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertDocument : Duplicate Key insertDoc:" + model.getTeacherId());
			return SvcStatus.GET_FAILURE("Document already exist" +  model.getTeacherId());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertDocument :Exception occured in insertDocument:" + model.getTeacherId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Document. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put("teacherDocId", holder.getKey().intValue());
			data.put(SvcStatus.MSG, "New Document Added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Document could not be added. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> deleteTeacherRole(TeacherRole model) {

		final String SQL =
				"DELETE FROM " + schoolId+"_teacher_role "
				+ " WHERE teacher_role_id = ? ";
		int count = 0;
		try {
			 count = template.update ( SQL, new Object[] {model.getTeacherRoleId()});
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertTeacherRole: Exception occured in insertTeacherRole:" + model.getTeacherId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Role for teacher. Contact System Admin");
		}
		
		if (count == 1 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Role removed from Teacher");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Teacher Role could not be removed. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> updateDocument(TeacherDocModel model) {
		
		final String SQL = "UPDATE "+schoolId+"_teacher_doc SET"
				+ " doc_path = CASE WHEN '"+model.getDocPath()+"' <> 'null' THEN ? else doc_path END"
				+ " WHERE teacher_id = ? AND doc_type_id =?";
	
	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
						model.getDocPath(),
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateDocument : Duplicate Key for this Document:" + model.getDocPath());
			return SvcStatus.GET_FAILURE("Document already exist");
		}
		catch(Exception e) {
			logger.error("updateDocument :Exception occured in updateDocument:" + model.getDocPath() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating Document. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Document Updated");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updateDocument : Failed to update Document :" +  model.getDocPath());
			return SvcStatus.GET_FAILURE("Document could not be updated. Contact System Admin");
		}
	
	}
	
	@Override
	public Map<String, Object> getTeacherEducationList(int teacherId) {

		final String SQL = 
			" SELECT education_id"
			+ " FROM "
			+ schoolId+"_teacher_education"
			+ " WHERE teacher_id = ?";
	
	List<Integer> list = new ArrayList<>();
	try {

		 
		list = template.queryForList(SQL, new Object[] {teacherId}, Integer.class);
	} 
	catch (EmptyResultDataAccessException e) {
		System.out.println(e);
		logger.error("selectTeacherEducation: No TeacherEducation found for teacherId:"+teacherId);
		return null;
	}
	catch (Exception e) {
		System.out.println(e);
		logger.error("selectTeacherEducation: Exception in selecting TeacherEducation " + e);
		return null;
	}
	
	Map<String, Object> result = new HashMap<>();
	result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
	result.put("lstTeacherEducation",  list );
	return result ;	
	}
	
	@Override
	public Map<String, Object> saveTeacherEducation(TeacherEducationModel model) {
		final String SQL = "INSERT INTO "
				+ schoolId+"_teacher_education (teacher_id,education_id) VALUES ("
						+ "?,?)";

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
						ps.setInt	 (	1, model.getTeacherId()			);
						ps.setInt	 (	2, model.getEducationId()		);
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertTeacherEducation : Duplicate Key insertTeacherEducation for teacherId:" + model.getTeacherId() + " educationId:"+model.getEducationId());
			return SvcStatus.GET_FAILURE("TeacherEducation already exist for this school");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertTeacherEducation :Exception occured in insertTeacherEducation for teacherId:" + model.getTeacherId() + " educationId: "+ model.getEducationId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding TeacherEducation. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Education Added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("TeacherEducation could not be added. Contact System Admin");
		}

	}
	
	@Override
	public Map<String, Object> updateTeacherEducation(TeacherEducationModel model) {

		final String SQL = "UPDATE "
				+ schoolId+"_teacher_education SET teacher_id = ?"
				+ ", education_id = ?"
				+ " WHERE teacher_education_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
					 model.getTeacherId(),
					 model.getEducationId(),
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateTeacherEducation : Duplicate Key for this TeacherEducation:" + model.getTeacherId());
			return SvcStatus.GET_FAILURE("TeacherEducation already exist for this teacher");
		}
		catch(Exception e) {
			logger.error("updateTeacherEducation :Exception occured in updateTeacherEducation:" + model.getTeacherId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating TeacherEducation. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Education Updated");
			return data;
		}
		else {
			logger.error("updateTeacherEducation : Failed to update TeacherEducation :" +  model.getTeacherEducationId());
			return SvcStatus.GET_FAILURE("TeacherEducation could not be updated. Contact System Admin");
		}

	}

/*-----------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public Map<String, Object> saveTeacherClass(TeacherClassModel model) {
		final String SQL = "INSERT INTO "
				+ schoolId+"_teacher_class (teacher_id,class_id) VALUES ("
						+ "?,?)";

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
						ps.setInt	 (	1, model.getTeacherId()		);
						ps.setInt	 (	2, model.getClassId()		);
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertTeacherClass : Duplicate Key insertTeacherClass:" + model.getTeacherId());
			return SvcStatus.GET_FAILURE("TeacherClass already exist for this school");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertTeacherClass :Exception occured in insertTeacherClass:" + model.getTeacherId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding TeacherClass. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Class Added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("TeacherClass could not be added. Contact System Admin");
		}

	}
	
	@Override
	public Map<String, Object> updateTeacherClass(TeacherClassModel model) {

		final String SQL = "UPDATE "
				+ schoolId+"_teacher_class SET teacher_id = ?"
				+ ", class_id = ?"
				+ " WHERE teacher_class_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
					 model.getTeacherId(),
					 model.getClassId(),
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateTeacherClass : Duplicate Key for this TeacherClass:" + model.getTeacherClassId());
			return SvcStatus.GET_FAILURE("TeacherClass already exist for this teacher");
		}
		catch(Exception e) {
			logger.error("updateTeacherClass :Exception occured in updateTeacherClass:" + model.getTeacherClassId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating TeacherClass. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Class Updated");
			return data;
		}
		else {
			logger.error("updateTeacherClass : Failed to update TeacherClass :" +  model.getTeacherClassId());
			return SvcStatus.GET_FAILURE("TeacherClass could not be updated. Contact System Admin");
		}

	}

	@Override
	public Map<String, Object> saveTeacherClassSubject(TeacherClassSubjectModel model) {
		final String SQL = "INSERT INTO "
				+ schoolId+"_teacher_class_subject (teacher_id,class_id,subject_id) VALUES ("
						+ "?,?,?)";

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
						ps.setInt	 (	1, model.getTeacherId()		);
						ps.setInt	 (	2, model.getClassId()		);
						ps.setInt	 (	3, model.getSubjectId()		);
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertTeacherClassSubject : Duplicate Key insertTeacherClassSubject:" + model.getTeacherId());
			return SvcStatus.GET_FAILURE("TeacherClassSubject already exist for this school");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertTeacherClassSubject :Exception occured in insertTeacherClassSubject:" + model.getTeacherId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding TeacherClassSubject. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Subject Added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("TeacherClassSubject could not be added. Contact System Admin");
		}


	}

	@Override
	public Map<String, Object> updateTeacherClassSubject(TeacherClassSubjectModel model) {
		final String SQL = "UPDATE "
				+ schoolId+"_teacher_class_subject SET teacher_id = ?"
				+ ", class_id = ?"
				+ ", subject_id = ?"
				+ " WHERE teacher_class_subject_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
					 model.getTeacherId(),
					 model.getClassId(),
					 model.getSubjectId(),
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateTeacherClassSubject : Duplicate Key for this TeacherClassSubject:" + model.getTeacherClassSubjectId());
			return SvcStatus.GET_FAILURE("TeacherClassSubject already exist for this teacher");
		}
		catch(Exception e) {
			logger.error("updateTeacherClassSubject :Exception occured in updateTeacherClassSubject:" + model.getTeacherClassSubjectId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating TeacherClassSubject. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Subject Updated");
			return data;
		}
		else {
			logger.error("updateTeacherClassSubject : Failed to update TeacherClassSubject :" +  model.getTeacherClassSubjectId());
			return SvcStatus.GET_FAILURE("TeacherClassSubject could not be updated. Contact System Admin");
		}	
	}

	@Override
	public String getTeacherDocTypePath(TeacherDocModel model) {
		final String SQL = "SELECT doc_path FROM "
				+ schoolId+"_teacher_doc"
						+ " WHERE doc_type_id=? AND teacher_id=?";
		return template.queryForObject(SQL, new Object[] {model.getDocTypeId(),model.getTeacherId()}, String.class);
	}

	@Override
	public Map<String, Object> deleteEducation(TeacherEducationModel model) {
		String sql = "DELETE "
				+ "FROM "
				+ schoolId + "_teacher_education"
				+ " WHERE teacher_education_id = ?";
		
		if(template.update(sql, new Object[]{model.getTeacherEducationId()}) == 1) {
			
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Education Deleted");
			return data;
		}
		else {
			logger.error("deleteTeacherEducation : Failed to delete TeacherEducation :" +  model.getTeacherEducationId());
			return SvcStatus.GET_FAILURE("TeacherEducation could not be deleted. Contact System Admin");
		}
	}

	public Map<String, Object> deleteDocument(TeacherDocModel model) {
		String sql = "DELETE "
				+ "FROM "
				+ schoolId + "_teacher_doc"
				+ " WHERE teacher_doc_id = ?";
		
		if(template.update(sql, new Object[]{model.getTeacherDocId()}) == 1) {
			
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Document Deleted");
			return data;
		}
		else {
			logger.error("deleteDocument: Failed to delete TeacherDocument :" +  model.getTeacherId());
			return SvcStatus.GET_FAILURE("TeacherDocument could not be deleted. Contact System Admin");
		}
	}

	public Map<String, Object> deleteClassSubject(SchoolTeacherClassSubjectModel model) {
		String sql = "DELETE "
				+ "FROM "
				+ schoolId + "_teacher_class_subject"
				+ " WHERE teacher_class_subject_id = ?";
		
		if(template.update(sql, new Object[]{model.getTeacherClassSubjectId()}) == 1) {
			
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Subject Deleted");
			return data;
		}
		else {
			logger.error("deleteTeacherClassSubject : Failed to delete TeacherClassSubject:" +  model.getTeacherId());
			return SvcStatus.GET_FAILURE("TeacherSubject could not be deleted. Contact System Admin");
		}	}

	public Map<String, Object> deleteClass(TeacherClassModel model) {
		String sql = "DELETE "
				+ "FROM "
				+ schoolId + "_teacher_class"
				+ " WHERE teacher_class_id = ?";
		
		if(template.update(sql, new Object[]{model.getTeacherClassId()}) == 1) {
			
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Class Deleted");
			return data;
		}
		else {
			logger.error("deleteTeacherClass : Failed to delete TeacherClass :" +  model.getTeacherClassId());
			return SvcStatus.GET_FAILURE("TeacherClass could not be deleted. Contact System Admin");
		}
	}
}
