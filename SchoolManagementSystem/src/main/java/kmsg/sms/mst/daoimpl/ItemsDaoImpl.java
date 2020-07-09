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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoint.ItemsDaoInt;
import kmsg.sms.mst.mapper.ItemsMapper;
import kmsg.sms.mst.model.Items;

@Repository
public class ItemsDaoImpl implements ItemsDaoInt, SMSLogger{
	
	
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public Map<String, Object> getItemList() {
		final String SQL = 
				" SELECT item_id,"
				+ " item_code,"
				+ " item, "
				+ " des,"
				+ " unit,"
				+ " rate,"
				+ " opening_bal,"
				+ " DATE_FORMAT(opening_bal_dt,'%d-%m-%y') as opening_bal_dt,"
				+ " DATE_FORMAT(expiry_dt,'%d-%m-%y') as expiry_dt,"
				+ " min_qty,"
				+ " has_sub_items"
				+ " FROM "+schoolId+"_mst_items";
		
		List<Items> list = new ArrayList<>();
		try {
			list = template.query(SQL, new ItemsMapper());
		} 
		catch (EmptyResultDataAccessException e) {
			System.out.println(e);
			logger.error("selectItem: No Item found");
			return SvcStatus.GET_FAILURE("No Item found. Contact System admin");
		}
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectItem: Exception in selecting Item " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Item. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstItems",  list );
		return result ;
	}

	@Override
	public Map<String, Object> saveItem(Items model) {
		
		final String SQL = "INSERT INTO "+schoolId+"_mst_items(item_code, item, des, unit, rate, opening_bal, opening_bal_dt, expiry_dt, min_qty, has_sub_items)"
				+ "VALUES (?,?,?,?,?,?,STR_TO_DATE(?,%d-%m-%y),STR_TO_DATE(?,%d-%m-%y),?,?)";
		
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
							ps.setString( 1, model.getItemCode());
							ps.setString( 2, model.getItem());
							ps.setString( 3, model.getDesc());
							ps.setString( 4, model.getUnit());
							ps.setFloat( 5, model.getRate());
							ps.setInt( 6, model.getOpeningBalance());
							ps.setString( 7, model.getOpeningBalanceDate());
							ps.setString(8, model.getExpiryDate());
							ps.setInt( 8, model.getMinQty());
							ps.setBoolean( 9, model.isHasSubItems());
								
							return ps ;
						}
					}, holder ) ;
			}
			catch(DuplicateKeyException e) {
				logger.error("insertItem : Duplicate Key insertItem:" + model.getItemId());
				return SvcStatus.GET_FAILURE("Item already exist" +  model.getItemId());
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("insertItem :Exception occured in insertItem:" + model.getItemId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in adding Item. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.MSG, "Item is added");
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				return data;
			}
			else {
				return SvcStatus.GET_FAILURE("Item could not be added. Contact System Admin");
			}
	}

	@Override
	public Map<String, Object> updateItem(Items model) {
		
		final String SQL = "UPDATE "+schoolId+"_mst_items SET item_code = ? ,"
				+ "item=?,"
				+ "des=?,"
				+ "unit=?,"
				+ "rate=?,"
				+ "opening_bal=?,"
				+ "opening_bal_dt=STR_TO_DATE(?,%d-%m-%y),"
				+ "expiry_dt=STR_TO_DATE(?,%d-%m-%y),"
				+ "min_qty=?,"
				+ "has_sub_items=?"
				+ " WHERE item_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
							model.getItemCode(),
							model.getItem(),
							model.getDesc(),
							model.getUnit(),
							model.getRate(),
							model.getOpeningBalance(),
							model.getOpeningBalanceDate(),
							model.getExpiryDate(),
							model.getMinQty(),
							
					} );
			}
			catch(DuplicateKeyException e) {
				logger.error("updateItem : Duplicate Key for this Item:" + model.getItemCode());
				return SvcStatus.GET_FAILURE("Item already exist");
			}
			catch(Exception e) {
				logger.error("updateItem :Exception occured in updateItem:" + model.getItemCode() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating Item. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Item Updated");
				return data;
			}
			else {
				logger.error("updateItem : Failed to update Item :" +  model.getItemCode());
				return SvcStatus.GET_FAILURE("Item could not be updated. Contact System Admin");
			}
	}
	
	

}
