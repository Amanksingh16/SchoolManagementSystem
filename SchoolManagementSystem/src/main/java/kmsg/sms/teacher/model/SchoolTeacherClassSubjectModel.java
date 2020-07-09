package kmsg.sms.teacher.model;

public class SchoolTeacherClassSubjectModel {
	private int teacherId;
	private int teacherClassId;
	private int schoolId;
	private int subjectId;
	private int teacherClassSubjectId;
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public int getTeacherClassId() {
		return teacherClassId;
	}
	public void setTeacherClassId(int teacherClassId) {
		this.teacherClassId = teacherClassId;
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
	
	public SchoolTeacherClassSubjectModel(int teacherClassSubjectId, int subjectId) {
		super();
		this.setTeacherClassSubjectId(teacherClassSubjectId) ;
		this.subjectId = subjectId;
	}

	public SchoolTeacherClassSubjectModel() {
		
	}
	
	
}
