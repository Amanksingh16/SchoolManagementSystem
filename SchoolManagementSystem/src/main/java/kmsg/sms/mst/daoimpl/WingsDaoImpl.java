package kmsg.sms.mst.daoimpl;

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
import kmsg.sms.mst.daoint.WingsDaoInt;
import kmsg.sms.mst.mapper.AcademicScheduleMapper;
import kmsg.sms.mst.mapper.AcademicYearMapper;
import kmsg.sms.mst.mapper.WingSessionMapper;
import kmsg.sms.mst.mapper.WingsMapper;
import kmsg.sms.mst.model.AcademicSchedule;
import kmsg.sms.mst.model.AcademicYear;
import kmsg.sms.mst.model.WingSession;
import kmsg.sms.mst.model.Wings;

@Repository
public class WingsDaoImpl implements WingsDaoInt, SMSLogger
{
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	
	public Map<String, Object> selectWings() 
	{
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =  "SELECT wing_id,"
				+ " wing,"
				+ " mc1.class_id as from_std_id, "
				+ " mc1.class_name as from_std, "
				+ " mc2.class_id as to_std_id, "
				+ " mc2.class_name as to_std, "
				+ " day "
				+ " FROM "+schoolId+"_wings w"
				+ " JOIN mst_class mc1 ON mc1.class_id = w.from_std_id"
				+ " JOIN mst_class mc2 ON mc2.class_id = w.to_std_id";

		List<Wings> list = new ArrayList<>();
		try {
			list = template.query( SQL, new WingsMapper());
			if(list.size() == 0)
			{
				result.put( SvcStatus.STATUS, SvcStatus.FAILURE);
				result.put( SvcStatus.MSG, "No Data exist");
				return result;
			}
		}
		catch(Exception e) {
			logger.error("selectWings occured:" + e.getMessage());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in selecting Wings. Contact system admin") ;
		}
		
		result.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put( "lstWings", list);
		return result ;
	}

