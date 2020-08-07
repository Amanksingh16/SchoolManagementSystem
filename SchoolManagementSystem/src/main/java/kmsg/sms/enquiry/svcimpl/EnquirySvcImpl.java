package kmsg.sms.enquiry.svcimpl;

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
import kmsg.sms.enquiry.adapter.EnquiryAdapter;
import kmsg.sms.enquiry.svcint.EnquirySvcInt;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/enquiry")
public class EnquirySvcImpl implements EnquirySvcInt, SMSLogger
{
	@Autowired
	EnquiryAdapter adapter;
	
    @Autowired
    private RedisSession ses;
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> listEnquiry(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int)map.get("schoolId");
			adapter.setSchoolId(schoolId);
			
			return adapter.getEnquiry();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveEnquiry(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId( schoolId);
			String strEnquiry = params.get("enquiry");
			
			return adapter.saveEnquiry(strEnquiry);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/register", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> registerEnquiry(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId( schoolId);
			String enquiryId = params.get("enquiryId");
			String regsNo = params.get("regsNo");
			
			return adapter.registerEnquiry(enquiryId,regsNo);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/conversion", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> ConversionGraph(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int)map.get("schoolId");
			String year = params.get("year");
			adapter.setSchoolId(schoolId);
			
			return adapter.ConversionGraph(year);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/yearcomparison", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> YearComparisonGraph(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int)map.get("schoolId");
			adapter.setSchoolId(schoolId);
			
			return adapter.YearComparisonGraph();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/classcomparison", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> ClassComparisonGraph(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int)map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String wingId = params.get("wingId");
			
			return adapter.ClassComparisonGraph(wingId,schoolId);
		}
		return map;
	}
}
