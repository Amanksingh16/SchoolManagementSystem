package kmsg.sms.student.model;

public class StudentMedicalDtls {
	private int studentId;
	private int schoolId;
	
	private float height ;
	private float weight ;
	private String bloodGroup ;
	private String diseases;
	private String eyes ;
	private String ears ;
	private String allergicTo;

	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getDiseases() {
		return diseases;
	}
	public void setDiseases(String diseases) {
		this.diseases = diseases;
	}
	public String getEyes() {
		return eyes;
	}
	public void setEyes(String eyes) {
		this.eyes = eyes;
	}
	public String getEars() {
		return ears;
	}
	public void setEars(String ears) {
		this.ears = ears;
	}
	public String getAllergicTo() {
		return allergicTo;
	}
	public void setAllergicTo(String allergicTo) {
		this.allergicTo = allergicTo;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

}
