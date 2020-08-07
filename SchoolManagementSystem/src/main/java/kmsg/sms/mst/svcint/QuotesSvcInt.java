package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface QuotesSvcInt {

	Map<String, Object> saveQuote(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getQuoteList(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getQuoteItemList(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> saveQuoteItem(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getQuoteSupplierList(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> saveQuoteSupplier(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getQuoteSupplierItemList(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response);

}
