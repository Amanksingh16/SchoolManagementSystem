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
import kmsg.sms.mst.adapter.DocTypeAdapter;
import kmsg.sms.mst.svcint.DocTypeSvcInt;

@RestController
@RequestMapping("/sms/mst/doctype")
public class DocTypeSvcImpl implements DocTypeSvcInt
{
	@Autowired
	DocTypeAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@RequestMapping(value="/list", method = RequestMethod.GET, headers="Accept=application/json")
	public Map<String, Object> getDocTypeList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			return adapter.getDocTypeList();
		}
		return map;
	}
}
