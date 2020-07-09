package kmsg.sms.common.AutoEmail;

public class AutomaticMailsModel 
{
	private int autoEmailId;
	private String email;
	private int idValue;
	private int type;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIdValue() {
		return idValue;
	}
	public void setIdValue(int idValue) {
		this.idValue = idValue;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAutoEmailId() {
		return autoEmailId;
	}
	public void setAutoEmailId(int autoEmailId) {
		this.autoEmailId = autoEmailId;
	}
}
