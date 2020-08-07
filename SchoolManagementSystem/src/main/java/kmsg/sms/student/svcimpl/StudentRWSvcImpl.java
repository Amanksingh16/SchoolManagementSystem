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

import kmsg.sms.student.adapter.StudentRWAdapter;
import kmsg.sms.student.svcint.StudentRWSvcInt;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/student")
public class StudentRWSvcImpl implements StudentRWSvcInt {
	
	@Autowired
	StudentRWAdapter studentAdapter;

	@Autowired
	RedisSession ses;
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveStudent( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();
		String student =  params.get("student");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.saveStudent( student );
		}
		return map;
	}

	@Override
	@RequestMapping(value="/save_parents", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveParents( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();
		
		String studentParents =  params.get("studentParents");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.saveStudentParents( studentParents );
		}
		return map;
	}

	@Override
	@RequestMapping(value="/save_sibling", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveSibling( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();
		
		String studentSibling =  params.get("studentSibling");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.saveStudentSibling( studentSibling );
		}
		return map;
	}

	@Override
	@RequestMapping(value="/remove_sibling", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> removeSibling( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		String schoolSibling = params.get("schoolSibling");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.removeStudentSibling( schoolSibling);
		}
		return map;
	}

	@Override
	@RequestMapping(value="/save_history", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveHistory( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		String studentHistory =  params.get("studentHistory");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.saveStudentHistory( studentHistory);
		}
		return map;
	}

	@Override
	@RequestMapping(value="/save_medical", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveMedicalHistory( @RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		String studentMedical = params.get("studentMedical");

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return studentAdapter.saveStudentMedical( studentMedical);
		}
		return map;
	}
}
