package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.Classes;

public class MstClassMapper implements RowMapper<Classes>
{

	@Override
	public Classes mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Classes(rs.getInt("class_id"), rs.getString("class_name"));
	}

}
