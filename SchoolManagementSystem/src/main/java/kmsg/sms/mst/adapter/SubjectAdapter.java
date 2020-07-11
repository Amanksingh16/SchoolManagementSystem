package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmsg.sms.mst.daoimpl.SubjectDaoImpl;


@Component
public class SubjectAdapter {
	
	@Autowired
	SubjectDaoImpl dao;
	
	public Map<String, Object> getSubjectList() 
	{
		return dao.getSubjectList();
	}
}
