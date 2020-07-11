package kmsg.sms.mst.model;

public class SubjectModel {
	private int subjectId;
	private String subject;
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
	public SubjectModel(int subjectId, String subject) {
		super();
		this.subjectId = subjectId;
		this.subject = subject;
	}
	
	
}
