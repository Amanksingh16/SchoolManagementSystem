package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmsg.sms.mst.daoimpl.EducationDaoImpl;

@Component
public class EducationAdapter {

	@Autowired
	EducationDaoImpl dao;

	public Map<String, Object> getEducationList() {
		// TODO Auto-generated method stub
		return dao.getEducationList();
	}
}
