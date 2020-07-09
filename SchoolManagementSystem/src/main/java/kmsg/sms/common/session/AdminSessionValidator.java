package kmsg.sms.common.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmsg.sms.admin.daoimpl.AdminDaoImpl;
import kmsg.sms.admin.model.AdminModel;
import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AdminSessionValidator implements SMSLogger
{
	public String MESSAGE = "You are not logged in";
	
	@Autowired
	AdminDaoImpl dao;
	
	public void createSession(AdminModel data, HttpSession sessionObj) 
	{
		if (data != null) 
		{
			AdminSessionData data1 = new AdminSessionData();
			
			data1.setName(data.getName());
			data1.setAdminId(data.getAdminId());
			data1.setSessionID(UUID.randomUUID());
			sessionObj.setAttribute("SessionAdminData", data1); 
			
			AdminSessionData.lstSessionData.add(data1);
		}
	}
	
	public Map<String,Object> validateAdminSession(HttpSession session, HttpServletRequest request, String method)
	{	
		Map<String,Object> map = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		AdminSessionData	currentSession	=  (AdminSessionData) session.getAttribute("SessionAdminData");
		
		if(currentSession == null)
		{
			map.put(Constants.STATUS,Constants.FAILURE);
			map.put(Constants.MESSAGE,"You are not Logged In");
			return map;	
		}
		
		UUID sessionID			= currentSession.getSessionID();
		int admin_Id			= currentSession.getAdminId();
			
		for(int i=0; i< AdminSessionData.lstSessionData.size(); i++)
		{
			int adminid	 =	AdminSessionData.lstSessionData.get(i).getAdminId();
			UUID sessionId =	AdminSessionData.lstSessionData.get(i).getSessionID();
			
			if(admin_Id == adminid && sessionID.equals(sessionId))
			{
				String lastActivity = "";
				String name = AdminSessionData.lstSessionData.get(i).getName();
				
				try
				{
					lastActivity = dao.getAdminLastActive(adminid); 
				}
				catch(Exception e)
				{
					e.printStackTrace();
					dao.updateAdminLastActive(adminid);
					logger.error("Exception Caught While Getting Last Activity due to: "+ e);
					map.put(Constants.STATUS,Constants.FAILURE);
					map.put(Constants.MESSAGE,"Error occured in getting Admin Last Active");
					return map;
				}
				
				try 
				{
					Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastActivity);
					
					Date date = new Date();
					long diff = date.getTime() - date1.getTime();
					long diffMinutes = diff / (60 *  1000);
					if(diffMinutes <= 360)
					{
						if(!dao.updateAdminLastActive(adminid))
						{
							logger.error("Last Activity: "+lastActivity);
							logger.error("Last Activity Date : "+date1);
							logger.error("Original Date : "+date);
							logger.error("Diff Minutes : "+diffMinutes);
							logger.error("Error In Session Validator Screen: Failed To Update Last Activity for: "+adminid);
							map.put(Constants.STATUS,Constants.FAILURE);
							map.put(Constants.MESSAGE,"Error occurred in updating Admin last Active");
							return map;
						}
						logger.debug("User "+name+" with admin ID "+admin_Id+" called "+method+" service at "+format.format(date.getTime()));
						
						map.put(Constants.STATUS,Constants.SUCCESS);
						return map;
					}
					else
					{
						logger.error("Last Activity: "+lastActivity);
						logger.error("Last Activity Date : "+date1);
						logger.error("Original Date : "+date);
						logger.error("Diff Minutes : "+diffMinutes);
						dao.updateAdminLastActive(adminid);
						map.put(Constants.STATUS,Constants.FAILURE);
						map.put(Constants.MESSAGE,"Server Timed Out, Login Again");
						return map;
					}
				} 
				catch (Exception e) 
				{
					dao.updateAdminLastActive(adminid);
					logger.error("Exception Caught While Calculating Difference due to: "+ e);
					map.put(Constants.STATUS,Constants.FAILURE);
					map.put(Constants.MESSAGE,"Exception Caught While Calculating Difference");
					return map;
				}
				
			  }
			}
			map.put(Constants.STATUS,Constants.FAILURE);
			map.put(Constants.MESSAGE,"You are not Logged In");
			return map;
		}
				
	
	public Map<String, Object> logout(HttpSession session)
	{			
		Map <String, Object> data = new HashMap<>();

		AdminSessionData	currentSession	=	(AdminSessionData) session.getAttribute("SessionAdminData"); 
		 
		if(currentSession == null)
		{
			data.put("SvcMsg", "You are not logged in");
			return data;	
		}
		
		int adminId			= currentSession.getAdminId();
		UUID sessionID			= currentSession.getSessionID();
		
		for(int i=0; i< AdminSessionData.lstSessionData.size(); i++)
		{
			int adminid	= 	AdminSessionData.lstSessionData.get(i).getAdminId();
			UUID sessionId	=	AdminSessionData.lstSessionData.get(i).getSessionID();
			
			if(adminId == adminid && sessionID.equals(sessionId))
				{		
					AdminSessionData.lstSessionData.remove(i);
					session.removeAttribute("SessionData");	
					data.put(Constants.STATUS, "Success");
					data.put(Constants.MESSAGE, "Logout Successful");
					break;
				}
				else
				{
					data.put(Constants.STATUS, "Failure");
					data.put(Constants.MESSAGE, "Cannot logout successfully");
				}
		}
		return data;		
	}
}
