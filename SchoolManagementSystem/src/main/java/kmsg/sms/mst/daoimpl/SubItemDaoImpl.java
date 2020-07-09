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
import kmsg.sms.mst.daoint.SubItemDaoInt;
import kmsg.sms.mst.mapper.SubItemMapper;
import kmsg.sms.mst.model.SubItem;


@Repository
public class SubItemDaoImpl implements SubItemDaoInt, SMSLogger{

	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	
	
	@Override
	public Map<String, Object> getSubItemList(int itemId) {
		
		final String SQL = 
				"SELECT msi.sub_item_id,"
				+ " msi.item_id,"
				+ " msi.child_item_id,"
				+ " msi.qty, "
				+ " mi.item,"
				+ " mi.unit, "
				+ " mi.item_code,"
				+ " mi.rate "
				+ " FROM "+schoolId+"_mst_sub_item msi "
				+ " JOIN "+schoolId+"_mst_items mi ON mi.item_id = msi.child_item_id"
				+ " WHERE msi.item_id = ?";
		List<SubItem> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {itemId}, new SubItemMapper());
		} 
		catch (EmptyResultDataAccessException e) {
			System.out.println(e);
			logger.error("selectSubItem: No Sub Item found");
			return SvcStatus.GET_FAILURE("No Sub Item found. Contact System admin");
		}
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectSubItem: Exception in selecting Sub Item " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Sub Item. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstSubItem",  list );
		return result ;
	}

	@Override
	public Map<String, Object> saveSubItem(SubItem model) {
		final String SQL = "INSERT INTO "+schoolId+"_mst_sub_item(item_id, child_item_id, qty)"
				+ "VALUES (?,?,?)";
		
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
						ps.setInt( 1, model.getItemId());
						ps.setInt( 2, model.getChildItemId());
						ps.setInt( 3, model.getQty());
							
						return ps ;
					}
				}, holder ) ;
		}
		catch(DuplicateKeyException e) {
			logger.error("insertSubItem : Duplicate Key insertSubItem:" + model.getSubItemId());
			return SvcStatus.GET_FAILURE("Sub Item already exist" +  model.getSubItemId());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("insertSubItem :Exception occured in insertSubItem:" + model.getSubItemId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in adding Sub Item. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.MSG, "Sub Item is added");
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			return SvcStatus.GET_FAILURE("Sub Item could not be added. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> updateSubItem(SubItem model) {
		

		final String SQL =
				"UPDATE "+schoolId+"_mst_sub_item SET item_id = ? ,"
				+ " child_item_id=?, "
				+ " qty=?"
				+ " WHERE sub_item_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
							model.getItemId(),
							model.getChildItemId(),
							model.getQty(),
							
					} );
			}
			catch(DuplicateKeyException e) {
				logger.error("updateSubItem : Duplicate Key for this SubItem:" + model.getItemId());
				return SvcStatus.GET_FAILURE("SubItem already exist");
			}
			catch(Exception e) {
				logger.error("updateSubItem :Exception occured in updateSubItem:" + model.getItemId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating SubItem. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "SubItem Updated");
				return data;
			}
			else {
				logger.error("updateSubItem : Failed to update SubItem :" +  model.getItemId());
				return SvcStatus.GET_FAILURE("SubItem could not be updated. Contact System Admin");
			}
	}


}
