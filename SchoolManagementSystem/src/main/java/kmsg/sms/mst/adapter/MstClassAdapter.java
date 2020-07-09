package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmsg.sms.mst.daoimpl.MstClassDaoImpl;

@Component
public class MstClassAdapter {

	@Autowired
	MstClassDaoImpl dao;
	
	public Map<String, Object> getClassList() 
	{
		return dao.getClassList();
	}

}
