package kmsg.sms.timetable.model;

public class TimetableClassSubjects 
{
	private int subjectId;
	private String subject;
	private String timeAlloted;
	private boolean syllabusCovered;
	private String remarks;
	
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
