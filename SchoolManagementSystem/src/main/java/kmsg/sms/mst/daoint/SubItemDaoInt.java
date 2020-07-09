package kmsg.sms.mst.daoint;

import java.util.Map;

import kmsg.sms.mst.model.SubItem;

public interface SubItemDaoInt {
	
	Map<String, Object> getSubItemList(int itemId);
	 
	 Map<String, Object> saveSubItem(SubItem model);
	 
	 Map<String, Object> updateSubItem(SubItem model);
	 
	 void setSchoolId(int schoolId);

}
