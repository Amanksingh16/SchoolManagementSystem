package kmsg.sms.admin.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.admin.daoimpl.SchoolDaoImpl;
import kmsg.sms.admin.model.School;
import kmsg.sms.common.Constants;
import kmsg.sms.common.DaoHandler;
import kmsg.sms.common.EmailType;
import kmsg.sms.common.SHAHandler;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SendSMSUtility;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.common.Util;
import kmsg.sms.common.AutoEmail.AutomaticMailsDao;
import kmsg.sms.common.AutoEmail.AutomaticMailsModel;

@Component
public class SchoolAdapter implements SMSLogger
{
	@Autowired
	SchoolDaoImpl dao;

	@Autowired
	AutomaticMailsDao emailDao;
	
	public Map<String, Object> insertSchoolSystem(String sch) 
	{
		Map<String, Object> map = new HashMap<>();
		School model = new School();
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			model = mapper.readValue(sch, School.class);
		}
		catch(Exception e)
		{
			logger.error("Exception occured in inserting school system "+e);
			return SvcStatus.GET_FAILURE("Something Went Wrong, Contact System Administrator");
		}
		
		DaoHandler dh = new DaoHandler();
		dh.start(new Object(){}.getClass().getEnclosingMethod().getName());
		
		String salt 				= 	SHAHandler.getNextSalt().toString();
		String originalPwdHash 		=	SHAHandler.generateHash(model.getPassword() + salt);
		
		map = dao.insertSchoolSystem(model.getEmail(),originalPwdHash,salt);	
		
		if(!map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
			return map;
		}
		int schoolId = dao.getId(model.getEmail());
		
		AutomaticMailsModel mails = new AutomaticMailsModel();
		mails.setEmail(model.getEmail());
		mails.setType(EmailType.SCHOOL_VERIFICATION);
		mails.setIdValue(schoolId);
		try
		{
			if(emailDao.addNewMail(mails))
			{
				dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
				return map;
			}
			else
			{
				dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
				return SvcStatus.GET_FAILURE("Verification mail cannot be sent");
			}
		}
		catch(Exception e)
		{
			dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Something Went Wrong in sending verification mail ");
		}
	}

	public Map<String, Object> createSchoolSystem(String school) 
	{
		School model = new School();
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			model = mapper.readValue(school, School.class);
			dao.createSchoolData(model);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured in creating school system "+e);
			return SvcStatus.GET_FAILURE("Something Went Wrong, Contact System Administrator");
		}
		return SvcStatus.GET_SUCCESS("School System Successfully Created");
	}

	public Map<String, Object> sendPhoneOTP(String phoneNo) 
	{
		String otp = Util.genOTP();
		
		if(!SendSMSUtility.sendSms(phoneNo, otp))
		{
			logger.error("Error occured in sending OTP to phoneNo : "+phoneNo);
			return SvcStatus.GET_FAILURE("Something Went Wrong in Sending OTP, Contact System Administrator");
		}
		Constants.phoneOTP.put(phoneNo, otp);
		return SvcStatus.GET_SUCCESS("Check Your Phone for OTP");
	}

	public Map<String, Object> verifyPhone(String phoneNo, String otp) 
	{
		String savedOTP = Constants.phoneOTP.get(phoneNo);
		
		if(savedOTP.equals(otp))
		{
			if(dao.updatePhoneVerification(phoneNo))
				return SvcStatus.GET_SUCCESS("Phone Verification Successful");
			else
				return SvcStatus.GET_FAILURE("Error Occured in Phone Verification");
		}
		else
			return SvcStatus.GET_FAILURE("OTP Does not match");
	}

	public Map<String, Object> schoolLogin(School obj) 
	{
		Map<String,Object> data = new HashMap<>();
		data = dao.selectUserDetails(obj.getEmail());	
		
		if(data.get(Constants.STATUS).equals(Constants.SUCCESS))
		{
			School model = (School) data.get("schoolModel");
			
			if(!dao.updateSchoolLastActive(model.getSchoolId()))
			{
				logger.error("Exception found While Admin Login due to,unable to update Last Active for: "+model.getSchoolId());
				return SvcStatus.GET_FAILURE("Oops,Something Went Wrong,Please Contact system Administrator");
			}
			
			String PasswordAndSalt = obj.getPassword()+model.getSalt();
			String hashPassword    = SHAHandler.generateHash(PasswordAndSalt);
			
			if(hashPassword.equals(model.getPassword()))
			{	
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "LoggedIn Successfully");
				return data;
			}
			else
				return SvcStatus.GET_FAILURE("Invalid Username/Password");
		}
		return data;
	}

	public Map<String, Object> changePassword(String emailId, String oldPassword, String newPassword)
	{
		String salt = dao.getSalt(emailId);
		String oldPasswordHash = SHAHandler.generateHash(oldPassword+salt);
		String newPasswordHash = SHAHandler.generateHash(newPassword+salt);

		try
		{
			if(!dao.changePassword(emailId,oldPasswordHash,newPasswordHash))
			{
				logger.error("Exception Caused change Password emailId: "+emailId+" oldPasswordHash:  "+oldPasswordHash+"  newPasswordHash: "+newPasswordHash);
				return SvcStatus.GET_FAILURE("Wrong EmailId/Password,Please Enter Correct Credentials");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception Caught While Changing Password,Due to: "+e);
			return SvcStatus.GET_FAILURE("Oops,Something Went Wrong,Please Contact System Administrator");
		}
		
		return SvcStatus.GET_SUCCESS("Password Has Been Changed Successfully");
	}

	public Map<String, Object> forgotPassword(String userEmail) 
	{
		String newGeneratedPwd		=	Util.generateRandomPassword();
		String salt 				= 	SHAHandler.getNextSalt().toString();
		String originalPwdHash 		=	SHAHandler.generateHash(newGeneratedPwd + salt);
		
		try
		{
			if(dao.forgotPassword(userEmail,originalPwdHash,salt))
			{
				int schoolId = dao.getId(userEmail);
				
				Constants.forgotPassword.put(schoolId, newGeneratedPwd);
				
				AutomaticMailsModel model = new AutomaticMailsModel();
				model.setEmail(userEmail);
				model.setType(EmailType.SCHOOL_FORGOTPASSWORD);
				model.setIdValue(schoolId);
				
				if(emailDao.addNewMail(model))
					return SvcStatus.GET_SUCCESS("Email Sent Succesfully");
			}
			return SvcStatus.GET_FAILURE("Something Went Wrong in Forgot Password");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception Caught in Forgot Password,Due to: "+e);
			return SvcStatus.GET_FAILURE("Oops,Something Went Wrong,Please Contact System Administrator");
		}
	}
}
