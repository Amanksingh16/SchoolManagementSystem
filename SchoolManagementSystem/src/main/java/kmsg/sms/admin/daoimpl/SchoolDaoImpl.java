package kmsg.sms.admin.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.admin.daoint.SchoolDaoInt;
import kmsg.sms.admin.mapper.SchoolMapper;
import kmsg.sms.admin.model.School;
import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.common.Util;

@Repository
public class SchoolDaoImpl implements SchoolDaoInt, SMSLogger
{
	@Autowired
	JdbcTemplate template;
	
	public Map<String, Object> insertSchoolSystem(String email, String originalPwdHash, String salt) 
	{
		final String SQL = "INSERT INTO school (email, password, salt) VALUES (?,?,?)";

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
						ps.setString(1, email);
						ps.setString(2, originalPwdHash);
						ps.setString(3, salt);
						
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertSchool : Duplicate Key insertSchool:" + email);
			return SvcStatus.GET_FAILURE("School Email already exist");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertSchool :Exception occured in insertSchool:" + email + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding new School. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "School Verification mail sent successfully");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put("schoolId", holder.getKey().intValue());
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("School could not be added. Contact System Admin");
		}
	}

	public boolean updateSchoolLastActive(int schoolId)
	{
		String SQL = " UPDATE school "
				   + " SET "
				   + " last_active = STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') "
				   + " WHERE school_id = ? ";
		
		return template.update(SQL, new Object[]{Util.Now(),schoolId}) == 1;
	}

	public String getSchoolLastActive(int schoolId) 
	{
		String SQL = " SELECT last_active "
				   + " FROM school "
				   + " WHERE school_id = ? ";
		return template.queryForObject(SQL, new Object[]{schoolId} , String.class);
	}
	
	public Map<String, Object> createSchoolData(School model) 
	{
		SimpleJdbcCall call = new SimpleJdbcCall(template)
	            .withProcedureName("CREATESCHOOL")
	            . declareParameters(
	                    new SqlParameter("schoolId", Types.INTEGER),
	                    new SqlParameter("schoolName", Types.VARCHAR),
	                    new SqlParameter("stateId", Types.INTEGER),
	                    new SqlParameter("cityId", Types.INTEGER),
	                    new SqlParameter("address1", Types.VARCHAR),
	                    new SqlParameter("address2", Types.VARCHAR),
	                    new SqlParameter("pin", Types.VARCHAR),
	                    new SqlParameter("phoneNo", Types.VARCHAR),
	                    new SqlParameter("noOfStudents", Types.INTEGER));
		
				MapSqlParameterSource in = new MapSqlParameterSource();
	            in.addValue("schoolId",model.getSchoolId());
	            in.addValue("schoolName",model.getSchool());
	            in.addValue("stateId",model.getStateId());
	            in.addValue("cityId",model.getCityId());
	            in.addValue("address1",model.getAddress1());
	            in.addValue("address2",model.getAddress2());
	            in.addValue("pin",model.getPin());
	            in.addValue("phoneNo",model.getPhoneNo());
	            in.addValue("noOfStudents",model.getNoOfStudents());
	       
		return call.execute(in);
	}

	public Map<String, Object> selectUserDetails(String email) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "SELECT "
				+ "school_id"
				+ ",password"
				+ ",salt"
				+ ",school "
				+ " FROM school s"
				+ " WHERE email = ?";
				
		try {
			List<School> list = template.query(SQL, new Object[]{email},new SchoolMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"User Does Not Exist");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("schoolModel",list.get(0));
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured in login into the system : "+e);
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE, "Error Occured in login into the system");
			return data;
		}
	}
	
	@Override
	public String getSalt(String emailId) {
		String sql = " SELECT salt FROM school WHERE email = ? ";
		return template.queryForObject(sql, new Object[]{ emailId }, String.class);
	}
	
	@Override
	public int getId(String emailId) {
		String sql = " SELECT school_id FROM school WHERE email = ? ";
		return template.queryForObject(sql, new Object[]{ emailId }, Integer.class);
	}
	
	@Override
	public int getIdFromAutomailId(int automaticMailId) {
		String sql = " SELECT id_value FROM auto_emails WHERE auto_emails_id = ? ";
		return template.queryForObject(sql, new Object[]{ automaticMailId }, Integer.class);
	}

	@Override
	public boolean changePassword(String emailId, String oldPasswordHash, String newPasswordHash) 
	{
		String sql = "UPDATE school SET password = ? WHERE email = ? AND password = ?";
		return template.update(sql,new Object[]{newPasswordHash,emailId,oldPasswordHash})==1;
	}
	
	@Override
	public boolean forgotPassword(String email, String originalPwdHash, String salt) 
	{
		String sql = " UPDATE school SET password = ?,salt = ? WHERE email = ? ";
		return template.update(sql, new Object[] {originalPwdHash, salt, email})==1;
	}

	public boolean updatePhoneVerification(String phoneNo) 
	{
		String sql = " UPDATE school SET mobile_no_approved = ? WHERE mobile_no = ? ";
		return template.update(sql, new Object[] {1,phoneNo})==1;
	}
	
	public boolean updateEmailVerification(int schoolId) 
	{
		String sql = " UPDATE school SET email_id_approved = ? WHERE school_id = ? ";
		return template.update(sql, new Object[] {1,schoolId})==1;
	}
}
