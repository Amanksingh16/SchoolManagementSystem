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
import kmsg.sms.mst.adapter.ItemsAdapter;
import kmsg.sms.mst.svcint.ItemsSvcInt;
import kmsg.sms.redis.session.RedisSession;


@RestController
@RequestMapping("/sms/item")
public class ItemsSvcImpl implements ItemsSvcInt{
	
	@Autowired
	ItemsAdapter adapter;
	
	@Autowired
	RedisSession ses;

	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getItemList(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getItemList();
		}
		return map;
	}

	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveItem(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response) {
	
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String items = params.get("items");
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.addItem(items);
		}
		return map;
	}

}
