package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.ManageClass;

public class ClassSectionMapper implements RowMapper<ManageClass>
{
	@Override
	public ManageClass mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		ManageClass cls = new ManageClass();
		cls.setBuildingId(rs.getInt("buildingId"));
		cls.setBuildingName(rs.getString("building"));
		cls.setRoomId(rs.getInt("roomId"));
		cls.setRoom(rs.getString("room"));
		cls.setFloor(rs.getString("floor"));
		cls.setClassLabel(rs.getString("class_name"));
		cls.setClassId(rs.getInt("class_id"));
		cls.setClassTeacher(rs.getString("teacher"));
		cls.setClassTeacherId(rs.getInt("class_teacher_id"));
		cls.setPrefectStudent(rs.getString("prefect"));
		cls.setPrefectStudentId(rs.getInt("prefect_student_id"));
		cls.setMonitorStudent(rs.getString("monitor"));
		cls.setMonitorStudentId(rs.getInt("monitor_student_id"));
		cls.setWing(rs.getString("wing"));
		cls.setWingId(rs.getInt("wing_id"));
		return cls;
	}
}
