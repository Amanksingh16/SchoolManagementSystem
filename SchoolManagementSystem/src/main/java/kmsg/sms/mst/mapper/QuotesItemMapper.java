package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.QuotesItem;

public class QuotesItemMapper implements RowMapper<QuotesItem>
{

	@Override
	public QuotesItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuotesItem item = new QuotesItem();
		item.setQuoteItemId(rs.getInt("quote_item_id"));
		item.setQuoteId(rs.getInt("quote_id"));
		item.setItemId(rs.getInt("item_id"));
		item.setQty(rs.getInt("qty"));
		item.setUnit(rs.getString("unit"));
		item.setItem(rs.getString("item"));
		item.setItemCode(rs.getString("item_code"));
		item.setExpRate(rs.getFloat("exp_rate"));
		item.setRequirement1(rs.getString("requirement_1"));
		item.setRequirement2(rs.getString("requirement_2"));
		item.setRequirement3(rs.getString("requirement_3"));
		item.setRequirement4(rs.getString("requirement_4"));
		return item;
	}

}
