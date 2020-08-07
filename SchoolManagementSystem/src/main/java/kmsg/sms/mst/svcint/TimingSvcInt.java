package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface TimingSvcInt {

	Map<String, Object> getTimingList(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveTiming(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveTimingPeriod(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getTimingPeriodList(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

}
