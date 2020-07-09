package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.Timetable;

public class TimetableMapper implements RowMapper<Timetable>
{
	@Override
	public Timetable mapRow(ResultSet rs, int rowNum) throws SQLException {
		Timetable table = new Timetable();
		table.setTimetableId(rs.getInt("timetable_id"));
		table.setTimetable(rs.getString("timetable"));
		table.setPublished(rs.getBoolean("published"));
		return table;
	}
}
