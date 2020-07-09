package kmsg.sms.admin.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import kmsg.sms.admin.daoimpl.AdminDaoImpl;
import kmsg.sms.admin.model.AdminModel;
import kmsg.sms.common.Constants;
import kmsg.sms.common.Encode;
import kmsg.sms.common.GetProperties;
import kmsg.sms.common.SHAHandler;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SendMail;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.common.Util;

@Component
public class AdminAdapter implements SMSLogger
{
	@Autowired
	AdminDaoImpl dao;
	
	public Map<String, Object> adminLogin(AdminModel admin) 
	{
		Map<String,Object> data = new HashMap<>();
		data = dao.selectUserDetails(admin.getEmail());	
		
		if(data.get(Constants.STATUS).equals(Constants.SUCCESS))
		{
			AdminModel model = (AdminModel) data.get("adminModel");
			
			if(!dao.updateAdminLastActive(model.getAdminId()))
			{
				data.put(Constants.STATUS, Constants.FAILURE);
				data.put(Constants.MESSAGE, "Oops,Something Went Wrong,Please Contact system Administrator");
				logger.error("Exception found While Admin Login due to,unable to update Last Active for: "+model.getAdminId());
				return data;
			}
			
			String PasswordAndSalt = admin.getPassword()+model.getSalt();
			String hashPassword    = SHAHandler.generateHash(PasswordAndSalt);
			
			if(hashPassword.equals(model.getPassword()))
			{	
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "LoggedIn Successfully");
				return data;
			}
			else
			{
				return SvcStatus.GET_FAILURE("Invalid Username/Password");
			}
		}
		else
			return data;
	}

	public Map<String, Object> forgotPassword(String userEmail) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String newGeneratedPwd		=	Util.generateRandomPassword();
		String salt 				= 	SHAHandler.getNextSalt().toString();
		String originalPwdHash 		=	SHAHandler.generateHash(newGeneratedPwd + salt);

		try
		{
			if(dao.forgotPassword(userEmail,originalPwdHash,salt))
			{
				GetProperties getProperties = new GetProperties();
				Properties prop = getProperties.getPropValues();
				
				final String host   =   prop.getProperty("host");
		    	final String port   =   prop.getProperty("port");
		    	String from         = 	prop.getProperty("email");
				String mailPassword = 	prop.getProperty("password");
				mailPassword = 	Encode.decrypt(mailPassword);
				String  subject		= 	"Your Password for SMS app";
				String  mailBody	= 	"<br>Namaste!<br><br>Your username is "+userEmail+"  and your Password is "+ newGeneratedPwd+
						              	"<br><br>Thank You<br>School Management System Team";
				
				if(!SendMail.sendEmail(host, port, from, mailPassword, userEmail,  subject, mailBody))
				{
					data.put(Constants.STATUS, Constants.FAILURE);
					data.put(Constants.MESSAGE, "Oops Something Went Wrong,Please Contact System Administrator");
					logger.error("Unable To Send Mail to userEmail: "+userEmail);
					return data;
				}
			}
			else
			{
				data.put(Constants.STATUS, Constants.FAILURE);
				data.put(Constants.MESSAGE, "User Does Not Exist. Please Check Your Userid");
				logger.error("Userid Does Not exist For emailId: "+userEmail);
				return data;
			}
		}
		catch(EmptyResultDataAccessException e)
		{
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE, "User Does Not Exist,Please Check Your Userid");
			logger.error("Userid Does Not exist For emailId: "+userEmail);
			return data;
		}
		catch(Exception e)
		{
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE, "Oops Something Went Wrong,Please Contact System Administrator");
			logger.error("Exception Found While Forgot Password due to: "+e);
			e.printStackTrace();
			return data;
		}
		
		data.put(Constants.STATUS, Constants.SUCCESS);
		data.put(Constants.MESSAGE, "Check your Email for new password");
		return data;
	}

	public Map<String, Object> changePassword(String emailId, String oldPassword, String newPassword) 
	{
		Map<String,Object> data = new HashMap<>();
		String salt = dao.getSalt(emailId);
		String oldPasswordHash = SHAHandler.generateHash(oldPassword+salt);
		String newPasswordHash = SHAHandler.generateHash(newPassword+salt);

		try
		{
			if(!dao.changePassword(emailId,oldPasswordHash,newPasswordHash))
			{
				data.put(Constants.STATUS, Constants.FAILURE);
				data.put(Constants.MESSAGE, "Wrong EmailId/Password,Please Enter Correct Credentials");
				logger.error("Exception Caused change Password emailId: "+emailId+" oldPasswordHash:  "+oldPasswordHash+"  newPasswordHash: "+newPasswordHash);
				return data;
			}
		}
		catch(Exception e)
		{
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE, "Oops,Something Went Wrong,Please Contact System Administrator");
			logger.error("Exception Caught While Changing Password,Due to: "+e);
			return data;
		}
		
		data.put(Constants.STATUS, Constants.SUCCESS);
		data.put(Constants.MESSAGE, "Password Has Been Changed Successfully");
		return data;
	}
}
