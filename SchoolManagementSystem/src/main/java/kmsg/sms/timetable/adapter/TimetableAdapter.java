package kmsg.sms.timetable.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.Constants;
import kmsg.sms.common.DaoHandler;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.timetable.daoimpl.TimetableDaoImpl;
import kmsg.sms.timetable.model.Timetable;
import kmsg.sms.timetable.model.TimetablePeriods;

@Component
public class TimetableAdapter 
{
	@Autowired
	TimetableDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getTimetable(String classSectionId, String wingId) 
	{	
		Map<String, Object> map = new HashMap<>();
		List<Timetable> finalList = new ArrayList<>();
		int clsSecId = Integer.parseInt(classSectionId);
		int wId = Integer.parseInt(wingId);
		
		try {
			if(dao.checkTimingPeriods(wId)==0)
				return SvcStatus.GET_FAILURE("Timing Periods not available for this wing session");		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Something went wrong in getting timetable");
		}
		map = dao.getTimetable(wId, clsSecId);
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			List<Timetable> list = (List<Timetable>) map.get("lstTimetable");
			map.clear();
			for(int i=0;i<list.size();i++)
			{
				Timetable table = list.get(i);

				if(table.isPublished())
					map = dao.selectTimetablePeriods(table.getTimetableId(),"");
				else
					map = dao.selectTimetablePeriods(table.getTimetableId(),"_tmp");
				
				if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
					return map;
				
				List<TimetablePeriods> tpList = (List<TimetablePeriods>) map.get("lstTimetablePeriods");
				table.setListTimetablePeriods(tpList);

				finalList.add(table);
			}
			map.clear();
			map.put(Constants.STATUS,Constants.SUCCESS);
			map.put("lstTimetable",finalList);
			return map;
		}
		if(map.get(SvcStatus.MSG).equals("No timetable available for selected filter"))
		{
			map = dao.getTimingPeriods(Integer.parseInt(wingId));
			map.put(SvcStatus.MSG,"No timetable available for selected filter");
		}
		return map;
	}

	public Map<String, Object> saveTimetable(String timetable, String table) 
	{
		Map<String,Object> map = new HashMap<>();
		DaoHandler dh = new DaoHandler();
		Timetable model = new Timetable();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(timetable, Timetable.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in reading timetable data");
			return map;
		}
		dh.start(new Object(){}.getClass().getEnclosingMethod().getName());
		if(model.getTimetableId() == 0)
		{
			map = dao.insertNewTimetable(model.getTimetable(),model.getWingId(),model.getClassSectionId(),table);
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
				model.setTimetableId((int)map.get("timetableId"));
			else
			{
				dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
				return map;
			}
		}
		else
		{
			if(dao.updateTimetable(model.getTimetable(),model.getTimetableId(),table)==0)
			{
				dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
				map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
				map.put(SvcStatus.MSG,"Something went wrong in updating timetable");
				return map;
			}
			
			if(dao.deleteTimetablePeriods(model.getTimetableId(),table)==0)
			{
				dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
				map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
				map.put(SvcStatus.MSG,"Something went wrong in deleting timetable periods");
				return map;
			}
		}
		
		map.clear();
		for(int i=0;i<model.getListTimetablePeriods().size();i++)
		{
			if(dao.insertNewTimetablePeriods(model.getListTimetablePeriods().get(i),model.getTimetableId(),table)==0)
			{
				dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
				map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
				map.put(SvcStatus.MSG,"Something went wrong in saving timetable periods");
				return map;
			}
		}
		dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
		map.put(SvcStatus.STATUS,SvcStatus.SUCCESS);
		map.put(SvcStatus.MSG,(table=="")?"Timetable Published":"Timetable Saved");
		return map;
	}

	public Map<String, Object> getClassSubjects(String classId) 
	{
		return dao.selectClassSubjects(classId);
	}
	
	public Map<String, Object> getClassTeachers(String classId) 
	{
		return dao.selectClassTeachers(classId);
	}
}
