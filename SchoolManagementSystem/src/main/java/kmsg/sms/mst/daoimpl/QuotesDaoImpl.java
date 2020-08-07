package kmsg.sms.mst.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoint.QuotesDaoInt;
import kmsg.sms.mst.mapper.QuotesItemMapper;
import kmsg.sms.mst.mapper.QuotesMapper;
import kmsg.sms.mst.mapper.QuotesSupplierItemMapper;
import kmsg.sms.mst.mapper.QuotesSupplierMapper;
import kmsg.sms.mst.model.QuoteSupplier;
import kmsg.sms.mst.model.QuoteSupplierItem;
import kmsg.sms.mst.model.QuotesItem;
import kmsg.sms.mst.model.QuotesModel;

@Repository
public class QuotesDaoImpl implements QuotesDaoInt, SMSLogger
{
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public Map<String, Object> saveQuote(QuotesModel model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_quote(quote_no, quote_dt, quote_expiry_dt, exp_del_dt)"
				+ "VALUES (?,STR_TO_DATE(?,'%d-%m-%Y'),STR_TO_DATE(?,'%d-%m-%Y'),STR_TO_DATE(?,'%d-%m-%Y'))";
		
		 int count = 0;
			KeyHolder holder = new GeneratedKeyHolder();
			try {
				 count = template.update (
					new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(
														SQL,
														Statement.RETURN_GENERATED_KEYS
																		);
							ps.setString( 1, model.getQuoteNo());
							ps.setString( 2, model.getQuoteDt());
							ps.setString( 3, model.getQuoteExpiryDt());
							ps.setString( 4, model.getExpDelDt());
							
							return ps ;
						}
					}, holder ) ;
			}
			catch(DuplicateKeyException e) {
				logger.error("insertQuote : Duplicate Key insertQuote:" + model.getQuoteId());
				return SvcStatus.GET_FAILURE("Quote already exist" +  model.getQuoteId());
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("insertQuote :Exception occured in insertQuote:" + model.getQuoteId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in adding Quote. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.MSG, "Quote is added");
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put("quoteId",holder.getKey().intValue());
				return data;
			}
			else {
				return SvcStatus.GET_FAILURE("Quote could not be added. Contact System Admin");
			}
	}

	public Map<String, Object> updateQuote(QuotesModel model) 
	{
		final String SQL = "UPDATE "+schoolId+"_quote SET quote_no = ?,"
				+ " quote_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " quote_expiry_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " exp_del_dt = STR_TO_DATE(?,'%d-%m-%Y')"
				+ " WHERE quote_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
							model.getQuoteNo(),
							model.getQuoteDt(),
							model.getQuoteExpiryDt(),
							model.getExpDelDt(),
							model.getQuoteId()
					} );
			}
			catch(DuplicateKeyException e) {
				logger.error("updateQuote : Duplicate Key for this Quote:" + model.getQuoteNo());
				return SvcStatus.GET_FAILURE("Quote already exist");
			}
			catch(Exception e) {
				logger.error("updateQuote :Exception occured in updateQuote:" + model.getQuoteNo() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating Quote. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Quote Updated");
				return data;
			}
			else {
				logger.error("updateQuote : Failed to update Quote :" +  model.getQuoteNo());
				return SvcStatus.GET_FAILURE("Quote could not be updated. Contact System Admin");
			}
	}

	public Map<String, Object> selectQuote() 
	{		
		final String SQL = 
				"	SELECT quote_id," + 
				"	quote_no," + 
				"	DATE_FORMAT(quote_dt,'%d-%m-%Y') as quote_dt," + 
				"	DATE_FORMAT(quote_expiry_dt,'%d-%m-%Y') as quote_expiry_dt," + 
				"	DATE_FORMAT(exp_del_dt,'%d-%m-%Y') as exp_del_dt," + 
				"	(SELECT count(*) FROM "+schoolId+"_quote_supplier qs WHERE qs.quote_id = q.quote_id AND qs.sent_dt IS NOT NULL) sent_count," + 
				"	(SELECT count(*) FROM "+schoolId+"_quote_supplier qs WHERE qs.quote_id = q.quote_id AND qs.supplier_response_dt IS NOT NULL) response_count" + 
				"	FROM "+schoolId+"_quote q";
		
		List<QuotesModel> list = new ArrayList<>();
		try {
			list = template.query(SQL, new QuotesMapper());
			if(list.size()==0)
			{
				logger.error("selectQuote: No Quotes found");
				return SvcStatus.GET_FAILURE("No Quotes found");
			}
		} 
		catch (Exception e) {
			logger.error("selectQuote: Exception in selecting Quote " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Quote. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstQuote", list);
		return result ;
	}

	public Map<String, Object> selectQuoteItems(int quoteId) 
	{
		final String SQL = 
				" SELECT quote_item_id,"
				+ " quote_id,"
				+ " mi.item_id,"
				+ " qty,"
				+ " mi.unit,"
				+ " mi.item_code,"
				+ " mi.item,"
				+ " exp_rate,"
				+ " IFNULL(requirement_1,'') as requirement_1,"
				+ " IFNULL(requirement_2,'') as requirement_2,"
				+ " IFNULL(requirement_3,'') as requirement_3,"
				+ " IFNULL(requirement_4,'') as requirement_4"
				+ " FROM "+schoolId+"_quote_item qi"
				+ " JOIN "+schoolId+"_mst_items mi ON mi.item_id = qi.item_id"
			    + " WHERE quote_id = ?";
		
		List<QuotesItem> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {quoteId}, new QuotesItemMapper());
			if(list.size()==0)
			{
				logger.error("selectQuoteItem: No Items found for this quote");
				return SvcStatus.GET_FAILURE("No Items found for this quote");
			}
		} 
		catch (Exception e) {
			logger.error("selectQuoteItem: Exception in selecting Quote Item" + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Quote Item. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstQuoteItems", list);
		return result ;
	}

	public Map<String, Object> saveQuoteItem(QuotesItem model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_quote_item(quote_id, item_id, qty, exp_rate, requirement_1, requirement_2, requirement_3, requirement_4)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		
		 int count = 0;
			KeyHolder holder = new GeneratedKeyHolder();
			try {
				 count = template.update (
					new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(
														SQL,
														Statement.RETURN_GENERATED_KEYS
																		);
							ps.setInt( 1, model.getQuoteId());
							ps.setInt( 2, model.getItemId());
							ps.setInt( 3, model.getQty());
							ps.setFloat( 4, model.getExpRate());
							ps.setString( 5, model.getRequirement1());
							ps.setString( 6, model.getRequirement2());
							ps.setString( 7, model.getRequirement3());
							ps.setString( 8, model.getRequirement4());
							
							return ps ;
						}
					}, holder ) ;
			}
			catch(DuplicateKeyException e) {
				logger.error("insertQuoteItem : Duplicate Key insertQuoteItem:" + model.getQuoteId());
				return SvcStatus.GET_FAILURE("Quote Item already exist" +  model.getQuoteId());
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("insertQuoteItem :Exception occured in insertQuoteItem:" + model.getQuoteId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in adding Quote Item. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.MSG, "Quote Item is added");
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put("quoteItemId",holder.getKey().intValue());
				return data;
			}
			else {
				return SvcStatus.GET_FAILURE("Quote Item could not be added. Contact System Admin");
			}
	}

	public Map<String, Object> updateQuoteItem(QuotesItem model)
	{
		final String SQL = "UPDATE "+schoolId+"_quote_item SET quote_id = ?,"
				+ " item_id = ?,"
				+ " qty=?,"
				+ " exp_rate = ?,"
				+ " requirement_1 = ?,"
				+ " requirement_2 = ?,"
				+ " requirement_3 = ?,"
				+ " requirement_4 = ?"
				+ " WHERE quote_item_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
							model.getQuoteId(),
							model.getItemId(),
							model.getQty(),
							model.getExpRate(),
							model.getRequirement1(),
							model.getRequirement2(),
							model.getRequirement3(),
							model.getRequirement4(),
							model.getQuoteItemId()
					} );
			}
			catch(DuplicateKeyException e) {
				logger.error("updateQuoteItem : Duplicate Key for this Quote Item:" + model.getQuoteItemId());
				return SvcStatus.GET_FAILURE("Quote Item already exist");
			}
			catch(Exception e) {
				logger.error("updateQuoteItem :Exception occured in updateQuoteItem:" + model.getQuoteItemId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating Quote Item. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Quote Item Updated");
				return data;
			}
			else {
				logger.error("updateQuoteItem : Failed to update Quote Item:" +  model.getQuoteItemId());
				return SvcStatus.GET_FAILURE("Quote Item could not be updated. Contact System Admin");
			}
	}

	public Map<String, Object> selectQuoteSupplier(int quoteSupplierId) 
	{
		final String SQL = 
				" SELECT quote_supplier_id,"
				+ " quote_id,"
				+ " s.supplier_id,"
				+ " s.supplier,"
				+ " s.supplier_code,"
				+ " s.business_name,"
				+ " DATE_FORMAT(sent_dt,'%d-%m-%Y') sent_dt,"
				+ " supp_response,"
				+ " DATE_FORMAT(supplier_response_dt,'%d-%m-%Y') as supplier_response_dt,"
				+ " IFNULL(supplier_remarks,'') as supplier_remarks"
				+ " FROM "+schoolId+"_quote_supplier qs"
				+ " JOIN supplier s ON s.supplier_id = qs.supplier_id"
			    + " WHERE quote_id = ?";
		
		List<QuoteSupplier> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {quoteSupplierId}, new QuotesSupplierMapper());
			if(list.size()==0)
			{
				logger.error("selectQuoteSupplier: No quote supplier found");
				return SvcStatus.GET_FAILURE("No Supplier found");
			}
		} 
		catch (Exception e) {
			logger.error("selectQuoteSupplier: Exception in selecting Quote Supplier" + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Quote Supplier. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstQuoteSupplier", list);
		return result ;
	}

	public Map<String, Object> saveQuoteSupplier(QuoteSupplier model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_quote_supplier(quote_id, supplier_id, sent_dt, supp_response, supplier_response_dt , supplier_remarks)"
				+ "VALUES (?,?,STR_TO_DATE(?,'%d-%m-%Y'),?,STR_TO_DATE(?,'%d-%m-%Y'),?)";
		
		 int count = 0;
			KeyHolder holder = new GeneratedKeyHolder();
			try {
				 count = template.update (
					new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(
														SQL,
														Statement.RETURN_GENERATED_KEYS
																		);
							ps.setInt( 1, model.getQuoteId());
							ps.setInt( 2, model.getSupplierId());
							ps.setString( 3, model.getSentDt());
							ps.setString( 4, model.getSuppResponse());
							ps.setString( 5, model.getSupplierResponseDt());
							ps.setString( 6, model.getSupplierRemarks());
							
							return ps ;
						}
					}, holder ) ;
			}
			catch(DuplicateKeyException e) {
				logger.error("insertQuoteSupplier : Duplicate Key insertQuoteSupplier:" + model.getQuoteId());
				return SvcStatus.GET_FAILURE("Quote Item already exist" +  model.getQuoteId());
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("insertQuoteSupplier :Exception occured in insertQuoteSupplier:" + model.getQuoteId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in adding Quote Supplier. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.MSG, "Quote Supplier is added");
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put("quoteSupplierId",holder.getKey().intValue());
				return data;
			}
			else {
				return SvcStatus.GET_FAILURE("Quote Supplier could not be added. Contact System Admin");
			}
	}

	public Map<String, Object> updateQuoteSupplier(QuoteSupplier model) 
	{
		final String SQL = "UPDATE "+schoolId+"_quote_supplier SET quote_id = ?,"
				+ " supplier_id = ?,"
				+ " sent_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " supp_response = ?,"
				+ " supplier_response_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " supplier_remarks = ?"
				+ " WHERE quote_supplier_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
							model.getQuoteId(),
							model.getSupplierId(),
							model.getSentDt(),
							model.getSuppResponse(),
							model.getSupplierResponseDt(),
							model.getSupplierRemarks(),
							model.getQuoteSupplierId()
					} );
			}
			catch(DuplicateKeyException e) {
				logger.error("updateQuoteSupplier : Duplicate Key for this Quote Suppler:" + model.getQuoteSupplierId());
				return SvcStatus.GET_FAILURE("Quote Supplier already exist");
			}
			catch(Exception e) {
				logger.error("updateQuoteSupplier :Exception occured in updateQuoteSupplier:" + model.getQuoteSupplierId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating Quote Supplier. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Quote Supplier Updated");
				return data;
			}
			else {
				logger.error("updateQuoteSupplier : Failed to update Quote Supplier:" +  model.getQuoteSupplierId());
				return SvcStatus.GET_FAILURE("Quote Supplier could not be updated. Contact System Admin");
			}
	}

	public Map<String, Object> selectQuoteSupplierItem(int quoteSupplierItemId) 
	{
		final String SQL = 
				" SELECT quote_supplier_item_id,"
				+ " quote_supplier_id,"
				+ " quote_id,"
				+ " mi.item_id,"
				+ " mi.item_code,"
				+ " mi.item,"
				+ " mi.unit,"
				+ " qty,"
				+ " supp_rate,"
				+ " (qty*supp_rate) as total,"				
				+ " DATE_FORMAT(supp_del_dt,'%d-%m-%Y') as supp_del_dt,"
				+ " requirement_1_response,"
				+ " requirement_2_response,"
				+ " requirement_3_response,"
				+ " requirement_4_response,"
				+ " supplier_remarks"
				+ " FROM "+schoolId+"_quote_supplier_item qsi"
				+ " JOIN "+schoolId+"_mst_items mi ON mi.item_id = qsi.item_id"
			    + " WHERE quote_supplier_id = ?";
		
		List<QuoteSupplierItem> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {quoteSupplierItemId}, new QuotesSupplierItemMapper());
			if(list.size()==0)
			{
				logger.error("selectQuoteSupplierItem: No Items found for this quote supplier");
				return SvcStatus.GET_FAILURE("No Items found for this quote supplier");
			}
		} 
		catch (Exception e) {
			logger.error("selectQuoteSupplierItem: Exception in selecting Quote Supplier Item" + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Quote Supplier Item. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstQuoteSupplierItem", list);
		return result ;
	}

	public Map<String, Object> saveQuoteSupplierItem(QuoteSupplierItem model) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_quote_supplier_item(quote_id, quote_supplier_id, item_id, qty, supp_rate, supp_del_dt, requirement_1_response , requirement_2_response ,requirement_3_response ,requirement_4_response ,supplier_remarks )"
				+ "VALUES (?,?,?,?,?,STR_TO_DATE(?,'%d-%m-%Y'),?,?,?,?,?)";
		
		 int count = 0;
			KeyHolder holder = new GeneratedKeyHolder();
			try {
				 count = template.update (
					new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(
														SQL,
														Statement.RETURN_GENERATED_KEYS
																		);
							ps.setInt( 1, model.getQuoteId());
							ps.setInt( 2, model.getQuoteSupplierId());
							ps.setInt( 3, model.getItemId());
							ps.setInt( 4, model.getQty());
							ps.setFloat( 5, model.getSuppRate());
							ps.setString( 6, model.getSuppDelDt());
							ps.setString(7, model.getRequirement1Response());
							ps.setString(8, model.getRequirement2Response());
							ps.setString(9, model.getRequirement3Response());
							ps.setString(10, model.getRequirement4Response());
							ps.setString(11, model.getSupplierRemarks());
							return ps ;
						}
					}, holder ) ;
			}
			catch(DuplicateKeyException e) {
				logger.error("insertQuoteSupplierItem : Duplicate Key insertQuoteSupplierItem:" + model.getQuoteSupplierId());
				return SvcStatus.GET_FAILURE("Quote Supplier Item already exist" +  model.getQuoteId());
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("insertQuoteSupplierItem :Exception occured in insertQuoteSupplierItem:" + model.getQuoteSupplierId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in adding Quote Supplier Item. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.MSG, "Quote Supplier Item is added");
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put("quoteSupplierItemId",holder.getKey().intValue());
				return data;
			}
			else {
				return SvcStatus.GET_FAILURE("Quote Supplier could not be added. Contact System Admin");
			}
	}

	public Map<String, Object> updateQuoteSupplierItem(QuoteSupplierItem model) 
	{
		final String SQL = "UPDATE "+schoolId+"_quote_supplier_item SET quote_id = ?,"
				+ " quote_supplier_id = ?,"
				+ " item_id = ?,"
				+ " qty = ?,"
				+ " supp_rate = ?,"
				+ " supp_del_dt = STR_TO_DATE(?,'%d-%m-%Y'),"
				+ " requirement_1_response = ?,"
				+ " requirement_2_response = ?,"
				+ " requirement_3_response = ?,"
				+ " requirement_4_response = ?,"
				+ " supplier_remarks = ?"
				+ " WHERE quote_supplier_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
							model.getQuoteId(),
							model.getQuoteSupplierId(),
							model.getItemId(),
							model.getQty(),
							model.getSuppRate(),
							model.getSuppDelDt(),
							model.getRequirement1Response(),
							model.getRequirement2Response(),
							model.getRequirement3Response(),
							model.getRequirement4Response(),
							model.getSupplierRemarks(),
							model.getQuoteSupplierId()
					} );
			}
			catch(DuplicateKeyException e) {
				logger.error("updateQuoteSupplierItem : Duplicate Key for this Quote Suppler Item:" + model.getQuoteSupplierId());
				return SvcStatus.GET_FAILURE("Quote Supplier Item already exist");
			}
			catch(Exception e) {
				logger.error("updateQuoteSupplierItem :Exception occured in updateQuoteSupplierItem:" + model.getQuoteSupplierId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating Quote Supplier Item. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Quote Supplier Item Updated");
				return data;
			}
			else {
				logger.error("updateQuoteSupplierItem : Failed to update Quote Supplier Item:" +  model.getQuoteSupplierId());
				return SvcStatus.GET_FAILURE("Quote Supplier Item could not be updated. Contact System Admin");
			}
	}
}
