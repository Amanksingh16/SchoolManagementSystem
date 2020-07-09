package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoimpl.ItemsDaoImpl;
import kmsg.sms.mst.model.Items;

@Component
public class ItemsAdapter {
	
	@Autowired
	ItemsDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> getItemList() 
	{	
		return dao.getItemList();
	}
	
	public Map<String, Object> addItem(String items){
		
		Items model = new Items();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(items, Items.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occurred in saving building.");
		}
		if(model.getItemId() == 0)
		{
			return dao.saveItem(model);
		}
		else
		{
			return dao.updateItem(model);
		}
		
	}

}
