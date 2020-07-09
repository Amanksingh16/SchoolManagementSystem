package kmsg.sms.admin.model;

public class SchoolStatus 
{
	private int 	schoolId;
	private String 	firstTimeLogin;
	private int 	phoneNoApproved;
	private int 	emailIdApproved;
	private String 	accountStatus;
	private String 	accountStartedOn;
	private String 	accountVerifiedOn;
	private String 	accountApprovedOn;
	private String 	accountBlockedOn;
	private String 	accountBlockedReason;
	private String 	accountDeletedOn;
	private String 	accountDeletedReason;
	private int 	fromStdId;
	private int 	toStdId;
	
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getFirstTimeLogin() {
		return firstTimeLogin;
	}
	public void setFirstTimeLogin(String firstTimeLogin) {
		this.firstTimeLogin = firstTimeLogin;
	}
	public int getPhoneNoApproved() {
		return phoneNoApproved;
	}
	public void setPhoneNoApproved(int phoneNoApproved) {
		this.phoneNoApproved = phoneNoApproved;
	}
	public int getEmailIdApproved() {
		return emailIdApproved;
	}
	public void setEmailIdApproved(int emailIdApproved) {
		this.emailIdApproved = emailIdApproved;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountStartedOn() {
		return accountStartedOn;
	}
	public void setAccountStartedOn(String accountStartedOn) {
		this.accountStartedOn = accountStartedOn;
	}
	public String getAccountVerifiedOn() {
		return accountVerifiedOn;
	}
	public void setAccountVerifiedOn(String accountVerifiedOn) {
		this.accountVerifiedOn = accountVerifiedOn;
	}
	public String getAccountApprovedOn() {
		return accountApprovedOn;
	}
	public void setAccountApprovedOn(String accountApprovedOn) {
		this.accountApprovedOn = accountApprovedOn;
	}
	public String getAccountBlockedOn() {
		return accountBlockedOn;
	}
	public void setAccountBlockedOn(String accountBlockedOn) {
		this.accountBlockedOn = accountBlockedOn;
	}
	public String getAccountBlockedReason() {
		return accountBlockedReason;
	}
	public void setAccountBlockedReason(String accountBlockedReason) {
		this.accountBlockedReason = accountBlockedReason;
	}
	public String getAccountDeletedOn() {
		return accountDeletedOn;
	}
	public void setAccountDeletedOn(String accountDeletedOn) {
		this.accountDeletedOn = accountDeletedOn;
	}
	public String getAccountDeletedReason() {
		return accountDeletedReason;
	}
	public void setAccountDeletedReason(String accountDeletedReason) {
		this.accountDeletedReason = accountDeletedReason;
	}
	public int getFromStdId() {
		return fromStdId;
	}
	public void setFromStdId(int fromStdId) {
		this.fromStdId = fromStdId;
	}
	public int getToStdId() {
		return toStdId;
	}
	public void setToStdId(int toStdId) {
		this.toStdId = toStdId;
	}
}
