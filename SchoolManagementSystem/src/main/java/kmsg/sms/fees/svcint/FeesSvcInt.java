package kmsg.sms.fees.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface FeesSvcInt {

	Map<String, Object> getFeeHeads(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveClassFeeHeads(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getClassFeeHeads(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> saveFeeHeads(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> DeleteClassFeeHead(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> DeleteFeeHead(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> GetClassFeeStudents(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getClassFee(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> SaveClassFeeStudents(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

}
