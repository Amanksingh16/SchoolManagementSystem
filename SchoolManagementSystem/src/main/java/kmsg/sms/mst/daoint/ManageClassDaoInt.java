package kmsg.sms.mst.daoint;

import java.util.Map;

import kmsg.sms.mst.model.ManageClass;

public interface ManageClassDaoInt {
	
	Map<String, Object> updateClass(ManageClass model);

	Map<String, Object> saveClass(ManageClass model);

	void setSchoolId(int schoolId);

	Map<String, Object> selectAllClassSections();
}
