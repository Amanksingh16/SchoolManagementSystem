package kmsg.sms.student.daoint;

import java.util.Map;

import kmsg.sms.student.model.Personal;
import kmsg.sms.student.model.SchoolSibling;
import kmsg.sms.student.model.StudentHistory;
import kmsg.sms.student.model.StudentMedicalDtls;
import kmsg.sms.student.model.StudentParents;
import kmsg.sms.student.model.StudentSibling;

public interface StudentRWDao {
	Map<String, Object> insertStudent( Personal personal);
	Map<String, Object> updateStudent( Personal personal);
	Map<String, Object> updateStudentParents(StudentParents parents);
	Map<String, Object> insertSibling(StudentSibling sibling);
	Map<String, Object> updateSibling(StudentSibling sibling);
	Map<String, Object> updateStudentHistory(StudentHistory history);
	Map<String, Object> updateStudentMedical(StudentMedicalDtls medical);
	Map<String, Object> deleteSibling(SchoolSibling model);
}
