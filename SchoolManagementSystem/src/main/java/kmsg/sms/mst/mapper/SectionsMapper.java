package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.ClassSections;

public class SectionsMapper implements RowMapper<ClassSections>
{
	@Override
	public ClassSections mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		ClassSections sec = new ClassSections();
		sec.setClassId(rs.getInt("class_id"));
		sec.setSectionId(rs.getInt("class_section_id"));
		sec.setSection(rs.getString("section"));
		return sec;
	}
}
