package kmsg.sms.admin.svcimpl;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.admin.adapter.SchoolAdapter;
import kmsg.sms.admin.model.School;
import kmsg.sms.admin.svcint.SchoolSvcInt;
import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.Util;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/school")
public class SchoolSvcImpl implements SchoolSvcInt, SMSLogger
{
	@Autowired
	SchoolAdapter adapter;
	
    @Autowired
    RedisSession ses;
	
	@Override
	@RequestMapping(value="/add", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> CreateSchool(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		logger.info(CurrMethod+" service called at "+Util.Now());
		String school = params.get("school");

		return adapter.insertSchoolSystem(school);
	}
	
	@Override
	@RequestMapping(value="/create", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> CreateSchoolSystem(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		logger.info(CurrMethod+" service called at "+Util.Now());
		String school = params.get("school");

		return adapter.createSchoolSystem(school);
	}
	
	@Override
	@RequestMapping(value="/sendphoneotp", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> SendPhoneOTP(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		logger.info(CurrMethod+" service called at "+Util.Now());
		String phoneNo = params.get("phoneNo");

		return adapter.sendPhoneOTP(phoneNo);
	}
	
	@Override
	@RequestMapping(value="/verifyphone", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> VerifyPhone(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		logger.info(CurrMethod+" service called at "+Util.Now());
		String phoneNo = params.get("phoneNo");
		String otp = params.get("otp");

		return adapter.verifyPhone(phoneNo,otp);
	}
	
	@Override
	@RequestMapping(value="/login", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> SchoolLogin(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String school = params.get("school");
		String msg = "Exception Occured in School Login";
		School obj = new School();
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			obj = mapper.readValue(school, School.class);
			map = adapter.schoolLogin(obj);
			
			String svcstatus	=	(String) map.get(Constants.STATUS);
			
			if("Success".equals(svcstatus))
			{
				obj	=	(School) map.get("schoolModel");
				String token = ses.createSession(obj, session.getId(), request.getRemoteAddr());
				response.setHeader("tokenId", token);
			}
			map.remove("schoolModel");
			return map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(Constants.STATUS,Constants.FAILURE);
			map.put(Constants.MESSAGE,msg);
			return map;
		}
	}
	
	@Override
	@RequestMapping(value = "/forgotpassword",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> forgotPassword(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request) 
	{
		Map<String, Object> data 			= 	new HashMap<>();
	
		String userEmail       =   params.get("email");
		
		try
		{
			return adapter.forgotPassword(userEmail);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE, "Oops,Something Went Wrong,Please Contact System Administrator");
			logger.error("Exception Caught, In service forgotpassword due to: "+e);
			return data;
		}
	}
	
	@Override
	@RequestMapping(value = "/changepassword",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> changePassword(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request) 
	{
		Map<String, Object> data 			= 	new HashMap<>();
		
		String emailId       =   params.get("email");
		String oldPassword   =   params.get("oldPassword");
		String newPassword   =   params.get("newPassword");
		
		try
		{
			return adapter.changePassword(emailId,oldPassword,newPassword);
		}
		catch(Exception e)
		{
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE, "Oops,Something Went Wrong,Please Contact System Administrator");
			logger.error("Exception Caught At Service changePassword due to: "+e);
			return data;
		}
	}
	
	@Override
	@RequestMapping(value = "/logout" , method = RequestMethod.GET)
	public Map<String, Object> logout(@RequestParam Map<String, String> params,HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{		
		return ses.logout(params.get("tokenId"));
	}
	
}
