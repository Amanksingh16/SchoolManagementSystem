package kmsg.sms.mst.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kmsg.sms.mst.daoint.CityDaoInt;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.mapper.CityMapper;
import kmsg.sms.mst.model.CityModel;

@Repository
public class CityDaoImpl implements CityDaoInt, SMSLogger
{
	@Autowired
	JdbcTemplate template;
	
	
	public Map<String, Object> getCityList(int stateId) 
	{
		final String SQL = 
				" SELECT mst_city_id,"
				+ " city,"
				+ " state_id"
				+ " FROM mst_city"
				+ " WHERE state_id = ?";
		
		List<CityModel> list = new ArrayList<>();
		try {
			list = template.query(SQL,new  Object[] {stateId}, new CityMapper());
			if(list.size() == 0)
			{
				logger.error("selectCity: No City found for this state");
				return SvcStatus.GET_FAILURE("No City found for this state. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectCity: Exception in selecting City " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting City. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstCity",  list );
		return result ;	
	}
	public Map<String, Object> getCityList() 
	{
		final String SQL = 
				" SELECT city_id,"
				+ " IFNULL(city,city) as city,"
				+ " state_id"
				+ " FROM mst_city";
		
		List<CityModel> list = new ArrayList<>();
		try {
			list = template.query(SQL, new CityMapper());
			if(list.size() == 0)
			{
				logger.error("selectCity: No City found");
				return SvcStatus.GET_FAILURE("No City found. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectCity: Exception in selecting City " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting City. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstCity",  list );
		return result ;	
	}

}