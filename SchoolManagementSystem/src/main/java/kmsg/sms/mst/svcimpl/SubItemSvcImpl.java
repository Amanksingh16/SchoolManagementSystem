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
import kmsg.sms.mst.adapter.SubItemAdapter;
import kmsg.sms.mst.svcint.SubItemSvcInt;
import kmsg.sms.redis.session.RedisSession;


@RestController
@RequestMapping("/sms/subitem")
public class SubItemSvcImpl implements SubItemSvcInt{
	
	@Autowired
	SubItemAdapter adapter;
	
	@Autowired
	RedisSession ses;

	@Override
	@RequestMapping(value="/item/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getSubItemList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int) map.get("schoolId");
			String itemId = params.get("itemId");
			adapter.setSchoolId(schoolId);
			return adapter.getSubItemList(itemId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getAllSubItemList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getAllSubItemList();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveSubItem(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int) map.get("schoolId");
			String items = params.get("subitem");
			adapter.setSchoolId(schoolId);
			return adapter.addSubItem(items);
		}
		return map;
	}
}
