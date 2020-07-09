package kmsg.sms.mst.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kmsg.sms.common.SvcStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.mst.daoint.PeriodTypeDaoInt;
import kmsg.sms.mst.mapper.PeriodTypeMapper;
import kmsg.sms.mst.model.PeriodType;

@Repository
public class PeriodTypeDaoImpl implements PeriodTypeDaoInt,SMSLogger {
	
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	
	@Override
	public Map<String, Object> TypeList() {
		final String SQL = 
				" SELECT mst_period_type_id,"
				+ " period_type,"
				+ " assign_teacher"
				+ " FROM "+schoolId+"_mst_period_type";
		
		List<PeriodType> list = new ArrayList<>();
		try {
			list = template.query(SQL, new PeriodTypeMapper());
		} 
		catch (EmptyResultDataAccessException e) {
			System.out.println(e);
			logger.error("selectInvoices: No Period Type found");
			return SvcStatus.GET_FAILURE("No Period Type found. Contact System admin");
		}
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectPeriodType: Exception in selecting Period Type " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Period Type. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstPeriodType",  list );
		return result ;
				
	}

	public Map<String, Object> savePeriodType(PeriodType model) {
		// TODO Auto-generated method stub
		final String SQL = "INSERT INTO "+schoolId+"_mst_period_type (period_type,assign_teacher) VALUES (?,?)";

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
						ps.setString( 1, model.getPeriodType());
						ps.setBoolean( 2, model.isAssignTeacher());
						
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertPeriodType : Duplicate Key insertPeriodType:" + model.getPeriodTypeId());
			return SvcStatus.GET_FAILURE("PeriodType already exist" +  model.getPeriodTypeId());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertPeriodType :Exception occured in insertPeriodType:" + model.getPeriodTypeId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding PeriodType. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Period Type is added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("PeriodType could not be added. Contact System Admin");
		}
	}

	public Map<String, Object> updatePeriodType(PeriodType model) 
	{

		final String SQL = "UPDATE "+schoolId+"_mst_period_type SET period_type = ? ,"
				+ " assign_teacher = ? "
				+ " WHERE mst_period_type_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
						model.getPeriodType(),
						model.isAssignTeacher(),
						model.getPeriodTypeId(),
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updatePeriodType : Duplicate Key for this PeriodType:" + model.getPeriodType());
			return SvcStatus.GET_FAILURE("PeriodType already exist");
		}
		catch(Exception e) {
			logger.error("updatePeriodType :Exception occured in updatePeriodType:" + model.getPeriodType() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating PeriodType. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updatePeriodType : Failed to update PeriodType :" +  model.getPeriodType());
			return SvcStatus.GET_FAILURE("PeriodType could not be updated. Contact System Admin");
		}
	}
}

