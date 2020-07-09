package kmsg.sms.redis.session;

import java.io.Serializable;

public class RedisData implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int schoolId;
	private String school;
	private String sessionId;
	private String loginTime;
	private String lastActiveTime;
	private String ipAddress;
	
	public String getLastActiveTime() {
		return lastActiveTime;
	}
	public void setLastActiveTime(String lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
