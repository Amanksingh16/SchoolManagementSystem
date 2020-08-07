package kmsg.sms.timetable.model;

public class TimetableClassTeachers 
{
	private int teacherId;
	private String teacher;
	private String timeAlloted;
	private boolean syllabusCovered;
	private String remarks;
	
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getTimeAlloted() {
		return timeAlloted;
	}
	public void setTimeAlloted(String timeAlloted) {
		this.timeAlloted = timeAlloted;
	}
	public boolean isSyllabusCovered() {
		return syllabusCovered;
	}
	public void setSyllabusCovered(boolean syllabusCovered) {
		this.syllabusCovered = syllabusCovered;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
