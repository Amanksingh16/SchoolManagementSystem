package kmsg.sms.fees.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.fees.model.ClassFeeHead;

public class FeeClassHeadMapper implements RowMapper<ClassFeeHead>
{
	@Override
	public ClassFeeHead mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		ClassFeeHead cfh = new ClassFeeHead();
		
		cfh.setFeeClassId(rs.getInt("fee_class_id"));
		cfh.setFeeHeadId(rs.getInt("fee_head_id"));
		cfh.setFeeHeadCode(rs.getString("fee_head_code"));
		cfh.setFeeFrequency(rs.getString("fee_frequency"));
		cfh.setCollectionFrequency(rs.getString("collection_frequency"));
		cfh.setAmount(rs.getInt("amount"));
		cfh.setLabel(rs.getString("label"));
		
		return cfh;
	}
}
