package kmsg.sms.mst.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kmsg.sms.mst.daoint.EducationDaoInt;
import kmsg.sms.mst.mapper.EducationMapper;
import kmsg.sms.mst.model.EducationModel;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;

@Repository
public class EducationDaoImpl implements EducationDaoInt,SMSLogger
{
	
	@Autowired
	JdbcTemplate template;	
	public Map<String, Object> getEducationList() {
		// TODO Auto-generated method stub
		final String SQL = 
				" SELECT mst_education_id,"
				+ " IFNULL(education,education) as education_label"
				+ " FROM "
				+ "mst_education";
		
		List<EducationModel> list = new ArrayList<>();
		try {
			list = template.query(SQL, new EducationMapper());
			if(list.size() == 0)
			{
				logger.error("selectEducation: No Education found");
				return SvcStatus.GET_FAILURE("No Education found. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectSubject: Exception in selecting Education " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Education. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstEducation",  list );
		return result ;	
	}

}
