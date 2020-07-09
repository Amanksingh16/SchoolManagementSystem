package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import kmsg.sms.mst.model.Building;

public class BuildingMapper implements RowMapper<Building> {
	
	@Override
	public Building mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Building model = new Building();
	
	  model.setBuildingId(rs.getInt("building_id"));
	  model.setBuildingName(rs.getString("building_name"));
	  model.setNoOfFloors(rs.getInt("no_of_floors"));
	 
		return model;
	}
}
