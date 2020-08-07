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
import kmsg.sms.mst.daoint.SupplierDaoInt;
import kmsg.sms.mst.mapper.SupplierMapper;
import kmsg.sms.mst.model.Supplier;

@Repository
public class SupplierDaoImpl implements SupplierDaoInt, SMSLogger{
	

	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public Map<String, Object> getSupplierList() {
		final String SQL = 
				" SELECT supplier_id,"
				+ " supplier_code,"
				+ " supplier, "
				+ " gender,"
				+ " mobile_no,"
				+ " sec_mobile_no,"
				+ " email,"
				+ " business_name,"
				+ " address_1,"
				+ " address_2,"
				+ " ms.state_id,"
				+ " mc.city_id,"
				+ " ms.state,"
				+ " mc.city,"
				+ " gstin,"
				+ " category,"
				+ " blocked"
				+ " FROM supplier s"
				+ " LEFT JOIN mst_state ms ON s.state_id = ms.state_id"
				+ " LEFT JOIN mst_city mc ON s.city_id = mc.city_id"
				+ " WHERE school_id = ?";
		
		List<Supplier> list = new ArrayList<>();
		try {
			list = template.query(SQL,new Object[] {schoolId}, new SupplierMapper());
			if(list.size()==0)
			{
				logger.error("selectSupplier: No supplier found");
				return SvcStatus.GET_FAILURE("No Supplier found");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectSupplier: Exception in selecting Supplier " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting Supplier. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstSupplier",  list );
		return result ;
	}

	@Override
	public Map<String, Object> saveSupplier(Supplier model) {
		
		final String SQL = "INSERT INTO supplier(supplier_code, supplier, gender, mobile_no, sec_mobile_no, email, business_name, address_1, address_2, state_id, city_id, gstin, category, blocked,school_id)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
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
							ps.setString( 1, model.getSupplierCode());
							ps.setString( 2, model.getSupplier());
							ps.setString( 3, model.getGender());
							ps.setString( 4, model.getMobileNo());
							ps.setString( 5, model.getSecMobileNo());
							ps.setString(6, model.getEmail());
							ps.setString( 7, model.getBusinessName());
							ps.setString( 8, model.getAddress1());
							ps.setString( 9, model.getAddress2());
							ps.setInt( 10, model.getStateId());
							ps.setInt( 11, model.getCityId());
							ps.setString( 12, model.getGstin());
							ps.setString( 13, model.getCategory());
							ps.setBoolean( 14, model.isBlocked());
							ps.setInt( 15, schoolId);
								
							return ps ;
						}
					}, holder ) ;
			}
			catch(DuplicateKeyException e) {
				logger.error("insertSupplier : Duplicate Key insertSupplier:" + model.getSupplierId());
				return SvcStatus.GET_FAILURE("Supplier already exist " +  model.getSupplierId());
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("insertSupplier :Exception occured in insertSupplier:" + model.getSupplierId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in adding Supplier. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.MSG, "Supplier is added");
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				return data;
			}
			else {
				return SvcStatus.GET_FAILURE("Supplier could not be added. Contact System Admin");
			}
	}

	@Override
	public Map<String, Object> updateSupplier(Supplier model) {
		
		final String SQL = "UPDATE supplier SET supplier_code = ? ,"
				+ " supplier=?, "
				+ " gender=?,"
				+ " mobile_no=?,"
				+ " sec_mobile_no=?,"
				+ " email=?,"
				+ " business_name=?,"
				+ " address_1=?,"
				+ " address_2=?, "
				+ " state_id=?,"
				+ " city_id=?,"
				+ " gstin=?, "
				+ " category=?, "
				+ " blocked=?"
				+ " WHERE supplier_id = ?";
		
	    int count = 0;
			try {
				 count = template.update (SQL, new Object[]
					 {
							model.getSupplierCode(),
							model.getSupplier(),
							model.getGender(),
							model.getMobileNo(),
							model.getSecMobileNo(),
							model.getEmail(),
							model.getBusinessName(),
							model.getAddress1(),
							model.getAddress2(),
							model.getStateId(),
							model.getCityId(),
							model.getGstin(),
							model.getCategory(),
							model.isBlocked(),
							model.getSupplierId()
							
					} );
			}
			catch(DuplicateKeyException e) {
				logger.error("updateSupplier : Duplicate Key for this Supplier:" + model.getSupplierCode());
				return SvcStatus.GET_FAILURE("Supplier already exist");
			}
			catch(Exception e) {
				logger.error("updateSupplier :Exception occured in updateSupplier:" + model.getSupplierCode() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in updating Supplier. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				data.put(SvcStatus.MSG, "Supplier Updated");
				return data;
			}
			else {
				logger.error("updateSupplier : Failed to update Supplier :" +  model.getSupplierCode());
				return SvcStatus.GET_FAILURE("Supplier could not be updated. Contact System Admin");
			}
	}

	@Override
	public int getId(String emailId) {
		String sql = " SELECT school_id FROM supplier WHERE email = ? ";
		return template.queryForObject(sql, new Object[]{ emailId }, Integer.class);
	}
	
	@Override
	public int forgotPassword(String email, String originalPwdHash, String salt) 
	{
		String sql = "UPDATE supplier SET password = ?,salt = ? WHERE email = ? ";
		return template.update(sql, new Object[] {originalPwdHash, salt, email});
	}
}
