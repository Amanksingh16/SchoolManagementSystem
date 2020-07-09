package kmsg.sms.teacher.model;

public class TeacherClassSubjectModel {
	private int teacherId;
	private int classId;
	private int subjectId;
	private int teacherClassSubjectId;
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
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getTeacherClassSubjectId() {
		return teacherClassSubjectId;
	}
	public void setTeacherClassSubjectId(int teacherClassSubjectId) {
		this.teacherClassSubjectId = teacherClassSubjectId;
	}
	public TeacherClassSubjectModel(int teacherId, int classId, int subjectId) {
		super();
		this.teacherId = teacherId;
		this.classId = classId;
		this.subjectId = subjectId;
	}

	public TeacherClassSubjectModel() {
		
	}
	
	
}
