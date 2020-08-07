package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.WingClass;

public class WingClassMapper implements RowMapper<WingClass> {
	@Override
	public WingClass mapRow(ResultSet rs, int rowNum) throws SQLException {
		WingClass model = new WingClass();
		model.setClassId(rs.getInt("class_id"));
		model.setClassName(rs.getString("class_name"));
		return model ;
	}
}
