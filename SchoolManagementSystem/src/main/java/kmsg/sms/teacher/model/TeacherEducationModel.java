package kmsg.sms.teacher.model;

public class TeacherEducationModel {

	private int teacherId;
	private int teacherEducationId;
	private int educationId;

	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getTeacherEducationId() {
		return teacherEducationId;
	}
	public void setTeacherEducationId(int teacherEducationId) {
		this.teacherEducationId = teacherEducationId;
	}
	public int getEducationId() {
		return educationId;
	}
	public void setEducationId(int educationId) {
		this.educationId = educationId;
	}
	public TeacherEducationModel(int teacherId, int teacherEducationId, int educationId) {
		super();
		this.teacherId = teacherId;
		this.teacherEducationId = teacherEducationId;
		this.educationId = educationId;
	}
	public TeacherEducationModel() {

	}
	
	
}
