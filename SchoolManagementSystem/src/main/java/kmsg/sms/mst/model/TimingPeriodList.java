package kmsg.sms.mst.model;

public class TimingPeriodList 
{
	private int timingPeriodId;
	private String period;
	private String startTm;
	private String periodType;
	private int duration;
	
	public int getTimingPeriodId() {
		return timingPeriodId;
	}
	public void setTimingPeriodId(int timingPeriodId) {
		this.timingPeriodId = timingPeriodId;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getStartTm() {
		return startTm;
	}
	public void setStartTm(String startTm) {
		this.startTm = startTm;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
