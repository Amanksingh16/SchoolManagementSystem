package kmsg.sms.teacher.svcint;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherSvcInt {
	public Map<String, Object> saveTeacher(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getTeacherList(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);
	
	public Map<String, Object> getTeacher(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> saveEducation(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveClass(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveSubject(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> deleteEducation(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> deleteDocument(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> deleteSubject(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> deleteClass(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveTeacherRole(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getTeacherRoles(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> saveDocument(MultipartFile file, Map<String, String> params, int docTypeId, int teacherId,
			String docPath, HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException;

	Map<String, Object> deleteTeacherRole(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);
}
