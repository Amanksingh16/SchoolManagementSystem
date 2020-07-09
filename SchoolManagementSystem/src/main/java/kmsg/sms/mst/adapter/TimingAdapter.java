package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.mst.daoimpl.TimingDaoImpl;
import kmsg.sms.mst.model.Timing;
import kmsg.sms.mst.model.TimingPeriod;
import kmsg.sms.common.DaoHandler;
import kmsg.sms.common.SvcStatus;

@Component
public class TimingAdapter {

	@Autowired
	TimingDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> getTimingList() 
	{
		return dao.getTimingList();
	}

	public Map<String, Object> saveTiming(String timing) 
	{
		Timing model = new Timing();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(timing, Timing.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occured in saving timing.");
		}
		
		if(model.getTimingId() == 0)
		{
			return dao.saveTiming(model);
		}
		else
		{
			return dao.updateTiming(model);
		}
	}

	public Map<String, Object> saveTimingPeriod(String timingPeriod) 
	{
		TimingPeriod model = new TimingPeriod();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(timingPeriod, TimingPeriod.class);
			int count = dao.checkForTimingPeriods(model.getTimingId());
			DaoHandler dh = new DaoHandler();
			dh.start(new Object(){}.getClass().getEnclosingMethod().getName());
			
			if(count > 0)
			{
				if(dao.deleteTimingPeriods(model.getTimingId())>0)
				{
					for(int i = 0;i<model.getTimingPeriod().size();i++)
					{
						if(dao.insertTimingPeriods(model.getTimingId(), model.getTimingPeriod().get(i))==0) {
							dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
							return SvcStatus.GET_FAILURE("Something went wrong in saving timing periods");
						}
					}
				}
				else {
					dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
					return SvcStatus.GET_FAILURE("Something went wrong in deleting timing periods");
				}
			}
			else
			{
				for(int i = 0;i<model.getTimingPeriod().size();i++)
				{
					if(dao.insertTimingPeriods(model.getTimingId(), model.getTimingPeriod().get(i))==0) {
						dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
						return SvcStatus.GET_FAILURE("Something went wrong in saving timing periods");
					}
				}
			}
			if(dao.updateTimingEndTm(model.getEndTm(), model.getTimingId())>0)
			{
				dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
				return SvcStatus.GET_SUCCESS("Timing Periods Saved");
			}
			dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
			return SvcStatus.GET_FAILURE("Something went wrong in saving timings end time");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error occured in saving timing periods.");
		}
	}

	public Map<String, Object> getTimingPeriodList(String timingId) {
		return dao.getTimingPeriodList(Integer.parseInt(timingId));
	}
}
