package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoimpl.SubItemDaoImpl;
import kmsg.sms.mst.model.SubItem;

@Component
public class SubItemAdapter {
	
	@Autowired
	SubItemDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> getSubItemList(String itemId) 
	{	
		return dao.getSubItemList(Integer.parseInt(itemId));
	}
	
	public Map<String, Object> addSubItem(String subItem){
		
		SubItem model = new SubItem();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(subItem, SubItem.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occurred in saving subItem");
		}
		if(model.getSubItemId() == 0)
			return dao.saveSubItem(model);
		else
			return dao.updateSubItem(model);
	}

	public Map<String, Object> getAllSubItemList() 
	{
		return dao.getAllSubItemList();	
	}
}
