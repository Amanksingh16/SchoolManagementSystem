package kmsg.sms.mst.model;

public class EducationModel {
	private int educationId;
	private String education;
	public int getEducationId() {
		return educationId;
	}
	public void setEducationId(int educationId) {
		this.educationId = educationId;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public EducationModel(int educationId, String education) {
		super();
		this.educationId = educationId;
		this.education = education;
	}
	
}
