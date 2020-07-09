package kmsg.sms.mst.daoint;

public interface TimingDaoInt {

	void setSchoolId(int schoolId);

	int checkForTimingPeriods(int timingId);

	int deleteTimingPeriods(int timingId);

	int updateTimingEndTm(String endTm, int timingId);

}
