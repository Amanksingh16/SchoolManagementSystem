package kmsg.sms.teacher.svcimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kmsg.sms.common.SvcStatus;
import kmsg.sms.redis.session.RedisSession;
import kmsg.sms.teacher.adapter.TeacherAdapter;
import kmsg.sms.teacher.svcint.TeacherSvcInt;

@RestController
@RequestMapping("/sms/teacher")
public class TeacherSvcImpl implements TeacherSvcInt{
	
	@Autowired
	TeacherAdapter adapter;

	@Autowired
	RedisSession ses;
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getTeacherList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getTeacherList();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/get", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getTeacher(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String schoolTeacher = params.get("teacherId");
			return adapter.getTeacher(Integer.parseInt(schoolTeacher));
		}
		return map;
	}

	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveTeacher(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String teacher = params.get("teacher");
			return adapter.addTeacher(teacher);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/get_teacher_roles", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getTeacherRoles(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS)) {
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String schoolTeacher = params.get("teacherId");
			return adapter.getTeacherRoles(Integer.parseInt(schoolTeacher));
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save_role", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveTeacherRole(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String strTeacherRole = params.get("teacherRole");
			return adapter.saveTeacherRole( strTeacherRole);
		}
		return map;
	}
	
	@RequestMapping(value="/docslist", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getDocList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		String schoolTeacher = params.get("teacherId");
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getTeacherDocsList(Integer.parseInt(schoolTeacher));
		}
		return map;
	}
	@RequestMapping(value="/subjectlist", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getSubjectList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		String schoolTeacherClass = params.get("schoolTeacherClass");
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getTeacherSubjectList(schoolTeacherClass);
		}
		return map;
	}
	
	@RequestMapping(value="/classlist", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getClassList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		String schoolTeacher = params.get("teacherId");
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getTeacherClassList(Integer.parseInt(schoolTeacher));
		}
		return map;
	}
	
	@RequestMapping(value="/educationlist", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getEducationList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		String schoolTeacher = params.get("teacherId");
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getTeacherEducationList(Integer.parseInt(schoolTeacher));
		}
		return map;
	}

	@Override
	@RequestMapping(value="/savedoc", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveDocument(@RequestParam(value = "file",required=true) MultipartFile file,@RequestParam("docTypeId") int docTypeId,@RequestParam("teacherId") int teacherId,@RequestParam("docPath") String docPath, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.addDocument(file,schoolId,docTypeId,teacherId,docPath);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/saveeducation", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveEducation(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String teacherEducation = params.get("teacherEducation");
			return adapter.addEducation(teacherEducation);
		}
		return map;
	}
	

	@Override
	@RequestMapping(value="/saveclass", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveClass(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String teacherClass = params.get("teacherClass");
			return adapter.addClass(teacherClass);
		}
		return map;
	}

	@Override
	@RequestMapping(value="/savesubject", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveSubject(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			String teacherSubject = params.get("teacherSubject");
			return adapter.addSubject(teacherSubject);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value = "/deleteeducation",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> deleteEducation(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String teacherEducation = params.get("teacherEducation");
			return adapter.deleteEducation(teacherEducation);		
		}
		return map;
	}
	
	@Override
	@RequestMapping(value = "/deletedoc",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> deleteDocument(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String teacherDocument = params.get("teacherDocument");
			return adapter.deleteDocument(teacherDocument);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value = "/deletesubject",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> deleteSubject(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String teacherSubject = params.get("teacherSubject");
			return adapter.deleteSubject(teacherSubject);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value = "/deleteclass",method=RequestMethod.POST,headers="Accept=application/json")
	public Map<String,Object> deleteClass(@RequestParam Map<String, String> params, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(session.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String teacherClass = params.get("teacherClass");
			return adapter.deleteClass(teacherClass);
		}
		return map;
	}
	
}
