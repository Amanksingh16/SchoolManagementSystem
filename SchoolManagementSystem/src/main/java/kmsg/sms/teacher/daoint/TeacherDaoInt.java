package kmsg.sms.teacher.daoint;

import java.util.Map;

import kmsg.sms.teacher.model.ManageTeacher;
import kmsg.sms.teacher.model.SchoolTeacherClassModel;
import kmsg.sms.teacher.model.TeacherClassModel;
import kmsg.sms.teacher.model.TeacherClassSubjectModel;
import kmsg.sms.teacher.model.TeacherDocModel;
import kmsg.sms.teacher.model.TeacherEducationModel;
import kmsg.sms.teacher.model.TeacherRole;

public interface TeacherDaoInt {
	public Map<String, Object> saveTeacher(ManageTeacher model);

	Map<String, Object> updateTeacher(ManageTeacher model);

	Map<String, Object> getTeacherSubjectList(SchoolTeacherClassModel model);

	Map<String, Object> selectTeacherRoles(int teacherId);

	Map<String, Object> updateDocument(TeacherDocModel model);

	Map<String, Object> saveDocument(TeacherDocModel model);

	Map<String, Object> saveTeacherEducation(TeacherEducationModel model);

	Map<String, Object> updateTeacherEducation(TeacherEducationModel model);

	Map<String, Object> saveTeacherClass(TeacherClassModel model);

	Map<String, Object> updateTeacherClass(TeacherClassModel model);

	Map<String, Object> saveTeacherClassSubject(TeacherClassSubjectModel model);

	Map<String, Object> updateTeacherClassSubject(TeacherClassSubjectModel model);

	Object getTeacherDocTypePath(TeacherDocModel model);

	Map<String, Object> deleteEducation(TeacherEducationModel model);

	void setSchoolId(int schoolId);

	Map<String, Object> getTeacherList();

	ManageTeacher getTeacher(int teacherId);

	Map<String, Object> getTeacherDocsList(int teacherId);

	Map<String, Object> getTeacherClassList(int teacherId);

	Map<String, Object> getTeacherEducationList(int teacherId);

	Map<String, Object> insertTeacherRole(TeacherRole model);

	Map<String, Object> deleteTeacherRole(TeacherRole model);
}
