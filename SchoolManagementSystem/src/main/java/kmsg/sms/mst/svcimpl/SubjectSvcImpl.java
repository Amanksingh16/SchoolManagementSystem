package kmsg.sms.mst.svcimpl;

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

import kmsg.sms.mst.adapter.SubjectAdapter;
import kmsg.sms.mst.svcint.SubjectSvcInt;
import kmsg.sms.redis.session.RedisSession;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;

@RestController
@RequestMapping("/sms/mst/subject")
public class SubjectSvcImpl implements SubjectSvcInt,SMSLogger {
	@Autowired
	SubjectAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getSubjectList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return adapter.getSubjectList();
		}
		return map;
	}
	

}
