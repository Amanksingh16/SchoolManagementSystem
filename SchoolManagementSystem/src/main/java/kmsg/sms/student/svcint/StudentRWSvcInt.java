package kmsg.sms.student.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

public interface StudentRWSvcInt {
	public Map<String, Object> saveStudent(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
	public Map<String, Object> saveParents(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
	public Map<String, Object> saveSibling(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
	public Map<String, Object> removeSibling(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
	public Map<String, Object> saveHistory(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
	public Map<String, Object> saveMedicalHistory(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response );
}
