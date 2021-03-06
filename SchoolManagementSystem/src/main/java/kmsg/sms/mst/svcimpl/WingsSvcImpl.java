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

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.adapter.WingsAdapter;
import kmsg.sms.mst.svcint.WingsSvcInt;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/wings")
public class WingsSvcImpl implements WingsSvcInt, SMSLogger
{
	@Autowired
	WingsAdapter adapter;
	
    @Autowired
    private RedisSession ses;
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> listWing(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int)map.get("schoolId");
			adapter.setSchoolId(schoolId);
			
			return adapter.selectWings();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveWing(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String wings = params.get("wings");
			
			return adapter.addNewWing(wings);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/ses/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> listWingSessions(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int)map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String wingId = params.get("wingId");
			return adapter.selectWingSessions(wingId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/ses/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveWingSessions(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String wingSession = params.get("wingSession");
			
			return adapter.saveWingSession(wingSession);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/year/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> listAcademicYear(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String wingId = params.get("wingId");
			
			return adapter.selectAcademicYear(wingId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/year/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveAcademicYear(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String wings = params.get("academicYear");
			
			return adapter.addAcademicYear(wings);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/sch/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> listAcademicSchedule(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String wingId = params.get("wingId");
			String academicYearId = params.get("academicYearId");
			
			return adapter.selectAcademicSchedule(wingId,academicYearId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/sch/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveAcademicSchedule(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String academicSchedule = params.get("academicSchedule");
			
			return adapter.addAcademicSchedule(academicSchedule);
		}
		return map;
	}

	@Override
	@RequestMapping(value="/cls/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getWingsClasses(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			int wingId=Integer.parseInt( params.get("wingId"));
			adapter.setSchoolId(schoolId);
			return adapter.getClasses(wingId);
		}
		return map;
	}

	@Override
	@RequestMapping(value="/cls/section/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getWingsClassesSections(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			int classId=Integer.parseInt(params.get("classId"));
			adapter.setSchoolId(schoolId);
			return adapter.getClassSections(classId);
		}
		return map;
	}
}
