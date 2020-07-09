package kmsg.sms.mst.model;

public class Timing 
{
	private int timingId;
	private int wingId;
	private String wing;
	private String effectiveDt;
	private String startTm;
	private String endTm;
	private int periods;
	
	public int getTimingId() {
		return timingId;
	}
	public void setTimingId(int timingId) {
		this.timingId = timingId;
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
	public String getEffectiveDt() {
		return effectiveDt;
	}
	public void setEffectiveDt(String effectiveDt) {
		this.effectiveDt = effectiveDt;
	}
	public String getStartTm() {
		return startTm;
	}
	public void setStartTm(String startTm) {
		this.startTm = startTm;
	}
	public String getEndTm() {
		return endTm;
	}
	public void setEndTm(String endTm) {
		this.endTm = endTm;
	}
	public int getPeriods() {
		return periods;
	}
	public void setPeriods(int periods) {
		this.periods = periods;
	}
	
	
}
