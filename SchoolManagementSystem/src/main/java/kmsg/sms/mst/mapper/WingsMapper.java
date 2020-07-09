package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.Wings;

public class WingsMapper implements RowMapper<Wings>
{
	@Override
	public Wings mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Wings wing = new Wings();
		wing.setWingId(rs.getInt("wing_id"));
		wing.setWing(rs.getString("wing"));
		wing.setFromClassId(rs.getInt("from_std_id"));
		wing.setFromClass(rs.getString("from_std"));
		wing.setToClassId(rs.getInt("to_std_id"));
		wing.setToClass(rs.getString("to_std"));
		String day = rs.getString("day");
		
		wing.setMon((day.charAt(0)=='1'?true:false));
		wing.setTue((day.charAt(1)=='1'?true:false));
		wing.setWed((day.charAt(2)=='1'?true:false));
		wing.setThu((day.charAt(3)=='1'?true:false));
		wing.setFri((day.charAt(4)=='1'?true:false));
		wing.setSat((day.charAt(5)=='1'?true:false));
		wing.setSun((day.charAt(6)=='1'?true:false));
		
		wing.setDay(day);
		return wing;
	}
}
