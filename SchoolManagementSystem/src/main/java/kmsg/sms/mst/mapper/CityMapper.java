package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.CityModel;

public class CityMapper implements RowMapper<CityModel> {

	@Override
	public CityModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new CityModel(rs.getInt("mst_city_id"), rs.getInt("state_id"), rs.getString("city"));	}

}
