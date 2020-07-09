package kmsg.sms.mst.adapter;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoimpl.PeriodTypeDaoImpl;
import kmsg.sms.mst.model.PeriodType;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PeriodTypeAdapter implements SMSLogger {

	@Autowired
	PeriodTypeDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> TypeList() 
	{	
		return dao.TypeList();
	}

	public Map<String, Object> addPeriodType(String period) 
	{
		PeriodType model = new PeriodType();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(period, PeriodType.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occurred in saving period type");
		}
		if(model.getPeriodTypeId() == 0)
			return dao.savePeriodType(model);
		else
			return dao.updatePeriodType(model);
	}
}
