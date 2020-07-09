package kmsg.sms.mst.model;

public class ManageClass {
	
	private int classSectionId;
	private int classId;
	private int wingId;
	private String wing;
	private String classLabel;
	private String section;
	private int classTeacherId;
	private String classTeacher;
	private int PrefectStudentId;
	private String prefectStudent;
	private int MonitorStudentId;
	private String monitorStudent;
	private int buildingId;
	private String buildingName;
	private int roomId;
	private String room;
	private String floor;
	private int maxStudents;
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getClassTeacherId() {
		return classTeacherId;
	}
	public void setClassTeacherId(int classTeacherId) {
		this.classTeacherId = classTeacherId;
	}
	public int getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getMaxStudents() {
		return maxStudents;
	}
	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}
	public int getClassSectionId() {
		return classSectionId;
	}
	public void setClassSectionId(int classSectionId) {
		this.classSectionId = classSectionId;
	}
	public String getClassLabel() {
		return classLabel;
	}
	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}
	public String getClassTeacher() {
		return classTeacher;
	}
	public void setClassTeacher(String classTeacher) {
		this.classTeacher = classTeacher;
	}
	public int getPrefectStudentId() {
		return PrefectStudentId;
	}
	public void setPrefectStudentId(int prefectStudentId) {
		PrefectStudentId = prefectStudentId;
	}
	public String getPrefectStudent() {
		return prefectStudent;
	}
	public void setPrefectStudent(String prefectStudent) {
		this.prefectStudent = prefectStudent;
	}
	public int getMonitorStudentId() {
		return MonitorStudentId;
	}
	public void setMonitorStudentId(int monitorStudentId) {
		MonitorStudentId = monitorStudentId;
	}
	public String getMonitorStudent() {
		return monitorStudent;
	}
	public void setMonitorStudent(String monitorStudent) {
		this.monitorStudent = monitorStudent;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public int getWingId() {
		return wingId;
	}
	public void setWingId(int wingId) {
		this.wingId = wingId;
	}
	public String getWing() {
		return wing;
	}
	public void setWing(String wing) {
		this.wing = wing;
	}
}
