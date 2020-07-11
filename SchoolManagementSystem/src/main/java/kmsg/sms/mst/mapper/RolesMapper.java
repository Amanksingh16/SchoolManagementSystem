package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.Roles;

public class RolesMapper implements RowMapper<Roles> {

	@Override
	public Roles mapRow( ResultSet rs, int rowNum) throws SQLException {
		return new Roles(
				rs.getInt("role_id")
				, rs.getString("role_name")
				, rs.getString("role_desc")
				);
	}

}
