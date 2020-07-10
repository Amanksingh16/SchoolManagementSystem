package kmsg.sms.mst.model;

public class WingSession 
{
	private int wingSessionId;
	private int wingId;
	private String wing;
	private String wingSession;
	private String sessionType;
	private String fromDt;
	private String toDt;
	
	public int getWingSessionId() {
		return wingSessionId;
	}
	public void setWingSessionId(int wingSessionId) {
		this.wingSessionId = wingSessionId;
	}
	public int getWingId() {
		return wingId;
	}
	public void setWingId(int wingId) {
		this.wingId = wingId;
	}
	public String getWing() {
		return wing;
	}
	public void setWing(String wing) {
		this.wing = wing;
	}
	public String getWingSession() {
		return wingSession;
	}
	public void setWingSession(String wingSession) {
		this.wingSession = wingSession;
	}
	public String getSessionType() {
		return sessionType;
	}
	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}
	public String getFromDt() {
		return fromDt;
	}
	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}
	public String getToDt() {
		return toDt;
	}
	public void setToDt(String toDt) {
		this.toDt = toDt;
	}
}
