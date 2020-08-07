package kmsg.sms.enquiry.daoimpl;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.common.Util;
import kmsg.sms.enquiry.daoint.EnquiryDaoInt;
import kmsg.sms.enquiry.mapper.EnquiryMapper;
import kmsg.sms.enquiry.model.Enquiry;

@Repository
public class EnquiryDaoImpl implements EnquiryDaoInt, SMSLogger {
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	
	public Map<String, Object> selectEnquiry() 
	{
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =
				"SELECT"
					+ " e.enquiry_id"
					+ ", e.enquiry_no"
					+ ", e.enquiry"
					+ ", DATE_FORMAT(e.enquiry_dt, '%d-%m-%Y') enquiry_dt"
					+ ", e.gender"
					+ ", e.age"
					+ ", e.contact_no"
					+ ", e.email"
					+ ", e.source_id"
					+ ", c.class_id"
					+ ", IFNULL(class_label,c.class_name) as class_name"
					+ ", e.assigned_to_id"
					+ ", t.teacher"
					+ ", DATE_FORMAT(e.assigned_dt, '%d-%m-%Y') assigned_dt"
					+ ", e.status_id"
					+ ", e.qry"
					+ ", e.regs_no"
				+ " FROM " +schoolId+ "_enquiry e"
				+ " JOIN " +schoolId+ "_class c ON e.class_id = c.class_id"
				+ " JOIN teacher t ON e.assigned_to_id = t.teacher_id"
				+ " ORDER BY enquiry_id DESC";

		List<Enquiry> list = new ArrayList<>();
		try {
			list = template.query( SQL, new EnquiryMapper());
			if(list.size() == 0) {
				result.put( SvcStatus.STATUS, SvcStatus.FAILURE);
				result.put( SvcStatus.MSG, "No Data exist");
				return result;
			}
		}
		catch(Exception e) {
			logger.error("selectEnquiry exception:" + e.getMessage());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in selecting Enquiry. Contact system admin") ;
		}
		
		result.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put( "lstEnquiry", list);
		return result ;
	}

