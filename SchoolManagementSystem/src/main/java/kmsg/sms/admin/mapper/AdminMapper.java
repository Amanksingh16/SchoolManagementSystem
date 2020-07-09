package kmsg.sms.admin.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.admin.model.AdminModel;

public class AdminMapper implements RowMapper<AdminModel>
{
	@Override
	public AdminModel mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		AdminModel model = new AdminModel();
		model.setAdminId(rs.getInt("admin_id"));
		model.setName(rs.getString("name"));
		model.setPassword(rs.getString("password"));
		model.setSalt(rs.getString("salt"));
		return model;
	}

}
