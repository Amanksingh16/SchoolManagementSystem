package kmsg.sms.student.svcimpl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kmsg.sms.student.adapter.StudentAdapter;
import kmsg.sms.student.svcint.StudentSvcInt;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/student")
public class StudentSvcImpl implements StudentSvcInt {
	
	@Autowired
	StudentAdapter studentAdapter;

	@Autowired
	RedisSession ses;
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getAllStudents( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();
		
		int schoolId = Integer.parseInt(params.get("schoolId"));

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.getAllStudents(schoolId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/dtls", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getPersonalDtls( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		String schoolStudent = params.get("schoolStudent");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.getDtls( schoolStudent);
		}
		return map;
	}

	@Override
	@RequestMapping(value="/parents", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getParents( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		String schoolStudent = params.get("schoolStudent");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.getParents(schoolStudent);
		}
		return map;
	}

	@Override
	@RequestMapping(value="/list_siblings", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getSiblings( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		String schoolStudent = params.get("schoolStudent");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.getSiblings( schoolStudent);
		}
		return map;
	}
}
