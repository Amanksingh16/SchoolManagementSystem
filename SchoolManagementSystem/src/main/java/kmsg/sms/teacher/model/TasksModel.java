package kmsg.sms.teacher.model;

import java.util.List;

public class TasksModel 
{
	private int taskId;
	private String task;
	private String taskDate;
	private String dueDate;
	private int wingId;
	private String wing;
	private String status;
	private List<TeacherTaskModel> teacherTasks;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public int getWingId() {
		return wingId;
	}
	public void setWingId(int wingId) {
		this.wingId = wingId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TeacherTaskModel> getTeacherTasks() {
		return teacherTasks;
	}
	public void setTeacherTasks(List<TeacherTaskModel> teacherTasks) {
		this.teacherTasks = teacherTasks;
	}
	public String getWing() {
		return wing;
	}
	public void setWing(String wing) {
		this.wing = wing;
	}
}
