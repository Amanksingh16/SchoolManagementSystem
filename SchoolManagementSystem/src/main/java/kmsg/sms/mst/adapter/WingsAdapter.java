package kmsg.sms.mst.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoimpl.WingsDaoImpl;
import kmsg.sms.mst.model.AcademicSchedule;
import kmsg.sms.mst.model.AcademicYear;
import kmsg.sms.mst.model.Wings;

@Component
public class WingsAdapter implements SMSLogger
{
	@Autowired
	WingsDaoImpl dao;	
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> selectWings() 
	{
		return dao.selectWings();
	}
	
	public Map<String, Object> addNewWing(String wings) 
	{
		Wings model = new Wings();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(wings, Wings.class);
			String day = (model.isMon()?"1":"0")+(model.isTue()?"1":"0")+(model.isWed()?"1":"0")+(model.isThu()?"1":"0")+(model.isFri()?"1":"0")+(model.isSat()?"1":"0")+(model.isSun()?"1":"0");
			model.setDay(day);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception Occured in wings");
		}
		if(model.getWingId() == 0)
			return dao.insertNewWing(model);
		else
			return dao.updateWing(model);
	}
	
	public Map<String, Object> addAcademicYear(String acdYear) 
	{
		Map<String,Object> map = new HashMap<>();
		AcademicYear model = new AcademicYear();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(acdYear, AcademicYear.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in academic year");
			return map;
		}
		if(model.getWingId() == 0)
			return dao.insertNewAcademicYear(model);
		else
			return dao.updateAcademicYear(model);
	}
	
	public Map<String, Object> addAcademicSchedule(String acdSchedule) 
	{
		Map<String,Object> map = new HashMap<>();
		AcademicSchedule model = new AcademicSchedule();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(acdSchedule, AcademicSchedule.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in academic schedule");
			return map;
		}
		if(model.getWingId() == 0)
			return dao.insertNewAcdSchedule(model);
		else
			return dao.updateAcdSchedule(model);
	}

	public Map<String, Object> selectAcademicSchedule(String wingId, String academicYearId) 
	{
		return dao.selectAcademicSchedule(Integer.parseInt(wingId),Integer.parseInt(academicYearId));
	}

	public Map<String, Object> selectAcademicYear(String wingId) 
	{
		return dao.selectAcademicYear(Integer.parseInt(wingId));
	}
}
