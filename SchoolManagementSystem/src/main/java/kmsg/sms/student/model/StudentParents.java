package kmsg.sms.student.model;

public class StudentParents {
	private int	studentId ;
	private int schoolId ;
	private Person father;
	private Person mother;
	private Person guardian;
	
	public Person getFather() {
		return father;
	}
	public void setFather(Person father) {
		this.father = father;
	}
	public Person getMother() {
		return mother;
	}
	public void setMother(Person mother) {
		this.mother = mother;
	}
	public Person getGuardian() {
		return guardian;
	}
	public void setGuardian(Person guardian) {
		this.guardian = guardian;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

}
