package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kmsg.sms.mst.daoimpl.RolesDaoImpl;

@Component
public class RolesAdapter {
	
	@Autowired
	RolesDaoImpl dao;
	
	public Map<String, Object> getStateList() {
		return dao.selectRoles();
	}

}
