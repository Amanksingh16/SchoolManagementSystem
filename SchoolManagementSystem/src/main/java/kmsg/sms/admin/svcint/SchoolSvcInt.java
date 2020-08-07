package kmsg.sms.admin.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface SchoolSvcInt {

	Map<String, Object> CreateSchool(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> CreateSchoolSystem(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> SendPhoneOTP(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> VerifyPhone(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> SchoolLogin(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> forgotPassword(Map<String, String> params, HttpSession session, HttpServletRequest request);

	Map<String, Object> changePassword(Map<String, String> params, HttpSession session, HttpServletRequest request);

	Map<String, Object> logout(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

}
