package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface ItemsSvcInt {
	Map<String, Object> getItemList(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveItem(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);
}
