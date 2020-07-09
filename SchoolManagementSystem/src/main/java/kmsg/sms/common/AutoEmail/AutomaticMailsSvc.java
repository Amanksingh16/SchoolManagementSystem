package kmsg.sms.common.AutoEmail;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;

@RestController
@RequestMapping("/sms/mails")
public class AutomaticMailsSvc implements SMSLogger
{

	@Autowired
	AutomaticMailsAdapter adapter;
	
	@RequestMapping(value = "/verify",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> VerifyMail(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request) 
	{
		Map<String, Object> data 			= 	new HashMap<>();
		
		String automaticMailId       =   params.get("automaticMailId");
		
		try
		{
			return adapter.verifyUpdate(automaticMailId);
		}
		catch(Exception e)
		{
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE, "Oops,Something Went Wrong,Please Contact System Administrator");
			logger.error("Exception Caught At Service verify due to: "+e);
			return data;
		}
	}
}
