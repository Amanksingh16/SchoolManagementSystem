package kmsg.sms.admin.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.admin.model.School;

public class SchoolMapper implements RowMapper<School>
{
	@Override
	public School mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		School sch = new School();
		
		sch.setSchoolId(rs.getInt("school_id"));
		sch.setPassword(rs.getString("password"));
		sch.setSalt(rs.getString("salt"));
		sch.setSchool(rs.getString("school"));
		
		return sch;
	}
}
