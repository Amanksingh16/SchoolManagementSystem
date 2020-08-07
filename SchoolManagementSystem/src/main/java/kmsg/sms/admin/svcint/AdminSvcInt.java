package kmsg.sms.admin.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface AdminSvcInt {

	Map<String, Object> forgotPassword(Map<String, String> params, HttpSession session, HttpServletRequest request);

	Map<String, Object> AdminLogin(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> changePassword(Map<String, String> params, HttpSession session, HttpServletRequest request);

	Map<String, Object> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response);

}
