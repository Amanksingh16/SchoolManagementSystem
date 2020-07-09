package kmsg.sms.teacher.model;

public class TeacherDocModel {

	private int teacherId;
	private int docTypeId;
	private String docPath;
	private Object teacherDocId;
	
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	
	public int getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public TeacherDocModel() {

	}

	public Object getTeacherDocId() {
		return teacherDocId;
	}

	public void setTeacherDocId(Object teacherDocId) {
		this.teacherDocId = teacherDocId;
	}

	public TeacherDocModel(int teacherDocId, int docTypeId, String docPath) {
		super();
		this.setTeacherDocId(teacherDocId) ;
		this.docTypeId = docTypeId;
		this.docPath = docPath;
	}	
}
