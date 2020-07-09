package kmsg.sms.teacher.model;

public class TeacherTaskModel {

	private int teacherTaskId;
	private int taskId;
	private int teacherId;
	private String teacher;
	private String status;
	
	public int getTeacherTaskId() {
		return teacherTaskId;
	}
	public void setTeacherTaskId(int teacherTaskId) {
		this.teacherTaskId = teacherTaskId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
