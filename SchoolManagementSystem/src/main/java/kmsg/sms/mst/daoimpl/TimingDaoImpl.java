package kmsg.sms.mst.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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

import kmsg.sms.mst.daoint.TimingDaoInt;
import kmsg.sms.mst.mapper.TimingMapper;
import kmsg.sms.mst.mapper.TimingPeriodMapper;
import kmsg.sms.mst.model.Timing;
import kmsg.sms.mst.model.TimingPeriodList;
import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;

@Repository
public class TimingDaoImpl implements TimingDaoInt, SMSLogger
{
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	
	public Map<String, Object> getTimingList() 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = " SELECT w.wing_id,"
				+ "w.wing,"
				+ "tm.timing_id,"
				+ "DATE_FORMAT(tm.effective_from_dt,'%d-%m-%Y') as effective_from_dt," + 
				" tm.periods," + 
				" DATE_FORMAT(tm.start_tm,'%H:%i') as start_tm," + 
				" IFNULL(DATE_FORMAT(tm.end_tm,'%H:%i'),'') as end_tm" + 
				" FROM "+schoolId+"_mst_timing tm" + 
				" LEFT JOIN "+schoolId+"_wings w ON w.wing_id = tm.wing_id";
				
		try {
			List<Timing> list = template.query(SQL,new TimingMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Exist");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstTiming",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}

	public Map<String, Object> saveTiming(Timing model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_mst_timing (effective_from_dt,start_tm, wing_id,periods) VALUES (STR_TO_DATE(?,'%d-%m-%Y'),STR_TO_DATE(?,'%H:%i'),?,?)";

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
						ps.setString( 1, model.getEffectiveDt());
						ps.setString( 2, model.getStartTm());
						ps.setInt( 3, model.getWingId());
						ps.setInt( 4, model.getPeriods());
						
						return ps ;
					}
				}, holder ) ;
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertBuilding :Exception occured in inserttimings:" + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding timings. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put("timingId", holder.getKey().intValue());
			data.put(SvcStatus.MSG, "New Timings Added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Timings could not be added. Contact System Admin");
		}
	}

	public Map<String, Object> updateTiming(Timing model) 
	{
		final String SQL = "UPDATE "+schoolId+"_mst_timing SET effective_from_dt = STR_TO_DATE(?,'%d-%m-%Y') ,"
				+ " start_tm = STR_TO_DATE(?,'%H:%i'),"
				+ " wing_id = ? , "
				+ " periods = ?  "
				+ " WHERE timing_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
					 model.getEffectiveDt(),
					 model.getStartTm(),
					 model.getWingId(),
					 model.getPeriods(),
					 model.getTimingId()
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateTiming : Duplicate Key for this Timing:" + model.getTimingId());
			return SvcStatus.GET_FAILURE("The Timing Data Already Exist");
		}
		catch(Exception e) {
			logger.error("updateTiming :Exception occured in updateTiming:" + model.getTimingId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating Timing. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Timings Updated");
			return data;
		}
		else {
			logger.error("updateTiming : Failed to update Timing :" +  model.getTimingId());
			return SvcStatus.GET_FAILURE("Timing could not be updated. Contact System Admin");
		}
	}
	
	@Override
	public int checkForTimingPeriods(int timingId) {
		String sql = " SELECT count(*) FROM "+schoolId+"_mst_timing_period WHERE timing_id = ? ";
		return template.queryForObject(sql, new Object[]{ timingId }, Integer.class);
	}
	
	@Override
	public int deleteTimingPeriods(int timingId) {
		String sql = " DELETE FROM "+schoolId+"_mst_timing_period WHERE timing_id = ? ";
		return template.update(sql, new Object[]{ timingId });
	}

	public int insertTimingPeriods(int timingId, TimingPeriodList timingPeriodList) {
		String sql = " INSERT INTO "+schoolId+"_mst_timing_period(timing_id, period, start_tm, period_type, duration) VALUES(?,?,STR_TO_DATE(?,'%H:%i'),?,?)";
		return template.update(sql, new Object[] {timingId, timingPeriodList.getPeriod(), timingPeriodList.getStartTm(), timingPeriodList.getPeriodType(),timingPeriodList.getDuration()});
	}

	@Override
	public int updateTimingEndTm(String endTm, int timingId) {
		String sql = " UPDATE "+schoolId+"_mst_timing SET end_tm = STR_TO_DATE(?,'%H:%i') WHERE timing_id = ? ";
		return template.update(sql, new Object[]{ endTm, timingId });
	}

	public Map<String, Object> getTimingPeriodList(int timingId) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = " SELECT timing_period_id,"
				+ "period,"
				+ "period_type,"
				+ "duration," + 
				" DATE_FORMAT(start_tm,'%H:%i') as start_tm" + 
				" FROM "+schoolId+"_mst_timing_period " + 
				" WHERE timing_id = ?";
				
		try {
			List<TimingPeriodList> list = template.query(SQL,new Object[] {timingId},new TimingPeriodMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Exist");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstTimingPeriod",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}
}
