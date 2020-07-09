package kmsg.sms.mst.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoint.BuildingDaoInt;
import kmsg.sms.mst.mapper.BuildingMapper;
import kmsg.sms.mst.mapper.BuildingRoomMapper;
import kmsg.sms.mst.model.Building;
import kmsg.sms.mst.model.BuildingRooms;

@Repository
public class BuildingDaoImpl implements BuildingDaoInt,SMSLogger{

	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	
	@Override
	public Map<String, Object> getBuildingList() {
		final String SQL = 
				" SELECT building_id,"
				+ " building_name,"
				+ " no_of_floors, "
				+ "building_img_path"
				+ " FROM "+schoolId+"_mst_building";
		
		List<Building> list = new ArrayList<>();
		try {
	
			 
			list = template.query(SQL, new BuildingMapper());
		} 
		catch (EmptyResultDataAccessException e) {
			System.out.println(e);
			logger.error("selectInvoices: No Building found");
			return SvcStatus.GET_FAILURE("No Building found. Contact System admin");
		}
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectPeriodType: Exception in selecting Building " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Building. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstBuildings",  list );
		return result ;	
}
	
	@Override
	public Map<String, Object> saveBuilding(Building model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_mst_building (building_name,no_of_floors,building_img_path) VALUES (?,?,?)";

	    int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			 count = template.update (
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement ps = conn.prepareStatement(
													SQL,
													Statement.RETURN_GENERATED_KEYS
																	);
						ps.setString( 1, model.getBuildingName());
						ps.setInt( 2, model.getNoOfFloors());
						ps.setString( 3, model.getBuildingPath());
						
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertBuilding : Duplicate Key insertBuilding:" + model.getBuildingId());
			return SvcStatus.GET_FAILURE("Building already exist" +  model.getBuildingId());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertBuilding :Exception occured in insertBuilding:" + model.getBuildingId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Building. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put("buildingId", holder.getKey().intValue());
			data.put(SvcStatus.MSG, "New Building Added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Building could not be added. Contact System Admin");
		}
	}
	
	@Override
	public Map<String, Object> updateBuilding(Building model) 
	{
		final String SQL = "UPDATE "+schoolId+"_mst_building SET building_name = ? ,"
				+ " no_of_floors = ?,"
				+ " building_img_path = CASE WHEN '"+model.getBuildingPath()+"' <> 'null' THEN ? else building_img_path END"
				+ " WHERE building_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
						model.getBuildingName(),
						model.getNoOfFloors(),
						model.getBuildingPath(),
						model.getBuildingId(),
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateBuilding : Duplicate Key for this Building:" + model.getBuildingName());
			return SvcStatus.GET_FAILURE("Building already exist");
		}
		catch(Exception e) {
			logger.error("updateBuilding :Exception occured in updateBuilding:" + model.getBuildingName() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating Building. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Building Updated");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updateBuilding : Failed to update Building :" +  model.getBuildingName());
			return SvcStatus.GET_FAILURE("Building could not be updated. Contact System Admin");
		}

	}

	@Override
	public Map<String, Object> getBuildingRoomList(int buildingId) 
	{
		final String SQL = 
				" SELECT building_id,"
				+ " room_id,"
				+ " room,"
				+ " floor,"
				+ " dimensions,"
				+ " capacity,"
				+ " room_type_id "
				+ " FROM "+schoolId+"_building_rooms WHERE building_id = ?";
		
		List<BuildingRooms> list = new ArrayList<>();
		try {		 
			list = template.query(SQL,new Object[]{buildingId}, new BuildingRoomMapper());
		} 
		catch (EmptyResultDataAccessException e) {
			logger.error("selectBuildingRooms: No Building Rooms found");
			return SvcStatus.GET_FAILURE("No Building Rooms found. Contact System admin");
		}
		catch (Exception e) {
			logger.error("selectBuildingRooms: Exception in selecting Building Rooms " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Building Rooms. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstBuildingRooms",  list );
		return result ;	
	}

	public String selectBuildingImgPath(int buildingId)
	{
		String SQL = "SELECT building_img_path FROM "+schoolId+"_mst_building WHERE building_id = ?";
		try {
			return template.queryForObject(SQL,new Object[] {buildingId}, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}

	@Override
	public Map<String, Object> saveBuildingRoom(BuildingRooms model) {
		// TODO Auto-generated method stub
		final String SQL = "INSERT INTO "+schoolId+"_building_rooms (building_id,floor,room,dimensions,room_type_id,capacity) VALUES (?,?,?,?,?,?)";

	    int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			 count = template.update (
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement ps = conn.prepareStatement(
													SQL,
													Statement.RETURN_GENERATED_KEYS
																	);
						ps.setInt( 1, model.getBuildingId());
						ps.setString( 2, model.getFloor());
						ps.setString( 3, model.getRoom());
						ps.setString( 4, model.getDimensions());
						ps.setInt( 5, model.getRoomTypeId());
						ps.setInt( 6, model.getCapacity());
						
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertRoom : Duplicate Key insertRoom:" + model.getRoomId());
			return SvcStatus.GET_FAILURE("Room already exist" +  model.getRoomId());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertRoom :Exception occured in insertRoom:" + model.getRoomId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Room. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put("roomId", holder.getKey().intValue());
			data.put(SvcStatus.MSG, "Room is added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Room could not be added. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> updateBuildingRoom(BuildingRooms model) {

		final String SQL = "UPDATE "+schoolId+"_building_rooms SET building_id = ? ,"
				+ " floor = ?,"
				+ "room=?,"
				+ "dimensions=?,"
				+ "room_type_id=?,"
				+ "capacity=? "
				+ " WHERE room_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
						model.getBuildingId(),
						model.getFloor(),
						model.getRoom(),
						model.getDimensions(),
						model.getRoomTypeId(),
						model.getCapacity(),
						model.getRoomId(),
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateBuildingRoom : Duplicate Key for this BuildingId:" + model.getBuildingId());
			return SvcStatus.GET_FAILURE("Room Name already exist");
		}
		catch(Exception e) {
			logger.error("updateBuildingRoom :Exception occured in updateBuildingRoom:" + model.getBuildingId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating Building Room. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Building Room Updated");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("updateBuildingRoom : Failed to update updateBuildingRoom :" +  model.getBuildingId());
			return SvcStatus.GET_FAILURE("Building Room could not be updated. Contact System Admin");
		}
	}
}
