package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.Timing;

public class TimingMapper implements RowMapper<Timing>
{
	@Override
	public Timing mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Timing model = new Timing();
		model.setTimingId(rs.getInt("timing_id"));
		model.setEffectiveDt(rs.getString("effective_dt"));
		model.setStartTm(rs.getString("start_tm"));
		model.setEndTm(rs.getString("end_tm"));
		model.setWingId(rs.getInt("wing_id"));
		model.setWing(rs.getString("wing"));
		return model;
	}
}
