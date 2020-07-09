package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmsg.sms.mst.daoimpl.CityDaoImpl;

@Component
public class CityAdapter {
	
	@Autowired
	CityDaoImpl dao;
	
	public Map<String, Object> getCityList() 
	{
		return dao.getCityList();
	}

}
