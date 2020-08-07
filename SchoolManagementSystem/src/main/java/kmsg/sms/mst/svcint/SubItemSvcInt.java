package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface SubItemSvcInt {
	
	Map<String, Object> getSubItemList(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);
	
	Map<String, Object> saveSubItem(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getAllSubItemList(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

}
