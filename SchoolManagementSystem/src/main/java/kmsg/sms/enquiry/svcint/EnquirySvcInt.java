package kmsg.sms.enquiry.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface EnquirySvcInt {
	Map<String, Object> listEnquiry(Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> saveEnquiry(Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response);
	Map<String, Object> registerEnquiry(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);
	Map<String, Object> ConversionGraph(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);
	Map<String, Object> YearComparisonGraph(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);
	Map<String, Object> ClassComparisonGraph(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

}
