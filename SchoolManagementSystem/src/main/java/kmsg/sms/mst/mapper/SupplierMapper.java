package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.mst.model.Supplier;

public class SupplierMapper implements RowMapper<Supplier>{

	@Override
	public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Supplier model = new Supplier();
		
		model.setSupplierId(rs.getInt("supplier_id"));
		model.setSupplierCode(rs.getString("supplier_code"));
		model.setSupplier(rs.getString("supplier"));
		model.setGender(rs.getString("gender"));
		model.setMobileNo(rs.getString("mobile_no"));
		model.setSecMobileNo(rs.getString("sec_mobile_no"));
		model.setEmail(rs.getString("email"));
		model.setBusinessName(rs.getString("business_name"));
		model.setAddress1(rs.getString("address_1"));
		model.setAddress2(rs.getString("address_2"));
		model.setStateId(rs.getInt("state_id"));
		model.setCityId(rs.getInt("city_id"));
		model.setGstin(rs.getString("gstin"));
		model.setCategory(rs.getString("category"));
		model.setBlocked(rs.getBoolean("blocked"));
		
		return model;
	}

}
