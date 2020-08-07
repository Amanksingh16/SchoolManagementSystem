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

import kmsg.sms.mst.adapter.TimingAdapter;
import kmsg.sms.mst.svcint.TimingSvcInt;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/mst/timing")
public class TimingSvcImpl implements TimingSvcInt
{
	@Autowired
	TimingAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getTimingList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getTimingList();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveTiming(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String timing = params.get("timing");
			return adapter.saveTiming(timing);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/period/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getTimingPeriodList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String timingId = params.get("timingId");
			return adapter.getTimingPeriodList(timingId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/period/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveTimingPeriod(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String timingPeriod = params.get("timingPeriod");
			return adapter.saveTimingPeriod(timingPeriod);
		}
		return map;
	}
}
