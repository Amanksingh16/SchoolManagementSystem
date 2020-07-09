package kmsg.sms.common.AutoEmail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.common.Util;

@Repository
public class AutomaticMailsDao implements SMSLogger
{
	@Autowired
	JdbcTemplate template;
	
	public Map<String, Object> getPendingMails()
	{
		Map<String, Object> result = new HashMap<>();
		
		final String SQL =  "SELECT auto_emails_id,"
				+ " email,"
				+ " id_value,"
				+ " type"
				+ " FROM auto_emails"
				+ " WHERE status = ?";

		List<AutomaticMailsModel> list = new ArrayList<>();
		try {
			list = template.query( SQL,new Object[] {Constants.PENDING}, new AutomaticMailsMapper());
			if(list.size() == 0)
			{
				result.put( SvcStatus.STATUS, SvcStatus.FAILURE);
				result.put( SvcStatus.MSG, "No Data exist");
				return result;
			}
		}
		catch(Exception e) {
			logger.error("selectEmails occured:" + e.getMessage());
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in selecting Emails. Contact system admin") ;
		}
		
		result.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put( "lstEmails", list);
		return result ;
	}
	
	public boolean addNewMail(AutomaticMailsModel model)
	{
		String sql = " INSERT INTO auto_emails(email, type, status, id_value) VALUES(?,?,?,?)";
		return template.update(sql, new Object[] {model.getEmail(), model.getType(), Constants.PENDING, model.getIdValue()})==1;
	}

	public boolean updateMail(int sent, int autoEmailId) {
		String sql = " UPDATE auto_emails SET status = ?, mail_sent_dt_tm = STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') WHERE auto_emails_id = ? ";
		return template.update(sql, new Object[] {sent,Util.Now(), autoEmailId})==1;
	}

	public int checkForExistingVerify(int automaticMailId) 
	{
		String sql = "SELECT status FROM auto_emails WHERE auto_emails_id = ?";
		return template.queryForObject(sql, new Object[] {automaticMailId}, Integer.class);
	}

	public boolean updateVerifyMail(int automaticMailId) 
	{
		String sql = " UPDATE auto_emails SET status = ?, mail_clicked_dt_tm = STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') WHERE auto_emails_id = ? ";
		return template.update(sql, new Object[] {Constants.CLICKED,Util.Now(), automaticMailId})==1;
	}
}
