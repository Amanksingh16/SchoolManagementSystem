package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.StateModel;

public class StateMapper implements RowMapper<StateModel>
{

	@Override
	public StateModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new StateModel(rs.getInt("mst_state_id"), rs.getString("state_label"));
	}

}
