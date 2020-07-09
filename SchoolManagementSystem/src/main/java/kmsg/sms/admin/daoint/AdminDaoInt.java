package kmsg.sms.admin.daoint;

import java.util.Map;

public interface AdminDaoInt {

	Map<String, Object> selectUserDetails(String email);

	boolean updateAdminLastActive(int adminId);

	String getAdminLastActive(int adminId);

	boolean forgotPassword(String userEmail, String originalPwdHash, String salt);

	String getSalt(String emailId);

	boolean changePassword(String emailId, String oldPasswordHash, String newPasswordHash);

}
