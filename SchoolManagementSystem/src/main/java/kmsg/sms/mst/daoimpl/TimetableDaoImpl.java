package kmsg.sms.mst.daoimpl; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoint.TimetableDaoInt;
import kmsg.sms.mst.mapper.TimetableMapper;
import kmsg.sms.mst.mapper.TimetablePeriodsMapper;
import kmsg.sms.mst.model.Timetable;
import kmsg.sms.mst.model.TimetableClassSubjects;
import kmsg.sms.mst.model.TimetableClassTeachers;
import kmsg.sms.mst.mapper.TimetableClassSubjectsMapper;
import kmsg.sms.mst.mapper.TimetableClassTeachersMapper;
import kmsg.sms.mst.model.TimetablePeriods;

@Repository
public class TimetableDaoImpl implements TimetableDaoInt, SMSLogger
{
	
	private int schoolId;
	
	@Autowired
	JdbcTemplate temp;
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public int checkTimingPeriods(int wingId) 
	{
		String SQL = "SELECT count(*) FROM "+schoolId+"_mst_timing WHERE wing_id = ?";
		return temp.queryForObject(SQL, new Object[] {wingId},Integer.class);
	}

	public Map<String, Object> getTimetable(int wingId, int clsSecId) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = " SELECT timetable_id,timetable, 1 published FROM "+schoolId+"_timetable" + 
				" WHERE wing_id = ? AND class_section_id = ?" + 
				" UNION" + 
				" SELECT timetable_id,timetable, 0 published FROM "+schoolId+"_tmp_timetable" + 
				" WHERE wing_id = ? AND class_section_id = ?";
				
