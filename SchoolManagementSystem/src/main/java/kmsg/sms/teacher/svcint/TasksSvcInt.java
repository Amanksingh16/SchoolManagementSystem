package kmsg.sms.teacher.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface TasksSvcInt {

	Map<String, Object> getTasksList(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveTasks(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);

}
