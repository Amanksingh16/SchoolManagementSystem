package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

public interface StateSvcInt {
	public Map<String, Object> getStateList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response);
}
