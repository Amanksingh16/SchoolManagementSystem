package kmsg.sms.common.AutoEmail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AutomaticMailsMapper implements RowMapper<AutomaticMailsModel>
{
	@Override
	public AutomaticMailsModel mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		AutomaticMailsModel model = new AutomaticMailsModel();
		model.setAutoEmailId(rs.getInt("auto_emails_id"));
		model.setEmail(rs.getString("email"));
		model.setIdValue(rs.getInt("id_value"));
		model.setType(rs.getInt("type"));
		return model;
	}
}
