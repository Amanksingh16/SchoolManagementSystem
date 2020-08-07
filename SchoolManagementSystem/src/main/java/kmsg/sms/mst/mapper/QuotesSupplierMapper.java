package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.common.SupplierResponse;
import kmsg.sms.mst.model.QuoteSupplier;

public class QuotesSupplierMapper implements RowMapper<QuoteSupplier>
{
	@Override
	public QuoteSupplier mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		QuoteSupplier sup = new QuoteSupplier();
		sup.setQuoteSupplierId(rs.getInt("quote_supplier_id"));
		sup.setSupplierId(rs.getInt("supplier_id"));
		sup.setQuoteId(rs.getInt("quote_id"));
		sup.setSentDt(rs.getString("sent_dt"));
		sup.setSupplierCode(rs.getString("supplier_code"));
		sup.setSupplier(rs.getString("supplier"));
		sup.setBusinessName(rs.getString("business_name"));
		sup.setSuppResponse(SupplierResponse.get(rs.getInt("supp_response")));
		sup.setSupplierResponseDt(rs.getString("supplier_response_dt"));
		sup.setSupplierRemarks(rs.getString("supplier_remarks"));
		return sup;
	}
}
