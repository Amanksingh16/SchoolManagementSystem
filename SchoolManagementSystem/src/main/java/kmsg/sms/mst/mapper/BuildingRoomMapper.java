package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.common.BuildingRoomType;
import kmsg.sms.mst.model.BuildingRooms;

public class BuildingRoomMapper implements RowMapper<BuildingRooms>
{
	@Override
	public BuildingRooms mapRow(ResultSet rs, int rowNum) throws SQLException {
		BuildingRooms room = new BuildingRooms();
		room.setRoomTypeId(rs.getInt("room_type_id"));
		room.setRoomType(BuildingRoomType.getRoomType(rs.getInt("room_type_id")));
		room.setFloor(rs.getString("floor"));
		room.setRoom(rs.getString("room"));
		room.setDimensions(rs.getString("dimensions"));
		room.setCapacity(rs.getInt("capacity"));
		room.setRoomId(rs.getInt("room_id"));
		room.setBuildingId(rs.getInt("building_id"));
		return room;
	}
}
