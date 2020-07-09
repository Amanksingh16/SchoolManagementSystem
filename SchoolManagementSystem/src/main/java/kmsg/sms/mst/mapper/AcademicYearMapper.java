package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.AcademicYear;

public class AcademicYearMapper implements RowMapper<AcademicYear>
{
	@Override
	public AcademicYear mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		AcademicYear year = new AcademicYear();
		year.setWingId(rs.getInt("wing_id"));
		year.setAcademicYear(rs.getString("academic_year"));
		year.setStartDt(rs.getString("start_dt"));
		year.setEndDt(rs.getString("to_dt"));
		year.setLocked(rs.getBoolean("locked"));
		
		return year;
	}
}
