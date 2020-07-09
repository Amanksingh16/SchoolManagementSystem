package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.Items;

public class ItemsMapper implements RowMapper<Items>{

	@Override
	public Items mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Items model = new Items();
		
		model.setItemId(rs.getInt("item_id"));
		model.setItemCode(rs.getString("item_code"));
		model.setItem(rs.getString("item"));
		model.setDesc(rs.getString("des"));
		model.setUnit(rs.getString("unit"));
		model.setRate(rs.getFloat("rate"));
		model.setOpeningBalance(rs.getInt("opening_bal"));
		model.setOpeningBalanceDate(rs.getString("opening_bal_dt"));
		model.setExpiryDate(rs.getString("expiry_dt"));
		model.setMinQty(rs.getInt("min_qty"));
		model.setHasSubItems(rs.getBoolean("has_sub_items"));
		
		return model;
	}

}
