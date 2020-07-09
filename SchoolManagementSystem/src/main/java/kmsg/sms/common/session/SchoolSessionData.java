package kmsg.sms.common.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SchoolSessionData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int schoolId;
	private String name;
	private UUID SessionID;
	private String loginTime;
	private String ipAddress;
	
	public static final List<SchoolSessionData> lstSessionData	=	new ArrayList<SchoolSessionData>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getSessionID() {
		return SessionID;
	}

	public void setSessionID(UUID sessionID) {
		SessionID = sessionID;
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

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
}
