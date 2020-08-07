package kmsg.sms.student.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

public interface StudentSvcInt {
	public Map<String, Object> getAllStudents(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
	public Map<String, Object> getPersonalDtls(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
	public Map<String, Object> getParents(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
	public Map<String, Object> getSiblings(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
}
