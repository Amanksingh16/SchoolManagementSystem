package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.TimetableClassSubjects;

public class TimetableClassSubjectsMapper implements RowMapper<TimetableClassSubjects>
{

	@Override
	public TimetableClassSubjects mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		TimetableClassSubjects sub = new TimetableClassSubjects();
		sub.setSubjectId(rs.getInt("subject_id"));
		sub.setSubject(rs.getString("subject"));
//		sub.setTimeAlloted(rs.getString("time_alloted"));
//		sub.setSyllabusCovered(rs.getBoolean("syllabus_covered"));
//		sub.setRemarks(rs.getString("remarks"));
		return sub;
	}
}
