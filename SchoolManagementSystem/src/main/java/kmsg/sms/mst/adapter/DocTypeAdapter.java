package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmsg.sms.mst.daoimpl.DocTypeDaoImpl;

@Component
public class DocTypeAdapter {

	@Autowired
	DocTypeDaoImpl dao;

	public Map<String, Object> getDocTypeList() {
		// TODO Auto-generated method stub
		return dao.getDocTypeList();
	}
}
