package kmsg.sms.mst.daoint;

import java.util.Map;

import kmsg.sms.mst.model.Items;

public interface ItemsDaoInt {

	Map<String, Object> getItemList();
	
	void setSchoolId(int schoolId);
	
	Map<String, Object> saveItem(Items model);

	Map<String, Object> updateItem(Items model);
}
