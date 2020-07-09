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
import kmsg.sms.mst.adapter.FeesAdapter;
import kmsg.sms.mst.svcint.FeesSvcInt;

@RestController
@RequestMapping("/sms/mst/fee")
public class FeesSvcImpl implements FeesSvcInt
{
	@Autowired
	FeesAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@Override
	@RequestMapping(value="/head/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getFeeHeads(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getFeeHeads();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/cls/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getClassFee(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId = (int) map.get("schoolId");
			String wingId = params.get("wingId");
			adapter.setSchoolId(schoolId);
			return adapter.getClassFee(wingId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/head/cls/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getClassFeeHeads(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId = (int) map.get("schoolId");
			String classId = params.get("classId");
			adapter.setSchoolId(schoolId);
			return adapter.getClassFeeHeads(classId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/head/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveFeeHeads(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String feeHead = params.get("feeHead");
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.saveFeeHeads(feeHead);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/head/cls/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveClassFeeHeads(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String feeClassHead = params.get("feeClassHead");
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.saveFeeClassHeads(feeClassHead);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/head/delete", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> DeleteFeeHead(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String feeHeadId = params.get("feeHeadId");
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.DeleteFeeHead(feeHeadId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/head/cls/delete", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> DeleteClassFeeHead(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String classId = params.get("classId");
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.DeleteFeeClassHead(classId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/head/stu", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> GetClassFeeStudents(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String classId = params.get("classId");
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.SelectClassFeeStudents(classId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/head/stu/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> SaveClassFeeStudents(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			String feeHeadStudent = params.get("feeHeadStudent");
			int schoolId = (int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.saveStudentFeeHeads(feeHeadStudent);
		}
		return map;
	}
}