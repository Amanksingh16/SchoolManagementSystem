package kmsg.sms.timetable.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.timetable.model.TimetableClassTeachers;

public class TimetableClassTeachersMapper implements RowMapper<TimetableClassTeachers>
{
	@Override
	public TimetableClassTeachers mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		TimetableClassTeachers tch = new TimetableClassTeachers();
		tch.setTeacherId(rs.getInt("teacher_id"));
		tch.setTeacher(rs.getString("teacher"));
//		tch.setTimeAlloted(rs.getString("time_alloted"));
//		tch.setSyllabusCovered(rs.getBoolean("syllabus_covered"));
//		tch.setRemarks(rs.getString("remarks"));
		return tch;
	}
}
