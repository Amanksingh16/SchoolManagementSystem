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
import kmsg.sms.mst.mapper.WingsMapper;
import kmsg.sms.mst.model.AcademicSchedule;
import kmsg.sms.mst.model.AcademicYear;
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
		} catch (Exception e) {
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
		} catch (Exception e) {
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
}