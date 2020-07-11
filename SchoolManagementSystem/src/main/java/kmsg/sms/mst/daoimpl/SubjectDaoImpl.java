package kmsg.sms.mst.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kmsg.sms.mst.mapper.SubjectMapper;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoint.SubjectDaoInt;
import kmsg.sms.mst.model.SubjectModel;


@Repository
public class SubjectDaoImpl implements SubjectDaoInt,SMSLogger{
	@Autowired
	JdbcTemplate template;	

	public Map<String, Object> getSubjectList() {
		final String SQL = 
				" SELECT mst_subject_id,"
				+ " IFNULL(subject,subject) as subject_label"
				+ " FROM "
				+ "mst_subject";
		
		List<SubjectModel> list = new ArrayList<>();
		try {
			list = template.query(SQL, new SubjectMapper());
			if(list.size() == 0)
			{
				logger.error("selectSubject: No Subject found");
				return SvcStatus.GET_FAILURE("No Subject found. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectSubject: Exception in selecting Subject " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Subject. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstSubject",  list );
		return result ;	
	}
	
/*		
		public List<TeacherClassModel> getTeacherClass(SchoolTeacherModel sch) {
			int schoolId = sch.getSchoolId();
			int teacherId = sch.getTeacherId();
			final String SQL = 
					" SELECT "
					+ "tcs.teacher_class_subject_id,"
					+ " tcs.subject_id,"
					+ " teacher_id,"
					+ " class_id"
					+ " FROM"
					+ " 1_teacher_class_subject tcs"
					+ " JOIN"
					+ " 1_teacher_class tc"
					+ " ON tc.teacher_class_id=tcs.teacher_class_id"
					+ " LEFT JOIN 1_subject s ON s.subject_id=tcs.subject_id"
					+ " LEFT JOIN mst_class mc ON class_id=tc.class_id"
					+ " WHERE teacher_id = ?"
					+ " ORDER BY class_id ASC";
			
			List<TeacherClassModel> list = new ArrayList<>();
			try {
				list = template.query(SQL, new Object[] {teacherId}, new TeacherClassMapper());
				if(list.size() == 0)
				{
					logger.error("selectClass: No Class found for teacher with Id = "+teacherId);
					// return SvcStatus.GET_FAILURE("No Class found. Contact System admin");
				}
			} 
			catch (Exception e) {
				System.out.println(e);
				logger.error("selectClass: Exception in selecting Class " + e);
				return SvcStatus.GET_FAILURE("Error occured in selecting Class for teacher with Id = "+teacherId+". Contact System admin");
			}
			
			Map<String, Object> result = new HashMap<>();
			result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			result.put("lstTeacherClass",  list );
			return list ;	
	}
*/
}
