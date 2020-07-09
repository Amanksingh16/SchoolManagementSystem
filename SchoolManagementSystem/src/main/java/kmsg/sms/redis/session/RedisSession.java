package kmsg.sms.redis.session;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmsg.sms.admin.model.School;
import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;

@Component
public class RedisSession implements SMSLogger
{
	public String MESSAGE = "You are not logged in";
	public String MESSAGE2 = "Your Session is expired, login again";
	
    @Autowired
    private RedisOperations op;
	
	public boolean createSession(School school,String sessionId, String ipAddress)
	{
		RedisData data = new RedisData();
    	
    	data.setSchoolId(school.getSchoolId());
    	data.setSchool(school.getSchool());
    	data.setIpAddress(ipAddress);
    	data.setSessionId(sessionId);
    	data.setLoginTime(new Date().toString());
    	data.setLastActiveTime(new Date().toString());
    	
    	op.save(data, sessionId);
    	op.putValueWithExpireTime(sessionId, data, 3, TimeUnit.HOURS);
    	logger.info("Login Successfull ,school = "+school.getSchool()+", sessionId = "+sessionId+" , ipAddress = "+ipAddress);
    	
    	return true;
	}
	
	public Map<String, Object> logout(String sessionId)
	{
		Map<String,Object> data = new HashMap<>();
		
		if(op.findById(sessionId) == null)
		{
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE,MESSAGE);
			return data;	
		}
		op.delete(sessionId);
		
		logger.info("Logout Successful ,sessionId = "+sessionId);
		data.put(Constants.STATUS, Constants.SUCCESS);
		data.put(Constants.MESSAGE,"Logout Successful");
		return data;	
	}

	public Map<String, Object> validateSchoolSession(String sessionId, String currMethod) 
	{
		Map<String,Object> data = new HashMap<>();
		if(op.findById(sessionId) == null)
		{
			logger.info("Not Logged In ,sessionId = "+sessionId);
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE,MESSAGE);
			return data;	
		}
		
		if(op.getValue(sessionId) != null)
		{
			RedisData rd = (RedisData)op.getValue(sessionId);
			rd.setLastActiveTime(new Date().toString());
			op.save(rd, sessionId);
			logger.info("Service Called "+currMethod +", sessionId = "+sessionId, " at "+rd.getLastActiveTime());
			
			data.put("schoolId",rd.getSchoolId());
			data.put("school",rd.getSchool());
			data.put(Constants.STATUS, Constants.SUCCESS);
			return data;
		}
		data.put(Constants.STATUS, Constants.FAILURE);
		data.put(Constants.MESSAGE,MESSAGE2);
		return data;	
	}
}
