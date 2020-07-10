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
import kmsg.sms.mst.daoint.StateDaoInt;
import kmsg.sms.mst.mapper.StateMapper;
import kmsg.sms.mst.model.StateModel;

@Repository
public class StateDaoImpl implements StateDaoInt,SMSLogger{
	@Autowired
	JdbcTemplate template;	
	
	public Map<String, Object> getStateList() 
	{
		final String SQL = 
				" SELECT state_id,"
				+ " IFNULL(state,state) as state_label"
				+ " FROM mst_state";
		
		List<StateModel> list = new ArrayList<>();
		try {
			list = template.query(SQL, new StateMapper());
			if(list.size() == 0)
			{
				logger.error("selectState: No State found");
				return SvcStatus.GET_FAILURE("No State found. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectState: Exception in selecting State " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting State. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstState",  list );
		return result ;	
	}
}