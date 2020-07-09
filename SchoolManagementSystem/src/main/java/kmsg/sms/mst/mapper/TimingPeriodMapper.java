package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.TimingPeriodList;

public class TimingPeriodMapper implements RowMapper<TimingPeriodList>
{
	@Override
	public TimingPeriodList mapRow(ResultSet rs, int rowNum) throws SQLException {
		TimingPeriodList prd = new TimingPeriodList();
		prd.setTimingPeriodId(rs.getInt("timing_period_id"));
		prd.setPeriod(rs.getString("period"));
		prd.setPeriodType(rs.getString("period_type"));
		prd.setStartTm(rs.getString("start_tm"));
		prd.setDuration(rs.getInt("duration"));
		return prd;
	}
}
