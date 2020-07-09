package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface WingsSvcInt {

	Map<String, Object> saveWing(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> listWing(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveAcademicYear(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> saveAcademicSchedule(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> listAcademicSchedule(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> listAcademicYear(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

}
