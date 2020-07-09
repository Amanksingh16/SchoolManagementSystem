package kmsg.sms.mst.model;

import java.util.List;

public class TimingPeriod 
{
	private int timingId;
	private String endTm;
	private List<TimingPeriodList> timingPeriod;
	
	public int getTimingId() {
		return timingId;
	}
	public void setTimingId(int timingId) {
		this.timingId = timingId;
	}
	public String getEndTm() {
		return endTm;
	}
	public void setEndTm(String endTm) {
		this.endTm = endTm;
	}
	public List<TimingPeriodList> getTimingPeriod() {
		return timingPeriod;
	}
	public void setTimingPeriod(List<TimingPeriodList> timingPeriod) {
		this.timingPeriod = timingPeriod;
	}
}
