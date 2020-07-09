package kmsg.sms.mst.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.mst.daoint.ManageClassDaoInt;
import kmsg.sms.mst.mapper.ClassSectionMapper;
import kmsg.sms.mst.mapper.MstClassMapper;
import kmsg.sms.mst.mapper.SectionsMapper;
import kmsg.sms.mst.model.ClassSections;
import kmsg.sms.mst.model.Classes;
import kmsg.sms.mst.model.ManageClass;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;

@Repository
public class ManageClassDaoImpl implements ManageClassDaoInt,SMSLogger {
	
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public Map<String, Object> selectAllClassSections() 
	{
		final String SQL = 
				" SELECT cs.class_section_id,"  + 
				" cs.class_id,"  + 
				" IFNULL(cs.class_label,mc.class_name) as class_name,"  + 
				" cs.section,"  + 
				" w.wing_id,"  + 
				" w.wing,"  + 
				" cs.class_teacher_id,"  + 
				" t.teacher,"  + 
				" cs.prefect_student_id,"  + 
				" s1.student as prefect,"  + 
				" cs.monitor_student_id,"  + 
				" s2.student as monitor,"  + 
				" mb.building_id,"  + 
				" mb.building_name,"  + 
				" br.room_id,"  + 
				" br.room,"  + 
				" br.floor,"  + 
				" cs.max_students"  + 
				" FROM "+schoolId+"_class_section cs "  + 
				" JOIN "+schoolId+"_wings w ON w.wing_id = cs.wing_id"  + 
				" JOIN mst_class mc ON mc.class_id = cs.class_id"  + 
				" JOIN teacher t ON t.teacher_id = cs.class_teacher_id"  + 
				" JOIN "+schoolId+"_student s1 ON s1.student_id = cs.prefect_student_id"  + 
				" JOIN "+schoolId+"_student s2 ON s2.student_id = cs.monitor_student_id"  + 
				" JOIN "+schoolId+"_mst_building mb ON mb.building_id = cs.building_id"  + 
				" JOIN "+schoolId+"_building_rooms br ON br.room_id = cs.room_id" ;
		
		List<ManageClass> list = new ArrayList<>();
		try {
			list = template.query(SQL, new ClassSectionMapper());
			if(list.size() == 0)
			{
				logger.error(" selectClass: No Data found" );
				return SvcStatus.GET_FAILURE(" No Data found. Contact System admin" );
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error(" selectWingClasses: Exception in selecting wing classes "  + e);
			return SvcStatus.GET_FAILURE(" Error occured in selecting wing classes. Contact System admin" );
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put(" lstWingClass" ,  list );
		return result ;	
	}

	@Override
	public Map<String, Object> updateClass(ManageClass model) 
	{
		final String SQL = " UPDATE class_section SET wing_id = ?," 
				+ "  class_id = ? ," 
				+ "  class_label = ? ," 
				+ "  section = ? ," 
				+ "  building_id = ?  ," 
				+ "  room_id = ? ," 
				+ "  max_students = ?," 
				+ "  class_teacher_id = ?," 
				+ "  prefect_student_id = ?," 
				+ "  monitor_student_id = ?" 
				+ "  WHERE class_section_id = ?" ;

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
					 model.getWingId(),
					 model.getClassId(),
					 model.getClassLabel(),
					 model.getSection(),
					 model.getBuildingId(),
					 model.getRoomId(),
					 model.getMaxStudents(),
					 model.getClassTeacherId(),
					 model.getPrefectStudentId(),
					 model.getMonitorStudentId(),
					 model.getClassSectionId()
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error(" updateClassSection : Duplicate Key for this Section:"  + model.getSection());
			return SvcStatus.GET_FAILURE(" Section already exist for this class" );
		}
		catch(Exception e) {
			logger.error(" updateClassSection :Exception occured in updateClassSection:"  + model.getSection() + " : "  + e.getMessage());
			return SvcStatus.GET_FAILURE(" Error occured in updating Class Section. Contact System Admin" );
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, " Class Section Updated" );
			return data;
		}
		else {
			logger.error(" updateClassSection : Failed to update Section :"  +  model.getSection());
			return SvcStatus.GET_FAILURE(" Class Section could not be updated. Contact System Admin" );
		}

	}
	

	@Override
	public Map<String, Object> saveClass(ManageClass model) {
		final String SQL = " INSERT INTO class_section (wing_id,class_id,class_label,section,building_id,room_id,max_students,class_teacher_id,prefect_student_id,monitor_student_id) VALUES (?,?,?,?,?,?,?,?,?,?)" ;

	    int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			 count = template.update (
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement ps = conn.prepareStatement(
													SQL,
													Statement.RETURN_GENERATED_KEYS
																	);
						ps.setInt(1, model.getWingId());
						ps.setInt(2, model.getClassId());
						ps.setString(3, model.getClassLabel());
						ps.setString(4, model.getSection());
						ps.setInt(5, model.getBuildingId());
						ps.setInt(6, model.getRoomId());
						ps.setInt(7, model.getMaxStudents());
						ps.setInt(8, model.getClassTeacherId());
						ps.setInt(9, model.getPrefectStudentId());
						ps.setInt(10, model.getMonitorStudentId());
						
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error(" insertClassSection : Duplicate Key insertSection:"  + model.getSection());
			return SvcStatus.GET_FAILURE(" Section already exist for this class" );
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(" insertClassSection :Exception occured in insertSection:"  + model.getSection() + " : "  + e.getMessage());
			return SvcStatus.GET_FAILURE(" Error occured in adding Class Section. Contact System Admin" );
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(" classSectionId" , holder.getKey().intValue());
			data.put(SvcStatus.MSG, " New Class Section Added" );
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE(" Class Section could not be added. Contact System Admin" );
		}
	}

	public Map<String, Object> selectWingClasses(int wingId) 
	{
		final String SQL = 
				"  SELECT class_id," 
				+ "  class_name FROM mst_class " 
				+ "  JOIN (SELECT from_std_id, to_std_id FROM " +schoolId+"_wings WHERE wing_id = ?) as w" 
				+ "  WHERE class_id BETWEEN w.from_std_id AND w.to_std_id;" ;
		
		List<Classes> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {wingId}, new MstClassMapper());
			if(list.size() == 0)
			{
				logger.error(" selectClass: No Data found" );
				return SvcStatus.GET_FAILURE(" No Data found. Contact System admin" );
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error(" selectWingClasses: Exception in selecting wing classes "  + e);
			return SvcStatus.GET_FAILURE(" Error occured in selecting wing classes. Contact System admin" );
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put(" lstWingClass" ,  list );
		return result ;	
	}

	public Map<String, Object> selectClassSections(int classId) 
	{
		final String SQL = "SELECT class_id,"
				+ " class_section_id,"
				+ " section"
				+ " FROM "+schoolId+"_class_section WHERE class_id = ?" ;
		
		List<ClassSections> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {classId}, new SectionsMapper());
			if(list.size() == 0)
			{
				logger.error(" selectClassSections: No sections found for this class" );
				return SvcStatus.GET_FAILURE(" No sections found for this class. Contact System admin" );
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error(" selectClassSections: Exception in selecting class sections "  + e);
			return SvcStatus.GET_FAILURE(" Error occured in selecting class sections. Contact System admin" );
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put(" lstClassSection" ,  list );
		return result ;	
	}
}
