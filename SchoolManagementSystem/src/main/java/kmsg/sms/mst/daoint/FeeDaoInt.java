package kmsg.sms.mst.daoint;

import java.util.Map;

import kmsg.sms.mst.model.ClassFeeHead;
import kmsg.sms.mst.model.FeeHead;

public interface FeeDaoInt {

	void setSchoolId(int schoolId);

	Map<String, Object> selectFeeHeads();

	Map<String, Object> saveFeeHead(FeeHead model);

	Map<String, Object> updateFeeHead(FeeHead model);
	
	Map<String, Object> DeleteFeeHead(int feeHeadId);

	Map<String, Object> DeleteClassFeeHead(int feeClassId);

	Map<String, Object> selectClassFeeHeads(int classId);

	Map<String, Object> insertClassFeeHead(ClassFeeHead model, int classId);

	Map<String, Object> selectClassFees(int wingId);

}
