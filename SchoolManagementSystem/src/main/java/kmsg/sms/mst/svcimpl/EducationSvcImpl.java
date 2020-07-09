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

import kmsg.sms.common.SvcStatus;
import kmsg.sms.redis.session.RedisSession;
import kmsg.sms.mst.adapter.EducationAdapter;
import kmsg.sms.mst.svcint.EducationSvcInt;

@RestController
@RequestMapping("/sms/mst/education")
public class EducationSvcImpl implements EducationSvcInt
{
	@Autowired
	EducationAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@RequestMapping(value="/list", method = RequestMethod.GET, headers="Accept=application/json")
	public Map<String, Object> getEducationList(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			return adapter.getEducationList();
		}
		return map;
	}
}
