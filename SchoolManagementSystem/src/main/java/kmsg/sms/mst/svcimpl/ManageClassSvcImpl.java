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

import kmsg.sms.mst.adapter.ManageClassAdapter;
import kmsg.sms.mst.svcint.ManageClassSvcInt;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/cls")
public class ManageClassSvcImpl implements ManageClassSvcInt
{
	@Autowired
	ManageClassAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@RequestMapping(value="/wing/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getWingsClassList(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String wingId = params.get("wingId");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			
			return adapter.getWingClasses(Integer.parseInt(wingId));
		}
		return map;
	}
	
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getAllClassSections(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			
			return adapter.getAllClassSections();
		}
		return map;
	}
	
	@RequestMapping(value="/sec/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getClassSections(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String classId = params.get("classId");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			
			return adapter.getClassSections(Integer.parseInt(classId));
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveClassSection(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			
			String timing = params.get("classSection");
			return adapter.addClassSection(timing);
		}
		return map;
	}
}
