package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.SubItem;

public class SubItemMapper implements RowMapper<SubItem>{

	@Override
	public SubItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SubItem model = new SubItem();
		
		model.setSubItemId(rs.getInt("sub_item_id"));
		model.setItemId(rs.getInt("item_id"));
		model.setChildItemId(rs.getInt("child_item_id"));
		model.setQty(rs.getInt("qty"));
		model.setItem(rs.getString("item"));
		model.setItemCode(rs.getString("item_code"));
		model.setUnit(rs.getString("unit"));
		model.setRate(rs.getFloat("rate"));
		
		return model;
	}

}
