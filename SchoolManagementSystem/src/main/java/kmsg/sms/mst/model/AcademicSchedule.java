package kmsg.sms.mst.model;

public class AcademicSchedule
{
	private int wingId;
	private int academicYearId;
	private int academicScheduleId;
	private String startDt;
	private String endDt;
	private String type;
	
	public int getWingId() {
		return wingId;
	}
	public void setWingId(int wingId) {
		this.wingId = wingId;
	}
	public int getAcademicYearId() {
		return academicYearId;
	}
	public void setAcademicYearId(int academicYearId) {
		this.academicYearId = academicYearId;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public int getAcademicScheduleId() {
		return academicScheduleId;
	}
	public void setAcademicScheduleId(int academicScheduleId) {
		this.academicScheduleId = academicScheduleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
