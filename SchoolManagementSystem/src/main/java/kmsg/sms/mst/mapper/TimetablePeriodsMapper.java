package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.TimetablePeriods;

public class TimetablePeriodsMapper implements RowMapper<TimetablePeriods>
{
	private int checkTimetable;
	
	public TimetablePeriodsMapper(int checkTimetable) {
		this.checkTimetable = checkTimetable;
	}

	@Override
	public TimetablePeriods mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		TimetablePeriods period = new TimetablePeriods();
		period.setDuration(rs.getInt("duration"));
		period.setEndTm(rs.getString("end_time"));
		period.setStartTm(rs.getString("start_time"));
		period.setTimingPeriodId(rs.getInt("timing_period_id"));
		period.setPeriod(rs.getString("period"));
		period.setPeriodType(rs.getString("period_type"));
		
		if(checkTimetable == 1)
		{
			period.setTimetableId(rs.getInt("timetable_id"));
			period.setMon1subId(rs.getInt("mon_1_subject_id"));
			period.setMon1sub(rs.getString("mon_1_subject"));
			period.setMon2subId(rs.getInt("mon_2_subject_id"));
			period.setMon2sub(rs.getString("mon_2_subject"));
			period.setMon1teacherId(rs.getInt("mon_1_teacher_id"));
			period.setMon1teacher(rs.getString("mon_1_teacher"));
			period.setMon2teacherId(rs.getInt("mon_2_teacher_id"));
			period.setMon2teacher(rs.getString("mon_2_teacher"));
			period.setTue1subId(rs.getInt("tue_1_subject_id"));
			period.setTue1sub(rs.getString("tue_1_subject"));
			period.setTue2subId(rs.getInt("tue_2_subject_id"));
			period.setTue2sub(rs.getString("tue_2_subject"));
			period.setTue1teacherId(rs.getInt("tue_1_teacher_id"));
			period.setTue1teacher(rs.getString("tue_1_teacher"));
			period.setTue2teacherId(rs.getInt("tue_2_teacher_id"));
			period.setTue2teacher(rs.getString("tue_2_teacher"));
			period.setWed1subId(rs.getInt("wed_1_subject_id"));
			period.setWed1sub(rs.getString("wed_1_subject"));
			period.setWed2subId(rs.getInt("wed_2_subject_id"));
			period.setWed2sub(rs.getString("wed_2_subject"));
			period.setWed1teacherId(rs.getInt("wed_1_teacher_id"));
			period.setWed1teacher(rs.getString("wed_1_teacher"));
			period.setWed2teacherId(rs.getInt("wed_2_teacher_id"));
			period.setWed2teacher(rs.getString("wed_2_teacher"));
			period.setThu1subId(rs.getInt("thu_1_subject_id"));
			period.setThu1sub(rs.getString("thu_1_subject"));
			period.setThu2subId(rs.getInt("thu_2_subject_id"));
			period.setThu2sub(rs.getString("thu_2_subject"));
			period.setThu1teacherId(rs.getInt("thu_1_teacher_id"));
			period.setThu1teacher(rs.getString("thu_1_teacher"));
			period.setThu2teacherId(rs.getInt("thu_2_teacher_id"));
			period.setThu2teacher(rs.getString("thu_2_teacher"));
			period.setFri1subId(rs.getInt("fri_1_subject_id"));
			period.setFri1sub(rs.getString("fri_1_subject"));
			period.setFri2subId(rs.getInt("fri_2_subject_id"));
			period.setFri2sub(rs.getString("fri_2_subject"));
			period.setFri1teacherId(rs.getInt("fri_1_teacher_id"));
			period.setFri1teacher(rs.getString("fri_1_teacher"));
			period.setFri2teacherId(rs.getInt("fri_2_teacher_id"));
			period.setFri2teacher(rs.getString("fri_2_teacher"));
			period.setSat1subId(rs.getInt("sat_1_subject_id"));
			period.setSat1sub(rs.getString("sat_1_subject"));
			period.setSat2subId(rs.getInt("sat_2_subject_id"));
			period.setSat2sub(rs.getString("sat_2_subject"));
			period.setSat1teacherId(rs.getInt("sat_1_teacher_id"));
			period.setSat1teacher(rs.getString("sat_1_teacher"));
			period.setSat2teacherId(rs.getInt("sat_2_teacher_id"));
			period.setSat2teacher(rs.getString("sat_2_teacher"));
			period.setSun1subId(rs.getInt("sun_1_subject_id"));
			period.setSun1sub(rs.getString("sun_1_subject"));
			period.setSun2subId(rs.getInt("sun_2_subject_id"));
			period.setSun2sub(rs.getString("sun_2_subject"));
			period.setSun1teacherId(rs.getInt("sun_1_teacher_id"));
			period.setSun1teacher(rs.getString("sun_1_teacher"));
			period.setSun2teacherId(rs.getInt("sun_2_teacher_id"));
			period.setSun2teacher(rs.getString("sun_2_teacher"));
		}
		return period;
	}
}
