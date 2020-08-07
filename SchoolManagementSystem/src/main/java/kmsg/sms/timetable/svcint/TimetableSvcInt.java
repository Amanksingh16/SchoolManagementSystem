package kmsg.sms.timetable.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface TimetableSvcInt {

	Map<String, Object> getTimeTable(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveTimeTable(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> PublishTimeTable(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getClassSubjects(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getClassTeachers(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

}
