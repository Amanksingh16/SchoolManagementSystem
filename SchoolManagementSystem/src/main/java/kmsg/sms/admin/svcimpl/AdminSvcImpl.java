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

import kmsg.sms.admin.adapter.AdminAdapter;
import kmsg.sms.admin.model.AdminModel;
import kmsg.sms.admin.svcint.AdminSvcInt;
import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.session.AdminSessionValidator;

@RestController
@RequestMapping("/sms/admin")
public class AdminSvcImpl implements AdminSvcInt, SMSLogger
{
	@Autowired
	AdminAdapter adapter;
	
	@Override
	@RequestMapping(value="/login", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> AdminLogin(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String admin = params.get("admin");
		AdminModel model = new AdminModel();
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			model = mapper.readValue(admin, AdminModel.class);
			map = adapter.adminLogin(model);
			
			String svcstatus	=	(String) map.get(Constants.STATUS);
			
			if("Success".equals(svcstatus))
			{
				model	=	(AdminModel) map.get("adminModel");
			
				AdminSessionValidator sessionObject	=	new AdminSessionValidator();
				sessionObject.createSession(model, session);
				
			}
			map.remove("adminModel");
			return map;
		}
		catch(Exception e)
		{
			map.put(Constants.STATUS,Constants.FAILURE);
			map.put(Constants.MESSAGE,"Exception Occured in Admin Login");
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
	public Map<String, Object> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{		
		AdminSessionValidator logoutSession	= new AdminSessionValidator();
		return logoutSession.logout(session);
	}
}
