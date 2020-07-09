package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import kmsg.sms.mst.model.AcademicSchedule;

public class AcademicScheduleMapper implements RowMapper<AcademicSchedule>
{
	@Override
	public AcademicSchedule mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		AcademicSchedule sch = new AcademicSchedule();
		sch.setAcademicScheduleId(rs.getInt("academic_schedule_id"));
		sch.setStartDt(rs.getString("start_dt"));
		sch.setEndDt(rs.getString("to_dt"));
		sch.setType(rs.getString("type"));
		
		return sch;
	}
}
