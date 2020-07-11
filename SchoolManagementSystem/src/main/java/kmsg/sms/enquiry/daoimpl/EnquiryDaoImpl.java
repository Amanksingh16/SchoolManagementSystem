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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
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
	
	public Map<String, Object> selectEnquiry() {
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =
				"SELECT"
					+ " e.enquiry_id"
					+ ", e.enquiry_no"
					+ ", e.enquiry"
					+ ", e.gender"
					+ ", e.age"
					+ ", e.contact_no"
					+ ", e.email"
					+ ", e.source_id"
					+ ", c.class_id"
					+ ", class_name"
					+ ", e.assigned_to_id"
					+ ", t.teacher"
					+ ", DATE_FORMAT(e.assigned_dt, '%d-%m-%Y') assigned_dt"
					+ ", e.status_id"
					+ ", e.qry"
					+ ", e.regs_no"
				+ " FROM " +schoolId+ "_enquiry e"
				+ " JOIN " +schoolId+ "_class c ON e.class_id = c.class_id"
				+ " JOIN " +schoolId+ "_teacher t ON e.assigned_to_id = t.teacher_id"
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
				
				+ ", regs_no"
			+ ") VALUES ("
			+ "?,?,?,?"
			+ ",?,?,?,?"
			+ ",?,STR_TO_DATE(?,'%d-%m-%Y' ),?,?"
			+ ",? )";

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

					ps.setString(ctr++, model.getRegsNo());
					return ps;
				}
			}, holder);
		} catch (Exception e) {
			logger.error("insertEnquiry: Exception occured:" + model.getEnquiry() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Enquiry. Contact System Admin");
		}

		if (count == 1) {
			Map<String, Object> data = new HashMap<>();
			data.put("wingId", holder.getKey().intValue());
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Enquiry added");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Enquiry could not be added. Contact System Admin");
		}
	}

}
