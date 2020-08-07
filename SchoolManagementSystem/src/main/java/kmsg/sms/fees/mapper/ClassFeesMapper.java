package kmsg.sms.fees.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.fees.model.ClassFees;

public class ClassFeesMapper implements RowMapper<ClassFees>
{
	@Override
	public ClassFees mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		ClassFees fee = new ClassFees();
		fee.setClassId(rs.getInt("class_id"));
		fee.setClassName(rs.getString("class_name"));
		fee.setTotalFees(rs.getInt("total_fees"));
		return fee;
	}
}
