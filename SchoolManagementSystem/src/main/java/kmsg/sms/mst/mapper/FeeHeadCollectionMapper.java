package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.FeeCollections;

public class FeeHeadCollectionMapper implements RowMapper<FeeCollections>
{
	@Override
	public FeeCollections mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		FeeCollections col = new FeeCollections();
		col.setMonth(rs.getString("month"));
		col.setDate(rs.getInt("date"));
		col.setAmount(rs.getInt("amount"));
		col.setRemarks(rs.getString("remarks"));
		return col;
	}
}
