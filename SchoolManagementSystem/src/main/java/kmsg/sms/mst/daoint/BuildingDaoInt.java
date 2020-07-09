package kmsg.sms.mst.daoint;

import java.util.Map;

import kmsg.sms.mst.model.Building;
import kmsg.sms.mst.model.BuildingRooms;

public interface BuildingDaoInt {
	Map<String, Object> getBuildingList();

	Map<String, Object> updateBuilding(Building model);

	Map<String, Object> saveBuilding(Building model);

	Map<String, Object> updateBuildingRoom(BuildingRooms model);

	Map<String, Object> getBuildingRoomList(int buildingId);

	Map<String, Object> saveBuildingRoom(BuildingRooms s);

	void setSchoolId(int schoolId);
}
