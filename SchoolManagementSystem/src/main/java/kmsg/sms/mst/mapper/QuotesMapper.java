package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.QuotesModel;

public class QuotesMapper implements RowMapper<QuotesModel>
{
	@Override
	public QuotesModel mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		QuotesModel model = new QuotesModel();
		model.setQuoteId(rs.getInt("quote_id"));
		model.setQuoteNo(rs.getString("quote_no"));
		model.setQuoteExpiryDt(rs.getString("quote_expiry_dt"));
		model.setExpDelDt(rs.getString("exp_del_dt"));
		model.setQuoteDt(rs.getString("quote_dt"));
		model.setSentCount(rs.getInt("sent_count"));
		model.setResponseCount(rs.getInt("response_count"));
		return model;
	}
}
