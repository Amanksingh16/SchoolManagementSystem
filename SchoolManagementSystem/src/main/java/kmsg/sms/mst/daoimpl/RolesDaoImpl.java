package kmsg.sms.mst.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoint.RolesDaoInt;
import kmsg.sms.mst.mapper.RolesMapper;
import kmsg.sms.mst.model.Roles;

@Repository
public class RolesDaoImpl implements RolesDaoInt, SMSLogger {
	@Autowired
	JdbcTemplate template;	
	
	public Map<String, Object> selectRoles() {
		final String SQL = 
				" SELECT "
				+ " role_id"
				+ ", role_name"
				+ ", role_desc"
				+ " FROM mst_roles"
				+ " ORDER BY role_name ";
		
		List<Roles> list = new ArrayList<>();
		try {
			list = template.query(SQL, new RolesMapper());
			if(list.size() == 0) {
				logger.error("selectRoles: No Roles found");
				return SvcStatus.GET_FAILURE("No Roles found. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectRoles: Exception in selecting Roles " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Roles. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstRole",  list );
		return result ;	
	}
}