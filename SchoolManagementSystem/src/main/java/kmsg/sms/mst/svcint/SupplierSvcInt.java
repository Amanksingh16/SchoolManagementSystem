package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface SupplierSvcInt {
	
	Map<String, Object> getSupplierList(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);
	
	Map<String, Object> saveSupplier(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> GenerateSupplierPassword(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

}