	public Map<String, Object> insertEnquiry(Enquiry model) {
		final String SQL = 
			"INSERT INTO " + schoolId + "_enquiry ("
				+" enquiry_no"
				+ ", enquiry"
				+ ", enquiry_dt"
				+ ", gender"
				+ ", age"
				+ ", contact_no"
				+ ", email"
				+ ", source_id"
				+ ", class_id"
				+ ", assigned_to_id"
				+ ", assigned_dt"
				+ ", status_id"
				+ ", qry"
			+ ") VALUES ("
			+ "?,?,STR_TO_DATE(?,'%d-%m-%Y' ),?,?"
			+ ",?,?,?,?"
			+ ",?,STR_TO_DATE(?,'%d-%m-%Y' ),?,?)";

		int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = template.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
					
					int ctr=1;
					ps.setString(ctr++, model.getEnquiryNo());
					ps.setString(ctr++, model.getEnquiry());
					ps.setString(ctr++, model.getEnquiryDt());
					ps.setString(ctr++, model.getGender());
					ps.setInt(ctr++, model.getAge());
					ps.setString(ctr++, model.getContactNo());
					ps.setString(ctr++, model.geteMail());
					ps.setInt(ctr++, model.getSourceId());
					ps.setInt(ctr++, model.getClasses().getClassId());
					ps.setInt(ctr++, model.getAssignedToId());
					ps.setString(ctr++, model.getAssignedOn());
					ps.setInt(ctr++, model.getStatusId());
					ps.setString(ctr++, model.getQuery());
					
					return ps;
				}
			}, holder);
		}catch(DuplicateKeyException e) {
			logger.error("updateEnquiry : Duplicate Key for this Enquiry:" + model.getEnquiryNo());
			return SvcStatus.GET_FAILURE("Enquiry Number already exist");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("insertEnquiry: Exception occured:" + model.getEnquiry() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Enquiry. Contact System Admin");
		}

		if (count == 1) {
			Map<String, Object> data = new HashMap<>();
			data.put("enquiryId", holder.getKey().intValue());
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Enquiry added");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Enquiry could not be added. Contact System Admin");
		}
	}

	public Map<String, Object> updateEnquiry(Enquiry model) 
	{
		final String SQL =
				"UPDATE "+schoolId+"_enquiry SET enquiry = ? ,"
				+ "enquiry_no = ? ,"
				+ "enquiry_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " gender = ?,"
				+ " age = ?,"
				+ " contact_no = ?,"
				+ " email = ?, "
				+ " source_id = ?, "
				+ " class_id = ?,"
				+ " assigned_to_id = ?, "
				+ " assigned_dt = STR_TO_DATE(?,'%d-%m-%Y'), "
				+ " status_id = ?, "
				+ " qry=?"
				+ " WHERE enquiry_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
						 model.getEnquiry(),
						 model.getEnquiryNo(),
						 model.getEnquiryDt(),
						 model.getGender(),
						 model.getAge(),
						 model.getContactNo(),
						 model.geteMail(),
						 model.getSourceId(),
						 model.getClasses().getClassId(),
						 model.getAssignedToId(),
						 model.getAssignedOn(),
						 model.getStatusId(),
						 model.getQuery(),
						 model.getEnquiryId()
					});
			}
			catch(DuplicateKeyException e) {
				logger.error("updateEnquiry : Duplicate Key for this Enquiry:" + model.getEnquiryNo());
				return SvcStatus.GET_FAILURE("Enquiry Number already exist");
			}
			catch(Exception e) {
				logger.error("updateEnquiry :Exception occured in updateEnquiry:" + model.getEnquiryNo() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating Enquiry. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Enquiry Updated");
				return data;
			}
			else {
				logger.error("updateEnquiry : Failed to update Enquiry :" +  model.getEnquiryNo());
				return SvcStatus.GET_FAILURE("Enquiry could not be updated. Contact System Admin");
			}
	}

	public Map<String, Object> registerEnquiry(String enquiryId, String regsNo) 
	{
		final String SQL =
				"UPDATE "+schoolId+"_enquiry SET"
				+ " regs_no = ?,"
				+ " regs_dt = STR_TO_DATE(?,'%Y-%m-%d')"
				+ " WHERE enquiry_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
							 regsNo,
							 Util.TodayDate(),
							 enquiryId
					 });
			}
			catch(DuplicateKeyException e) {
				logger.error("registerEnquiry : Duplicate Key for this Enquiry ID:" + enquiryId);
				return SvcStatus.GET_FAILURE("Registration Number already exist");
			}
			catch(Exception e) {
				logger.error("registerEnquiry :Exception occured in registerEnquiry for ID:" + enquiryId + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in registering Enquiry. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Registration Successful");
				return data;
			}
			else {
				logger.error("registerEnquiry : Failed to register Enquiry for ID :" +  enquiryId);
				return SvcStatus.GET_FAILURE("Enquiry could not be registered. Contact System Admin");
			}
	}

	public Map<String, Object> ConversionGraph(String year, String dt) 
	{
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =
				" SELECT count("+dt+")" + 
				" FROM "+schoolId+"_enquiry  " + 
				" RIGHT JOIN week_days w ON week("+dt+") = w.week_no AND year("+dt+") = ?" + 
				" GROUP BY w.week_no";

		List<Integer> list = new ArrayList<>();
		try {
			list = template.queryForList( SQL, new Object[] {year},Integer.class);
		}
		catch(Exception e) {
			logger.error("selectConversion exception:" + e.getMessage());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in selecting Conversion. Contact system admin") ;
		}
		
		result.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put( "enquiryRegister", list);
		return result ;
	}

	public Map<String, Object> YearlyComparisonGraph(int year) 
	{
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =
				" SELECT count(e.enquiry_dt) as noOfEnq" + 
				" FROM 1_enquiry e" + 
				" RIGHT JOIN week_days w ON week(enquiry_dt) = w.week_no AND year(e.enquiry_dt) = ?" + 
				" GROUP BY w.week_no";

		List<Integer> list = new ArrayList<>();
		try {
			list = template.queryForList( SQL, new Object[] {year},Integer.class);
		}
		catch(Exception e) {
			logger.error("selectYearlyComparison exception:" + e.getMessage());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in selecting Yearly Comparison. Contact system admin") ;
		}
		
		result.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put( "yearData", list);
		return result ;
	}

	public int ClassWiseGraph(int year, int classId) 
	{		
		final String SQL =
				" SELECT count(*)" + 
				" FROM "+schoolId+"_enquiry WHERE year(enquiry_dt) = ?" + 
				" AND class_id = ?";

		try {
			return template.queryForObject( SQL, new Object[] {year, classId},Integer.class);
		}
		catch(Exception e) {
			logger.error("selectWeekWise exception:" + e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
}
