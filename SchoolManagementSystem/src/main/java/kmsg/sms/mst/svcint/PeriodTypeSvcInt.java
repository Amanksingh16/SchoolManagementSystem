package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface PeriodTypeSvcInt {


	Map<String, Object> TypeList(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> TypeSave(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

}
