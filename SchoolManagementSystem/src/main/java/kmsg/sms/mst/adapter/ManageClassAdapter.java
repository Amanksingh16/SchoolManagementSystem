package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.mst.daoimpl.ManageClassDaoImpl;
import kmsg.sms.mst.model.ManageClass;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;

@Component
public class ManageClassAdapter implements SMSLogger {

	@Autowired
	ManageClassDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> addClassSection(String section) 
	{
		ManageClass model = new ManageClass();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(section, ManageClass.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception Occured in saving class section");
		}
		if(model.getClassSectionId() == 0)
		{
			return dao.saveClass(model);
		}
		else
		{
			return dao.updateClass(model);
		}
	}

	public Map<String, Object> getWingClasses(int wingId) 
	{
		return dao.selectWingClasses(wingId);
	}

	public Map<String, Object> getAllClassSections() 
	{
		return dao.selectAllClassSections();
	}

	public Map<String, Object> getClassSections(int classId) 
	{
		return dao.selectClassSections(classId);
	}
}
