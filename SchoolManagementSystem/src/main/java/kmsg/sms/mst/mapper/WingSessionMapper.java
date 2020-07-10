package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.WingSession;

public class WingSessionMapper implements RowMapper<WingSession>
{
	@Override
	public WingSession mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		WingSession ses = new WingSession();
		ses.setWingId(rs.getInt("wing_id"));
		ses.setWing(rs.getString("wing"));
		ses.setWingSessionId(rs.getInt("wing_session_id"));
		ses.setWingSession(rs.getString("wing_session"));
		ses.setSessionType(rs.getString("session_type"));
		ses.setFromDt(rs.getString("from_dt"));
		ses.setToDt(rs.getString("to_dt"));
		return ses;
	}
}
