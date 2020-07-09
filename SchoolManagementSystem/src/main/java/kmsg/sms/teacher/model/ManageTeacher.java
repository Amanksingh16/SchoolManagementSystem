package kmsg.sms.teacher.model;

public class ManageTeacher {
	private int teacherId;
	private String teacher;
	private int schoolId;
	private String gender;
	private String dob;
	private String doj;
	private boolean married;
	private int experience;
	private String address1;
	private String address2;
	private int cityId;
	private int stateId;
	private String mobileNo;
	private String email;
	private String salt;
	private String password;
	private String emergencyContactNo;
	private boolean blocked;
	
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
	public int getSchoolId() {
		return schoolId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public boolean isMarried() {
		return married;
	}
	public void setMarried(boolean married) {
		this.married = married;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}
	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public ManageTeacher() {
	
	}
	public ManageTeacher(int teacherId, String teacher, String gender, String dob, String doj,
			boolean married, int experience, String address1, String address2, int cityId, int stateId, String mobileNo,
			String email, String emergencyContactNo) {
		super();
		this.teacherId = teacherId;
		this.teacher = teacher;
		this.gender = gender;
		this.dob = dob;
		this.doj = doj;
		this.married = married;
		this.experience = experience;
		this.address1 = address1;
		this.address2 = address2;
		this.cityId = cityId;
		this.stateId = stateId;
		this.mobileNo = mobileNo;
		this.email = email;
		this.emergencyContactNo = emergencyContactNo;
	}
	
}