	public Map<String, Object> insertNewWing(Wings model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_wings(wing,from_std_id,to_std_id,day) VALUES (?,?,?,?)";

		int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = template.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
					
					ps.setString(1, model.getWing());
					ps.setInt(2, model.getFromClassId());
					ps.setInt(3, model.getToClassId());
					ps.setString(4, model.getDay());
					return ps;
				}
			}, holder);
		} 
		catch(DuplicateKeyException e) {
			logger.error("insertWing : Duplicate Key insertWing:" + model.getWing());
			return SvcStatus.GET_FAILURE("Wing name already exist" +  model.getWing());
		}
		catch (Exception e) {
			logger.error("insertWing:Exception occured:" + model.getWing() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Wing. Contact System Admin");
		}

		if (count > 0) {
			Map<String, Object> data = new HashMap<>();
			data.put("wingId", holder.getKey().intValue());
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "New Wing Added");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Wing could not be added. Contact System Admin");
		}
	}

	public Map<String, Object> updateWing(Wings model)
	{
		final String SQL = "UPDATE "+schoolId+"_wings SET "
						+ " wing = ?,"
						+ " day = ?,"
						+ " from_std_id = ?,"
						+ " to_std_id = ?" 
						+ " WHERE wing_id = ? ";

		int count = 0;
		try {
			count = template.update(SQL,
					new Object[] { 
								model.getWing(),
								model.getDay(),
								model.getFromClassId(),
								model.getToClassId(),
								model.getWingId(),
							});
		} 
		catch(DuplicateKeyException e) {
			logger.error("insertWing : Duplicate Key insertWing:" + model.getWing());
			return SvcStatus.GET_FAILURE("Wing name already exist" +  model.getWing());
		}
		catch (Exception e) {
			logger.error("updateWing:Exception occured: Wing : " + model.getWingId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating wing. Contact System Admin");
		}

		if (count > 0) {
			return SvcStatus.GET_SUCCESS("Wing updated");
		} else {
			logger.error("updateWing:Failed to update Wing :" + model.getWingId());
			return SvcStatus.GET_FAILURE("Wing could not be updated. Contact System Admin");
		}
	}

	public Map<String, Object> selectAcademicYear(int wingId) 
	{
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =  "SELECT "
				+ " academic_year_id,"
				+ " academic_year,"
				+ " DATE_FORMAT(start_dt,'%d-%m-%Y') as start_dt,"
				+ " DATE_FORMAT(end_dt,'%d-%m-%Y') as end_dt,"
				+ " locked"
				+ " FROM "+schoolId+"_academic_year"
				+ " WHERE wing_id = ?";


		List<AcademicYear> list = new ArrayList<>();
		try {
			list = template.query( SQL,new Object[] {wingId}, new AcademicYearMapper());
			if(list.size() == 0)
			{
				result.put( SvcStatus.STATUS, SvcStatus.FAILURE);
				result.put( SvcStatus.MSG, "No Data exist");
				return result;
			}
		}
		catch(Exception e) {
			logger.error("selectAcademicYear occured:" + e.getMessage());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in selecting AcademicYear. Contact system admin") ;
		}
		
		result.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put( "lstAcademicYear", list);
		return result ;
	}
	
	public Map<String, Object> insertNewAcademicYear(AcademicYear model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_academic_year(wing_id,academic_year,from_dt,to_dt,locked) VALUES (?,?,STR_TO_DATE(?,'%d-%m-%Y'),STR_TO_DATE(?,'%d-%m-%Y'),?)";

		int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = template.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
					
					ps.setInt(1, model.getWingId());
					ps.setString(2, model.getAcademicYear());
					ps.setString(3, model.getStartDt());
					ps.setString(4, model.getEndDt());
					ps.setInt(5, 0);
					return ps;
				}
			}, holder);
		} catch (Exception e) {
			logger.error("insertAcademicYear:Exception occured: Wing " + model.getWingId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Academic Year. Contact System Admin");
		}

		if (count > 0) {
			Map<String, Object> data = new HashMap<>();
			data.put("academicId", holder.getKey().intValue());
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "New Academic Year Added");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Academic Year could not be added. Contact System Admin");
		}
	}

	public Map<String, Object> updateAcademicYear(AcademicYear model) 
	{
		final String SQL = "UPDATE "+schoolId+"_academic_year SET "
				+ " academic_year = ?,"
				+ " start_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " to_dt = STR_TO_DATE(?,'%d-%m-%Y')," 
				+ " locked = ?" 
				+ " WHERE academic_year_id = ? ";

		int count = 0;
		try {
			count = template.update(SQL,
					new Object[] { 
								model.getAcademicYear(),
								model.getStartDt(),
								model.getEndDt(),
								model.isLocked(),
								model.getAcademicYearId(),
							});
		} catch (Exception e) {
			logger.error("updateAcademicYear:Exception occured: Academic Year : " + model.getAcademicYearId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating Academic Year. Contact System Admin");
		}
		
		if (count > 0) {
			return SvcStatus.GET_SUCCESS("Academic Year updated");
		} else {
			logger.error("updateAcademicYear:Exception to update Academic Year :" + model.getWingId());
			return SvcStatus.GET_FAILURE("Academic Year could not be updated. Contact System Admin");
		}
	}
	
	public Map<String, Object> selectAcademicSchedule(int wingId, int academicYear) 
	{
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =  "SELECT "
				+ " academic_schedule_id,"
				+ " DATE_FORMAT(start_dt,'%d-%m-%Y') as start_dt,"
				+ " DATE_FORMAT(end_dt,'%d-%m-%Y') as end_dt,"
				+ " type"
				+ " FROM "+schoolId+"_academic_schedule"
				+ " WHERE wing_id = ? AND academic_year_id = ?";

		List<AcademicSchedule> list = new ArrayList<>();
		try {
			list = template.query( SQL,new Object[] {wingId, academicYear}, new AcademicScheduleMapper());
			if(list.size() == 0)
			{
				result.put( SvcStatus.STATUS, SvcStatus.FAILURE);
				result.put( SvcStatus.MSG, "No Data exist");
				return result;
			}
		}
		catch(Exception e) {
			logger.error("selectAcademicSchedule occured:" + e.getMessage());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in selecting AcademicSchedule. Contact system admin") ;
		}
		
		result.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put( "lstAcademicSchedule", list);
		return result ;
	}

	public Map<String, Object> insertNewAcdSchedule(AcademicSchedule model) {
		final String SQL = "INSERT INTO "+schoolId+"_academic_schedule(wing_id,academic_year_id,from_dt,to_dt,type) VALUES (?,?,STR_TO_DATE(?,'%d-%m-%Y'),STR_TO_DATE(?,'%d-%m-%Y'),?)";

		int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = template.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
					
					ps.setInt(1, model.getWingId());
					ps.setInt(2, model.getAcademicYearId());
					ps.setString(3, model.getStartDt());
					ps.setString(4, model.getEndDt());
					ps.setString(5, model.getType());
					return ps;
				}
			}, holder);
		} catch (Exception e) {
			logger.error("insertAcademicSchedule:Exception occured: Academic Year " + model.getAcademicYearId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Academic Schedule. Contact System Admin");
		}

		if (count > 0) {
			Map<String, Object> data = new HashMap<>();
			data.put("academicScheduleId", holder.getKey().intValue());
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "New Academic Schedule Added");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Academic Schedule could not be added. Contact System Admin");
		}
	}

	public Map<String, Object> updateAcdSchedule(AcademicSchedule model) 
	{
		final String SQL = "UPDATE "+schoolId+"_academic_schedule SET "
				+ " start_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " to_dt = STR_TO_DATE(?,'%d-%m-%Y')," 
				+ " type = ?" 
				+ " WHERE academic_schedule_id = ? ";

		int count = 0;
		try {
			count = template.update(SQL,
					new Object[] { 
								model.getStartDt(),
								model.getEndDt(),
								model.getType(),
								model.getAcademicScheduleId(),
							});
		} catch (Exception e) {
			logger.error("updateAcademicSchedule:Exception occured: Academic Schedule : " + model.getAcademicScheduleId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating Academic Schedule. Contact System Admin");
		}
		
		if (count > 0) {
			return SvcStatus.GET_SUCCESS("Academic Schedule updated");
		} else {
			logger.error("updateAcademicSchedule:Exception to update Academic Year :" + model.getWingId());
			return SvcStatus.GET_FAILURE("Academic Schedule could not be updated. Contact System Admin");
		}
	}

	public Map<String, Object> insertNewWingSession(WingSession model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_wing_session(wing_id,wing_session,from_dt,to_dt,session_type) VALUES (?,?,STR_TO_DATE(?,'%d-%m-%Y'),STR_TO_DATE(?,'%d-%m-%Y'),?)";

		int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = template.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
					
					ps.setInt(1, model.getWingId());
					ps.setString(2, model.getWingSession());
					ps.setString(3, model.getFromDt());
					ps.setString(4, model.getToDt());
					ps.setString(5, model.getSessionType());
					return ps;
				}
			}, holder);
		}
		catch(DuplicateKeyException e) {
			logger.error("insertWingSession : Duplicate Key insertWingSession:" + model.getWingSession());
			return SvcStatus.GET_FAILURE("Wing session already exist" +  model.getWingSession());
		}
		catch (Exception e) {
			logger.error("insertWingSession:Exception occured: Wing Session " + model.getWingSession() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in saving Wing Session. Contact System Admin");
		}

		if (count > 0) {
			Map<String, Object> data = new HashMap<>();
			data.put("wingSessionId", holder.getKey().intValue());
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "New Wing Session Added");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Wing Session could not be added. Contact System Admin");
		}
	}

	public Map<String, Object> updateWingSession(WingSession model) 
	{
		final String SQL = "UPDATE "+schoolId+"_wing_session SET "
				+ " from_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " to_dt = STR_TO_DATE(?,'%d-%m-%Y')," 
				+ " session_type = ?," 
				+ " wing_session = ?,"
				+ " wing_id = ?" 
				+ " WHERE wing_session_id = ? ";

		int count = 0;
		try {
			count = template.update(SQL,
					new Object[] { 
								model.getFromDt(),
								model.getToDt(),
								model.getSessionType(),
								model.getWingSession(),
								model.getWingId(),
								model.getWingSessionId()
							});
		} 
		catch(DuplicateKeyException e) {
			logger.error("updateWingSession : Duplicate Key updateWingSession:" + model.getWingSession());
			return SvcStatus.GET_FAILURE("Wing session already exist" +  model.getWingSession());
		}
		catch (Exception e) {
			logger.error("updateWingSession:Exception occured: Wing Session : " + model.getWingSession() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating Wing Session. Contact System Admin");
		}
		
		if (count > 0) {
			return SvcStatus.GET_SUCCESS("Wing Session updated");
		} else {
			logger.error("updateWingSession:Exception to update Wing Session :" + model.getWingId());
			return SvcStatus.GET_FAILURE("Wing Session could not be updated. Contact System Admin");
		}
	}

	public Map<String, Object> selectWingSessions(int wingId) 
	{
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =  "SELECT "
				+ " wing_session_id,"
				+ " DATE_FORMAT(from_dt,'%d-%m-%Y') as from_dt,"
				+ " DATE_FORMAT(to_dt,'%d-%m-%Y') as to_dt,"
				+ " session_type,"
				+ " wing_session,"
				+ " w.wing_id,"
				+ " w.wing"
				+ " FROM "+schoolId+"_wing_session ws"
				+ " JOIN "+schoolId+"_wings w ON w.wing_id = ws.wing_id"
				+ " WHERE ws.wing_id = ?";

		List<WingSession> list = new ArrayList<>();
		try {
			list = template.query( SQL,new Object[] {wingId}, new WingSessionMapper());
			if(list.size() == 0)
			{
				result.put( SvcStatus.STATUS, SvcStatus.FAILURE);
				result.put( SvcStatus.MSG, "No session exist for this wing");
				return result;
			}
		}
		catch(Exception e) {
			logger.error("selectWingSession occured:" + e.getMessage());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in selecting WingSession. Contact system admin") ;
		}
		
		result.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put( "lstWingSession", list);
		return result ;
	}

	public int checkExistingWing(Wings model) 
	{
		final String SQL =  " SELECT wing_id " + 
				" FROM "+schoolId+"_wings" + 
				" WHERE " + 
				" CASE WHEN (? = from_std_id AND ? = to_std_id) OR (? = to_std_id) OR (? = from_std_id)" + 
				" IS TRUE THEN " + 
				" TRUE" + 
				" ELSE( " + 
				"		CASE WHEN ((? > from_std_id AND ? < to_std_id)" + 
				"		OR  " + 
				"		(? > from_std_id AND ? < to_std_id)" + 
				"	 ) " + 
				"     IS FALSE THEN " + 
				"	 ( " + 
				"		(from_std_id > ? AND from_std_id < ?) OR  " + 
				"	    (to_std_id > ? AND to_std_id < ?)  " + 
				"	 ) " + 
				"	 ELSE TRUE END " + 
				" )"+
			    " END AND" + 
				" ( " + 
				"	? = 0 OR wing_id != ?" + 
				" )  " + 
				" LIMIT 1 ;";
		try {
			return template.queryForObject(SQL, new Object[] {
					 model.getFromClassId()
					 , model.getToClassId()
					 , model.getFromClassId()
					 , model.getToClassId()
					 , model.getFromClassId()
					 , model.getFromClassId()
					 , model.getToClassId()
					 , model.getToClassId()
					 , model.getFromClassId()
					 , model.getToClassId()
					 , model.getFromClassId()
					 , model.getToClassId()
					, model.getWingId()
					, model.getWingId()
			}, Integer.class);
		}
		catch(EmptyResultDataAccessException e) {
			logger.error("selectExistingWing: No Wing found");
			return 0;
		}
		catch(Exception e) {
			logger.error("selectExistingSlot: Exception occured:" + e.getMessage());
			return -1 ;
		}
	}
}
