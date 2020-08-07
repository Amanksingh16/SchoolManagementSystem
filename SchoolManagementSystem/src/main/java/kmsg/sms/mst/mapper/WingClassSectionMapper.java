package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.WingClassSection;

public class WingClassSectionMapper implements RowMapper<WingClassSection> {
	@Override
	public WingClassSection mapRow(ResultSet rs, int rowNum) throws SQLException {
		WingClassSection model = new WingClassSection();
		model.setClassSectionId(rs.getInt("class_section_id"));
		model.setSection(rs.getString("section"));
		model.setClassTeacherId(rs.getInt("class_teacher_id"));
		return model ;
	}
}
