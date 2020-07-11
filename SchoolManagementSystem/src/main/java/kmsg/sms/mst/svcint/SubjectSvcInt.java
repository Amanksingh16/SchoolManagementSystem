package kmsg.sms.mst.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

public interface SubjectSvcInt {
	public Map<String, Object> getSubjectList(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response);
}
