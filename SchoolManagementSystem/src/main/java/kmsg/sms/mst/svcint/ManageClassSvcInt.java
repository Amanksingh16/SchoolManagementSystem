package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface ManageClassSvcInt {

	Map<String, Object> saveClassSection(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

}
