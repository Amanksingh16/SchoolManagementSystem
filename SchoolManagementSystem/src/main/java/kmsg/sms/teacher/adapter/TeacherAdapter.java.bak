package kmsg.sms.teacher.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.teacher.daoimpl.TeacherDaoImpl;
import kmsg.sms.teacher.model.ManageTeacher;
import kmsg.sms.teacher.model.SchoolTeacherClassModel;
import kmsg.sms.teacher.model.SchoolTeacherClassSubjectModel;
import kmsg.sms.teacher.model.TeacherClassModel;
import kmsg.sms.teacher.model.TeacherClassSubjectModel;
import kmsg.sms.teacher.model.TeacherDocModel;
import kmsg.sms.teacher.model.TeacherEducationModel;
import kmsg.sms.teacher.model.TeacherRole;

@Component
public class TeacherAdapter implements SMSLogger {

	@Autowired
	TeacherDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}

	@Value("${saveFilePath}")
	private String saveFilePath;
	
	public Map<String, Object> saveTeacherRole(String strTeacherRole) {
		
		Map<String,Object> map = new HashMap<>();
		TeacherRole model = new TeacherRole();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(strTeacherRole, TeacherRole.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG, "Exception Occured in data of Teacher Role");
			return map;
		}
		
		return dao.insertTeacherRole(model);
	}
	
	public Map<String, Object> getTeacherRoles(int teacherId) {
		return dao.selectTeacherRoles(teacherId);
	}
	
	public Map<String, Object> addTeacher(String teacher) { 
		
		Map<String,Object> map = new HashMap<>();
		ManageTeacher model = new ManageTeacher();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(teacher, ManageTeacher.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving Teacher");
			return map;
		}
		if(model.getTeacherId() == 0)
		{
			return dao.saveTeacher(model);
		}
		else
		{
			return dao.updateTeacher(model);
		}
	}

	public Map<String, Object> getTeacherList() {
		return dao.getTeacherList();
	}

	public Map<String, Object> getTeacher(int teacherId)
	{
		Map<String,Object> map = new HashMap<>();
		
		map.put("SvcStatus", SvcStatus.SUCCESS);
		map.put("teacher", dao.getTeacher(teacherId));
		return map;
		
	}

	public Map<String, Object> getTeacherDocsList(int teacherId) {
		return dao.getTeacherDocsList(teacherId);
	}

	public Map<String, Object> getTeacherClassList(int teacherId) {
		return dao.getTeacherClassList(teacherId);
		
	}

	public Map<String, Object> getTeacherSubjectList(String schoolTeacherClass) {
		Map<String,Object> map = new HashMap<>();
		SchoolTeacherClassModel model = new SchoolTeacherClassModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(schoolTeacherClass, SchoolTeacherClassModel.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in getting Teacher");
			return map;
		}
		
		return dao.getTeacherSubjectList(model);
		
	}

	public Map<String, Object> addDocument(String doc, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<>();
		TeacherDocModel model = new TeacherDocModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(doc, TeacherDocModel.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving Building");
			return map;
		}
		if(file!=null)
		{
			String contractFilePath = saveFilePath+"/"+file.getOriginalFilename();
			File directory = new File(contractFilePath);
			directory.getParentFile().mkdirs();
		      if (!directory.exists())		
					directory.createNewFile();			
			 
			try (FileOutputStream fos = new FileOutputStream(directory)) {
				byte[] imageByte = file.getBytes();
				fos.write(imageByte);
				model.setDocPath(contractFilePath);
			} catch (Exception e) {
				map.put(SvcStatus.MSG,"Error saving file for building " + model.getDocPath() + " : " + e);
				map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
				e.printStackTrace();
				return map;
			}
		}
		if(dao.getTeacherDocTypePath(model) != null){
			return dao.saveDocument(model);
		}
		else{
			return dao.updateDocument(model);
		}

	}

	@SuppressWarnings("unused")
	public Map<String, Object> addDocument(MultipartFile file, int schoolId, int docTypeId, int teacherId, String docPath) throws IOException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<>();
		TeacherDocModel model = new TeacherDocModel();
		model.setTeacherId(teacherId);
		model.setDocPath(docPath);
		model.setDocTypeId(docTypeId);

		if(file!=null)
		{
			String contractFilePath = saveFilePath+"/"+file.getOriginalFilename();
			File directory = new File(contractFilePath);
			directory.getParentFile().mkdirs();
		      if (!directory.exists())		
					directory.createNewFile();			
			 
			try (FileOutputStream fos = new FileOutputStream(directory)) {
				byte[] imageByte = file.getBytes();
				fos.write(imageByte);
				model.setDocPath(contractFilePath);
			} catch (Exception e) {
				map.put(SvcStatus.MSG,"Error saving file for document " + model.getDocPath() + " : " + e);
				map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
				e.printStackTrace();
				return map;
			}
		}
		try{
			String existingDocPath = dao.getTeacherDocTypePath(model);
		}
		catch(EmptyResultDataAccessException ex) {
			return dao.saveDocument(model);
		}	
		return dao.updateDocument(model);
	}

	public Map<String, Object> getTeacherEducationList(int teacherId) {
		return dao.getTeacherEducationList(teacherId);
	}

	public Map<String, Object> addEducation(String teacherEducation) {

		Map<String,Object> map = new HashMap<>();
		TeacherEducationModel model = new TeacherEducationModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(teacherEducation, TeacherEducationModel.class);
		}
		catch(Exception e){
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving Teacher Education");
			return map;
		}
		if(model.getTeacherEducationId() == 0){
			return dao.saveTeacherEducation(model);
		}
		else{
			return dao.updateTeacherEducation(model);
		}

	}

	public Map<String, Object> addClass(String teacherClass) {

		Map<String,Object> map = new HashMap<>();
		TeacherClassModel model = new TeacherClassModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(teacherClass, TeacherClassModel.class);
		}
		catch(Exception e){
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving Teacher Class");
			return map;
		}
		if(model.getTeacherClassId() == 0){
			return dao.saveTeacherClass(model);
		}
		else{
			return dao.updateTeacherClass(model);
		}
	}

	public Map<String, Object> addSubject(String teacherClassSubject) {
		
		Map<String,Object> map = new HashMap<>();
		TeacherClassSubjectModel model = new TeacherClassSubjectModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(teacherClassSubject, TeacherClassSubjectModel.class);
		}
		catch(Exception e){
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in saving Subject in Teacher Class");
			return map;
		}
		if(model.getTeacherClassSubjectId() == 0){
			return dao.saveTeacherClassSubject(model);
		}
		else{
			return dao.updateTeacherClassSubject(model);
		}
	}

	public Map<String, Object> deleteEducation(String teacherEducation) {

		Map<String,Object> map = new HashMap<>();
		TeacherEducationModel model = new TeacherEducationModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(teacherEducation, TeacherEducationModel.class);
		}
		catch(Exception e){
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in deleting teacher Education");
			return map;
		}
		return dao.deleteEducation(model);
	}
	
	public Map<String, Object> deleteDocument(String teacherDocument) {

		Map<String,Object> map = new HashMap<>();
		TeacherDocModel model = new TeacherDocModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(teacherDocument, TeacherDocModel.class);
		}
		catch(Exception e){
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in deleting teacher Document");
			return map;
		}
		return dao.deleteDocument(model);
	}

	public Map<String, Object> deleteSubject(String teacherClassSubject) {
		Map<String,Object> map = new HashMap<>();
		SchoolTeacherClassSubjectModel model = new SchoolTeacherClassSubjectModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(teacherClassSubject, SchoolTeacherClassSubjectModel.class);
		}
		catch(Exception e){
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in deleting Teacher Class Subject");
			return map;
		}
		return dao.deleteClassSubject(model);
	}

	public Map<String, Object> deleteClass(String teacherClass) {
		Map<String,Object> map = new HashMap<>();
		TeacherClassModel model = new TeacherClassModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(teacherClass, TeacherClassModel.class);
		}
		catch(Exception e){
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in deleting Teacher Class");
			return map;
		}
		return dao.deleteClass(model);
	}
}
