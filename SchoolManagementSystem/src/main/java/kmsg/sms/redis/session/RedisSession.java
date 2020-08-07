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
import kmsg.sms.common.Util;

@Component
public class RedisSession implements SMSLogger
{
	public String MESSAGE = "You are not logged in";
	public String MESSAGE2 = "Your Session is expired, login again";
	public String MESSAGE3 = "Invalid User Request";
	
    @Autowired
    private RedisOperations op;
	
	public String createSession(School school,String sessionId, String ipAddress)
	{
		RedisData data = new RedisData();
    	
    	data.setSchoolId(school.getSchoolId());
    	data.setSchool(school.getSchool());
    	data.setIpAddress(ipAddress);
    	data.setSessionId(sessionId);
    	data.setLoginTime(new Date().toString());
    	data.setLastActiveTime(new Date().toString());
    	
    	String token = Util.generateRandomTOKEN();
    	
    	op.save(data, token);
    	op.putValueWithExpireTime(token, data, Constants.TOKEN_EXPIRE_TIME_MINUTES, TimeUnit.MINUTES);
    	op.putValueWithExpireTime(sessionId, data, Constants.SESSION_EXPIRE_TIME_HOURS, TimeUnit.HOURS);
    	
    	logger.info("Login Successfull ,school = "+school.getSchool()+", sessionId = "+sessionId+" , ipAddress = "+ipAddress);
    	
    	return token;
	}
	
	public Map<String, Object> logout(String tokenId)
	{
		Map<String,Object> data = new HashMap<>();
		
		if(op.findById(tokenId) == null)
		{
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE,MESSAGE);
			return data;	
		}
		op.delete(tokenId);
		
		logger.info("Logout Successful ,sessionId = "+tokenId);
		data.put(Constants.STATUS, Constants.SUCCESS);
		data.put(Constants.MESSAGE,"Logout Successful");
		return data;	
	}

	public Map<String, Object> validateSchoolSession(String sessionId,String tokenId) 
	{
		Map<String,Object> data = new HashMap<>();
		String token = tokenId;
		
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];
		String methodName = e.getMethodName();
		
		if(token == null)
		{
			logger.info("Invalid Token Id ,sessionId = "+sessionId);
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE,MESSAGE3);
			return data;			
		}
		
		if(op.findById(token) == null)
		{
			logger.info("Not Logged In ,sessionId = "+sessionId);
			data.put(Constants.STATUS, Constants.FAILURE);
			data.put(Constants.MESSAGE,MESSAGE);
			return data;	
		}
		
		if(op.getValue(token) == null)
		{
			RedisData rd = op.findById(tokenId);
			token = Util.generateRandomTOKEN();
	    	op.putValueWithExpireTime(token, rd, Constants.TOKEN_EXPIRE_TIME_MINUTES, TimeUnit.MINUTES);			
		}
		
		if(op.getValue(sessionId) != null)
		{
			RedisData rd = (RedisData)op.getValue(token);
			rd.setLastActiveTime(new Date().toString());
			op.save(rd, token);
			logger.info("Service Called "+methodName +", sessionId = "+sessionId, " at "+rd.getLastActiveTime());
			
			data.put("tokenId",token);
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
