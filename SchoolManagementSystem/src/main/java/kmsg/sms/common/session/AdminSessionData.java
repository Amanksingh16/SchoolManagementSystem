package kmsg.sms.common.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminSessionData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int adminId;;
	private String name;
	private String role;
	private UUID SessionID;
	private String loginTime;
	private String ipAddress;
	
	public static final List<AdminSessionData> lstSessionData	=	new ArrayList<AdminSessionData>();

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
}
