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
import kmsg.sms.mst.mapper.MstClassMapper;
import kmsg.sms.mst.model.Classes;

@Repository
public class MstClassDaoImpl implements SMSLogger
{

	@Autowired
	JdbcTemplate template;	
	
	public Map<String, Object> getClassList() 
	{
		final String SQL = 
				" SELECT class_id,"
				+ " class_name "
				+ " FROM mst_class";
		
		List<Classes> list = new ArrayList<>();
		try {
			list = template.query(SQL, new MstClassMapper());
			if(list.size() == 0)
			{
				logger.error("selectClass: No Class found");
				return SvcStatus.GET_FAILURE("No Class found. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectClass: Exception in selecting Class " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Class. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstClass",  list );
		return result ;	
	}
}
