package kmsg.sms.teacher.model;

public class TeacherClassModel {
	private  int teacherId;
	private int classId;
	private int teacherClassId;
	
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getTeacherClassId() {
		return teacherClassId;
	}
	public TeacherClassModel() {
		
	}
	public TeacherClassModel(int teacherId, int classId) {
		super();
		this.teacherId = teacherId;
		this.classId = classId;
	}
	
}
