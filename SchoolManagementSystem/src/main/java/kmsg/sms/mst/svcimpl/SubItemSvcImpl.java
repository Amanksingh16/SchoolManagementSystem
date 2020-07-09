package kmsg.sms.mst.svcimpl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getSubItemList(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String itemId = params.get("itemId");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getSubItemList(itemId);
		}
		return map;
	}

	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveSubItem(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String subitem = params.get("subitem");
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.addSubItem(subitem);
		}
		return map;
	}
}
