package kmsg.sms.admin.daoint;

public interface SchoolDaoInt {

	String getSalt(String emailId);

	boolean changePassword(String emailId, String oldPasswordHash, String newPasswordHash);

	boolean forgotPassword(String email, String originalPwdHash, String salt);

	int getId(String emailId);

	int getIdFromAutomailId(int automaticMailId);

}
