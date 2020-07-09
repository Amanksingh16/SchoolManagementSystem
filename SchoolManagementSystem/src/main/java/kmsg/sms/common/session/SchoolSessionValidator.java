package kmsg.sms.common.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kmsg.sms.admin.daoimpl.SchoolDaoImpl;
import kmsg.sms.admin.model.School;
import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SchoolSessionValidator implements SMSLogger
{
	public String MESSAGE = "You are not logged in";
	
	@Autowired
	SchoolDaoImpl dao;
	
	public void createSession(School data, HttpSession sessionObj) 
	{
		if (data != null) 
		{
			SchoolSessionData data1 = new SchoolSessionData();
			
			data1.setName(data.getSchool());
			data1.setSchoolId(data.getSchoolId());
			data1.setSessionID(UUID.randomUUID());
			sessionObj.setAttribute("SessionSchoolData", data1); 
			SchoolSessionData.lstSessionData.add(data1);
		}
	}
	
	public Map<String,Object> validateSchoolSession(HttpSession session, HttpServletRequest request, String method)
	{	
		Map<String,Object> map = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SchoolSessionData	currentSession	=  (SchoolSessionData) session.getAttribute("SessionSchoolData");
		
		if(currentSession == null)
		{
			map.put(Constants.STATUS,Constants.FAILURE);
			map.put(Constants.MESSAGE,"You are not Logged In");
			return map;	
		}
		
		UUID sessionID			= currentSession.getSessionID();
		int school_Id			= currentSession.getSchoolId();
			
		for(int i=0; i< SchoolSessionData.lstSessionData.size(); i++)
		{
			int schoolId	 =	SchoolSessionData.lstSessionData.get(i).getSchoolId();
			UUID sessionId =	SchoolSessionData.lstSessionData.get(i).getSessionID();
			
			if(school_Id == schoolId && sessionID.equals(sessionId))
			{
				String lastActivity = "";
				String name = SchoolSessionData.lstSessionData.get(i).getName();
				
				try
				{
					lastActivity = dao.getSchoolLastActive(schoolId); 
				}
				catch(Exception e)
				{
					e.printStackTrace();
					dao.updateSchoolLastActive(schoolId);
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
						if(!dao.updateSchoolLastActive(schoolId))
						{
							logger.error("Last Activity: "+lastActivity);
							logger.error("Last Activity Date : "+date1);
							logger.error("Original Date : "+date);
							logger.error("Diff Minutes : "+diffMinutes);
							logger.error("Error In Session Validator Screen: Failed To Update Last Activity for: "+schoolId);
							map.put(Constants.STATUS,Constants.FAILURE);
							map.put(Constants.MESSAGE,"Error occurred in updating Admin last Active");
							return map;
						}
						logger.debug("User "+name+" with admin ID "+school_Id+" called "+method+" service at "+format.format(date.getTime()));
						
						map.put("schoolId",currentSession.getSchoolId());
						map.put(Constants.STATUS,Constants.SUCCESS);
						return map;
					}
					else
					{
						logger.error("Last Activity: "+lastActivity);
						logger.error("Last Activity Date : "+date1);
						logger.error("Original Date : "+date);
						logger.error("Diff Minutes : "+diffMinutes);
						dao.updateSchoolLastActive(schoolId);
						map.put(Constants.STATUS,Constants.FAILURE);
						map.put(Constants.MESSAGE,"Server Timed Out, Login Again");
						return map;
					}
				} 
				catch (Exception e) 
				{
					dao.updateSchoolLastActive(schoolId);
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
