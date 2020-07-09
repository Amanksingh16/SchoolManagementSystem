package kmsg.sms.student.daoint;

import java.util.Map;

import kmsg.sms.student.model.SchoolStudent;

public interface StudentDao {
	Map<String, Object> selectAllStudents( int schoolId);
	Map<String, Object> selectStudentDtls( SchoolStudent model);
	Map<String, Object> selectStudentParents( SchoolStudent model);
	Map<String, Object> selectStudentSiblings( SchoolStudent model);

}
