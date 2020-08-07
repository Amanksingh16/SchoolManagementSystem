package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.QuoteSupplierItem;

public class QuotesSupplierItemMapper implements RowMapper<QuoteSupplierItem>
{
	@Override
	public QuoteSupplierItem mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		QuoteSupplierItem item = new QuoteSupplierItem();
		item.setQuoteSupplierItemId(rs.getInt("quote_supplier_item_id"));
		item.setQuoteId(rs.getInt("quote_id"));
		item.setQuoteSupplierId(rs.getInt("quote_supplier_id"));
		item.setItemId(rs.getInt("item_id"));
		item.setItemCode(rs.getString("item_code"));
		item.setItem(rs.getString("item"));
		item.setUnit(rs.getString("unit"));
		item.setQty(rs.getInt("qty"));
		item.setTotal(rs.getFloat("total"));
		item.setSuppRate(rs.getFloat("supp_rate"));
		item.setSuppDelDt(rs.getString("supp_del_dt"));
		item.setRequirement1Response(rs.getString("requirement_1_response"));
		item.setRequirement2Response(rs.getString("requirement_2_response"));
		item.setRequirement3Response(rs.getString("requirement_3_response"));
		item.setRequirement4Response(rs.getString("requirement_4_response"));
		item.setSupplierRemarks(rs.getString("supplier_remarks"));
		return item;
	}
}
