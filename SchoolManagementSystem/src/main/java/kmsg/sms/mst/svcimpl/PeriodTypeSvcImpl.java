package kmsg.sms.mst.svcimpl;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kmsg.sms.mst.adapter.PeriodTypeAdapter;
import kmsg.sms.common.SvcStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kmsg.sms.mst.svcint.PeriodTypeSvcInt;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/a/type")
public class PeriodTypeSvcImpl implements PeriodTypeSvcInt {
	
	@Autowired
	PeriodTypeAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> TypeList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.TypeList();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> TypeSave(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			String project = params.get("periodType");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.addPeriodType(project);
		}
		return map;
	}
}
