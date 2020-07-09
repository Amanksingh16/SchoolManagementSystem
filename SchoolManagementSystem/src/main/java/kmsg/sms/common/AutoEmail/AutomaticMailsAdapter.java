package kmsg.sms.common.AutoEmail;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kmsg.sms.admin.daoimpl.SchoolDaoImpl;
import kmsg.sms.common.Constants;
import kmsg.sms.common.DaoHandler;
import kmsg.sms.common.EmailType;
import kmsg.sms.common.Encode;
import kmsg.sms.common.GetProperties;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SendMail;
import kmsg.sms.common.SvcStatus;

@Component
public class AutomaticMailsAdapter implements SMSLogger
{
	@Autowired
	AutomaticMailsDao dao;
	
	@Autowired
	SchoolDaoImpl schoolDao;
	
	@Scheduled(fixedDelay = 10000, initialDelay = 1000)
	public void SendAutomaticMails()
	{
		Map<String, Object> map = dao.getPendingMails();
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			@SuppressWarnings("unchecked")
			List<AutomaticMailsModel> list = (List<AutomaticMailsModel>) map.get("lstEmails");
			
			for(int i = 0;i < list.size();i++)
			{
				AutomaticMailsModel mails = list.get(i);
				String subject = "", body="";
				
				switch(mails.getType())
				{
					case EmailType.SCHOOL_VERIFICATION:
					{
						subject = "VERIFICATION FOR SCHOOL REGISTRATION";
						
						body =  "Dear Sir/Madam,<br><br>"
								+ "<b>This Mail is for the verification purpose of School's Email</b><br>"
								+ "<a href=\""+Constants.MAIL_VERIFY_LINK+"\">Click on this link to verify</a>";
					}
					break;
					
					case EmailType.TEACHER_VERIFICATION:
					{
						subject = "VERIFICATION FOR NEW TEACHER REGISTRATION";
						
						body =  "Dear Sir/Madam,<br><br>"
								+ "<b>This Mail is for the verification purpose of Teacher's Email</b><br>"
								+ "<a href=\""+Constants.MAIL_VERIFY_LINK+"\">Click on this link to verify</a>";
					}
					break;
					
					case EmailType.STUDENT_VERIFICATION:
					{
						subject = "VERIFICATION FOR NEW STUDENT REGISTRATION";
						
						body =  "Dear Sir/Madam,<br><br>"
								+ "<b>This Mail is for the verification purpose of Student's Email</b><br>"
								+ "<a href=\""+Constants.MAIL_VERIFY_LINK+"\">Click on this link to verify</a>";
					}
					break;
					
					case EmailType.SCHOOL_FORGOTPASSWORD:
					{
						String newGeneratedPwd		=	Constants.forgotPassword.get(mails.getIdValue());
						
						subject = "Request for School New Password";
						
						body =  "<br>Dear School Admin,<br><br>Your username is "+mails.getEmail()+"  and your Password is "+ newGeneratedPwd+ 
								"<br><br>Thank You<br>School Management System Team";
					}
					break;
					
					case EmailType.TEACHER_FORGOTPASSWORD:
					{
						String newGeneratedPwd		=	Constants.forgotPassword.get(mails.getIdValue());
						
						subject = "Request for Teacher New Password";
						
						body =  "<br>Dear Teacher,<br><br>Your username is "+mails.getEmail()+"  and your Password is "+ newGeneratedPwd+ 
								"<br><br>Thank You<br>School Management System Team";
					}
					break;
					
					case EmailType.STUDENT_FORGOTPASSWORD:
					{
						String newGeneratedPwd		=	Constants.forgotPassword.get(mails.getIdValue());
						
						subject = "Request for Student New Password";
						
						body =  "<br>Dear Student,<br><br>Your username is "+mails.getEmail()+"  and your Password is "+ newGeneratedPwd+ 
								"<br><br>Thank You<br>School Management System Team";
					}
					break;
				}
				GetProperties getProperties = new GetProperties();
				Properties prop = getProperties.getPropValues();
				
				final String host   =   prop.getProperty("host");
		    	final String port   =   prop.getProperty("port");
		    	String from         = 	prop.getProperty("email");
				String mailPassword = 	prop.getProperty("password");
				try {
					mailPassword = 	Encode.decrypt(mailPassword);
					
					if(!SendMail.sendEmail(host, port, from, mailPassword, mails.getEmail(),  subject, body))
					{
						logger.error("Unable To Send Mail to userEmail: "+mails.getEmail());
					}
					else
					{
						if(!dao.updateMail(Constants.SENT,mails.getAutoEmailId()))
							logger.error("Not Able to update mail info: "+mails.getEmail());
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error("Exception occurred in Sending Mail to userEmail: "+mails.getEmail()+" due to "+e);
				}
			}
			logger.info("New Mails Sent!");
		}
	}

	public Map<String, Object> verifyUpdate(String automaticMailId) 
	{
		DaoHandler dh = new DaoHandler();
		dh.start(new Object(){}.getClass().getEnclosingMethod().getName());
		try {
			int check = dao.checkForExistingVerify(Integer.parseInt(automaticMailId));
			if(check == Constants.CLICKED)
				return SvcStatus.GET_FAILURE("Email Already Verified");
			
			if(!dao.updateVerifyMail(Integer.parseInt(automaticMailId)))
			{
				dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
				logger.error("Not Able to update verification mail for : "+automaticMailId);
				return SvcStatus.GET_FAILURE("Something went wrong in email verification");
			}
			
			int schoolId = schoolDao.getIdFromAutomailId(Integer.parseInt(automaticMailId));
			
			if(!schoolDao.updateEmailVerification(schoolId))
			{
				dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
				logger.error("Not Able to update school verification mail for : "+automaticMailId);
				return SvcStatus.GET_FAILURE("Something went wrong in school email verification");
			}
			dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
			return SvcStatus.GET_SUCCESS("Email Verified Successfully");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
			logger.error("Exception occurred in email verificaton due to "+e);
			return SvcStatus.GET_FAILURE("Something went wrong in email verification");
		}
	}
}