		try {
			List<Timetable> list = temp.query(SQL,new Object[]{wingId, clsSecId, wingId, clsSecId}, new TimetableMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No timetable available for selected filter");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstTimetable",list);
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

	public Map<String, Object> selectTimetablePeriods(int timetableId, String table) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "	SELECT timetable_id,"
				+ " mtp.timing_period_id," + 
				"	mtp.period," + 
				"	mtp.duration," + 
				"	DATE_FORMAT(mtp.start_tm,'%H:%i') as start_time," + 
				"   DATE_FORMAT(ADDTIME(DATE_FORMAT(mtp.start_tm,'%H:%i'),SEC_TO_TIME(mtp.duration * 60)),'%H:%i') as end_time," + 
				"	mtp.period_type," + 
				"	s1mon.mst_subject_id mon_1_subject_id," + 
				"	IFNULL(s1mon.subject,'') mon_1_subject," + 
				"	s2mon.mst_subject_id mon_2_subject_id," + 
				"	IFNULL(s2mon.subject,'') mon_2_subject," + 
				"	t1mon.teacher_id mon_1_teacher_id," + 
				"	IFNULL(t1mon.teacher,'') mon_1_teacher," + 
				"	t2mon.teacher_id mon_2_teacher_id," + 
				"	IFNULL(t2mon.teacher,'') mon_2_teacher," + 
				"	s1tue.mst_subject_id tue_1_subject_id," + 
				"	IFNULL(s1tue.subject,'') tue_1_subject," + 
				"	s2tue.mst_subject_id tue_2_subject_id," + 
				"	IFNULL(s2tue.subject,'') tue_2_subject, " + 
				"	t1tue.teacher_id tue_1_teacher_id," + 
				"	IFNULL(t1tue.teacher,'') tue_1_teacher," + 
				"	t2tue.teacher_id tue_2_teacher_id," + 
				"	IFNULL(t2tue.teacher,'') tue_2_teacher," + 
				"	s1wed.mst_subject_id wed_1_subject_id," + 
				"	IFNULL(s1wed.subject,'') wed_1_subject," + 
				"	s2wed.mst_subject_id wed_2_subject_id," + 
				"	IFNULL(s2wed.subject,'') wed_2_subject," + 
				"	t1wed.teacher_id wed_1_teacher_id," + 
				"	IFNULL(t1wed.teacher,'') wed_1_teacher," + 
				"	t2wed.teacher_id wed_2_teacher_id," + 
				"	IFNULL(t2wed.teacher,'') wed_2_teacher," + 
				"	s1thu.mst_subject_id thu_1_subject_id," + 
				"	IFNULL(s1thu.subject,'') thu_1_subject," + 
				"	s2thu.mst_subject_id thu_2_subject_id," + 
				"	IFNULL(s2thu.subject,'') thu_2_subject," + 
				"	t1thu.teacher_id thu_1_teacher_id," + 
				"	IFNULL(t1thu.teacher,'') thu_1_teacher, " + 
				"	t2thu.teacher_id thu_2_teacher_id," + 
				"	IFNULL(t2thu.teacher,'') thu_2_teacher," + 
				"	s1fri.mst_subject_id fri_1_subject_id," + 
				"	IFNULL(s1fri.subject,'') fri_1_subject," + 
				"	s2fri.mst_subject_id fri_2_subject_id," + 
				"	IFNULL(s2fri.subject,'') fri_2_subject," + 
				"	t1fri.teacher_id fri_1_teacher_id," + 
				"	IFNULL(t1fri.teacher,'') fri_1_teacher," + 
				"	t2fri.teacher_id fri_2_teacher_id," + 
				"	IFNULL(t2fri.teacher,'') fri_2_teacher, " + 
				"	s1sat.mst_subject_id sat_1_subject_id," + 
				"	IFNULL(s1sat.subject,'') sat_1_subject," + 
				"	s2sat.mst_subject_id sat_2_subject_id," + 
				"	IFNULL(s2sat.subject,'') sat_2_subject," + 
				"	t1sat.teacher_id sat_1_teacher_id," + 
				"	IFNULL(t1sat.teacher,'') sat_1_teacher," + 
				"	t2sat.teacher_id sat_2_teacher_id," + 
				"	IFNULL(t2sat.teacher,'') sat_2_teacher," + 
				"	s1sun.mst_subject_id sun_1_subject_id," + 
				"	IFNULL(s1sun.subject,'') sun_1_subject," + 
				"	s2sun.mst_subject_id sun_2_subject_id," + 
				"	IFNULL(s2sun.subject,'') sun_2_subject," + 
				"	t1sun.teacher_id sun_1_teacher_id," + 
				"	IFNULL(t1sun.teacher,'') sun_1_teacher," + 
				"	t2sun.teacher_id sun_2_teacher_id," + 
				"	IFNULL(t2sun.teacher,'') sun_2_teacher	" + 
				"	FROM org_mst_timing_period mtp" + 
				"	JOIN "+schoolId+table+"_timetable_periods tp ON mtp.timing_period_id = tp.timing_period_id" + 
				"	LEFT JOIN mst_subject s1mon ON tp.mon_1_subject_id = s1mon.mst_subject_id" + 
				"	LEFT JOIN mst_subject s2mon ON tp.mon_2_subject_id = s1mon.mst_subject_id" + 
				"	LEFT JOIN teacher t1mon ON tp.mon_1_teacher_id = t1mon.teacher_id" + 
				"	LEFT JOIN teacher t2mon ON tp.mon_2_teacher_id = t2mon.teacher_id" + 
				"	LEFT JOIN mst_subject s1tue ON tp.tue_1_subject_id = s1tue.mst_subject_id" + 
				"	LEFT JOIN mst_subject s2tue ON tp.tue_2_subject_id = s1tue.mst_subject_id" + 
				"	LEFT JOIN teacher t1tue ON tp.tue_1_teacher_id = t1tue.teacher_id" + 
				"	LEFT JOIN teacher t2tue ON tp.tue_2_teacher_id = t2tue.teacher_id" + 
				"	LEFT JOIN mst_subject s1wed ON tp.wed_1_subject_id = s1wed.mst_subject_id" + 
				"	LEFT JOIN mst_subject s2wed ON tp.wed_2_subject_id = s1wed.mst_subject_id" + 
				"	LEFT JOIN teacher t1wed ON tp.wed_1_teacher_id = t1wed.teacher_id" + 
				"	LEFT JOIN teacher t2wed ON tp.wed_2_teacher_id = t2wed.teacher_id" + 
				"	LEFT JOIN mst_subject s1thu ON tp.thu_1_subject_id = s1thu.mst_subject_id" + 
				"	LEFT JOIN mst_subject s2thu ON tp.thu_2_subject_id = s1thu.mst_subject_id" + 
				"	LEFT JOIN teacher t1thu ON tp.thu_1_teacher_id = t1thu.teacher_id" + 
				"	LEFT JOIN teacher t2thu ON tp.thu_2_teacher_id = t2thu.teacher_id" + 
				"	LEFT JOIN mst_subject s1fri ON tp.fri_1_subject_id = s1fri.mst_subject_id" + 
				"	LEFT JOIN mst_subject s2fri ON tp.fri_2_subject_id = s1fri.mst_subject_id" + 
				"	LEFT JOIN teacher t1fri ON tp.fri_1_teacher_id = t1fri.teacher_id" + 
				"	LEFT JOIN teacher t2fri ON tp.fri_2_teacher_id = t2fri.teacher_id" + 
				"   LEFT JOIN mst_subject s1sat ON tp.sat_1_subject_id = s1sat.mst_subject_id" + 
				"	LEFT JOIN mst_subject s2sat ON tp.sat_2_subject_id = s1sat.mst_subject_id" + 
				"	LEFT JOIN teacher t1sat ON tp.sat_1_teacher_id = t1sat.teacher_id" + 
				"	LEFT JOIN teacher t2sat ON tp.sat_2_teacher_id = t2sat.teacher_id" + 
				"	LEFT JOIN mst_subject s1sun ON tp.sun_1_subject_id = s1sun.mst_subject_id" + 
				"	LEFT JOIN mst_subject s2sun ON tp.sun_2_subject_id = s1sun.mst_subject_id" + 
				"	LEFT JOIN teacher t1sun ON tp.sun_1_teacher_id = t1sun.teacher_id" + 
				"	LEFT JOIN teacher t2sun ON tp.sun_2_teacher_id = t2sun.teacher_id" + 
				"	WHERE timetable_id = ? order by tp.timing_period_id;";
				
		try {
			List<TimetablePeriods> list = temp.query(SQL,new Object[]{timetableId}, new TimetablePeriodsMapper(1));
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Available for the timetable "+timetableId);
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstTimetablePeriods",list);
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

	public Map<String, Object> insertNewTimetable(String timetable, int wingId, int classSectionId,String table) 
	{
		final String SQL = "INSERT INTO "+schoolId+table+"_timetable(timetable,wing_id,class_section_id) VALUES (?,?,?)";

		int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = temp.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
					
					ps.setString(1, timetable);
					ps.setInt(2, wingId);
					ps.setInt(3, classSectionId);
					
					return ps;
				}
			}, holder);
		} catch (Exception e) {
			logger.error("insertTimetable:Exception occured:" + timetable + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in saving timetable. Contact System Admin");
		}

		if (count > 0) {
			Map<String, Object> data = new HashMap<>();
			data.put("timetableId", holder.getKey().intValue());
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "New Timetable Saved");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Timetable could not be saved. Contact System Admin");
		}
	}

	public int updateTimetable(String timetable, int timetableId,String table) 
	{
		String SQL = "UPDATE "+schoolId+table+"_timetable SET timetable = ? WHERE timetable_id = ?";
		try {
			return temp.update(SQL, new Object[]{timetable , timetableId});
		}
		catch(Exception e)
		{
			logger.error("updateTimetable:Exception occured:" + timetable + ": " + e.getMessage());
			return 0;
		}
	}

	public int deleteTimetablePeriods(int timetableId,String table) 
	{
		String SQL = "DELETE FROM "+schoolId+table+"_timetable_periods WHERE timetable_id = ?";
		try {
			return temp.update(SQL, new Object[]{timetableId});
		}
		catch(Exception e)
		{
			logger.error("deleteTimetablePeriods:Exception occured:" + timetableId + ": " + e.getMessage());
			return 0;
		}
	}

	public int insertNewTimetablePeriods(TimetablePeriods per, int timetableId,String table) 
	{
		String SQL = "INSERT INTO "+schoolId+table+"_timetable_periods(timetable_id,"
				+ "timing_period_id,"
				+ "mon_1_subject_id,"
				+ "tue_1_subject_id,"
				+ "wed_1_subject_id,"
				+ "thu_1_subject_id,"
				+ "fri_1_subject_id,"
				+ "sat_1_subject_id,"
				+ "sun_1_subject_id,"
				+ "mon_1_teacher_id,"
				+ "tue_1_teacher_id,"
				+ "wed_1_teacher_id,"
				+ "thu_1_teacher_id,"
				+ "fri_1_teacher_id,"
				+ "sat_1_teacher_id,"
				+ "sun_1_teacher_id,"
				+ "mon_2_subject_id,"
				+ "tue_2_subject_id,"
				+ "wed_2_subject_id,"
				+ "thu_2_subject_id,"
				+ "fri_2_subject_id,"
				+ "sat_2_subject_id,"
				+ "sun_2_subject_id,"
				+ "mon_2_teacher_id,"
				+ "tue_2_teacher_id,"
				+ "wed_2_teacher_id,"
				+ "thu_2_teacher_id,"
				+ "fri_2_teacher_id,"
				+ "sat_2_teacher_id,"
				+ "sun_2_teacher_id"
				+ ") "
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			return temp.update(SQL, new Object[]{
				timetableId,
				per.getTimingPeriodId(),
				per.getMon1subId(),
				per.getTue1subId(),
				per.getWed1subId(),
				per.getThu1subId(),
				per.getFri1subId(),
				per.getSat1subId(),
				per.getSun1subId(),
				per.getMon1teacherId(),
				per.getTue1teacherId(),
				per.getWed1teacherId(),
				per.getThu1teacherId(),
				per.getFri1teacherId(),
				per.getSat1teacherId(),
				per.getSun1teacherId(),
				per.getMon2subId(),
				per.getTue2subId(),
				per.getWed2subId(),
				per.getThu2subId(),
				per.getFri2subId(),
				per.getSat2subId(),
				per.getSun2subId(),
				per.getMon2teacherId(),
				per.getTue2teacherId(),
				per.getWed2teacherId(),
				per.getThu2teacherId(),
				per.getFri2teacherId(),
				per.getSat2teacherId(),
				per.getSun2teacherId()
			});
		}
		catch(Exception e)
		{
			logger.error("insertTimetablePeriods:Exception occured:" + per.getTimetableId() + ": " + e.getMessage());
			return 0;
		}	
	}
	
	public Map<String, Object> getTimingPeriods(int wingId) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = " SELECT mtp.timing_period_id," + 
				" mtp.period," + 
				" mtp.duration," + 
				" DATE_FORMAT(mtp.start_tm,'%H:%i') as start_time," + 
				" DATE_FORMAT(ADDTIME(DATE_FORMAT(mtp.start_tm,'%H:%i'),SEC_TO_TIME(mtp.duration * 60)),'%H:%i') as end_time," + 
				" mtp.period_type" +
				" FROM "+schoolId+"_mst_timing mt" + 
				" JOIN "+schoolId+"_mst_timing_period mtp ON mtp.timing_id = mt.timing_id"+
				" WHERE wing_id = ?";
				
		try {
			List<TimetablePeriods> list = temp.query(SQL,new Object[]{wingId}, new TimetablePeriodsMapper(0));
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Available");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("timingPeriods",list);
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

	public Map<String, Object> selectClassSubjects(String classId) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "SELECT ms.mst_subject_id subject_id,"
				+ " ms.subject"
				+ " FROM "+schoolId+"_class_subjects cs"
				+ " JOIN mst_subject ms ON ms.mst_subject_id = cs.subject_id"
				+ " WHERE cs.class_id = ?";
				
		try {
			List<TimetableClassSubjects> list = temp.query(SQL,new Object[]{classId}, new TimetableClassSubjectsMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Subjects available for this class");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstSubjects",list);
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

	public Map<String, Object> selectClassTeachers(String classId) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "SELECT t.teacher_id,"
				+ " t.teacher"
				+ " FROM "+schoolId+"_teacher_class ts"
				+ " JOIN teacher t ON t.teacher_id = ts.teacher_id"
				+ " WHERE ts.class_id = ?";
				
		try {
			List<TimetableClassTeachers> list = temp.query(SQL,new Object[]{classId}, new TimetableClassTeachersMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Teachers available for this class");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstTeachers",list);
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
