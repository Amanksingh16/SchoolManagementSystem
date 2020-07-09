package kmsg.sms.admin.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kmsg.sms.admin.daoint.AdminDaoInt;
import kmsg.sms.admin.mapper.AdminMapper;
import kmsg.sms.admin.model.AdminModel;
import kmsg.sms.common.Constants;
import kmsg.sms.common.Util;

@Repository
public class AdminDaoImpl implements AdminDaoInt
{
	@Autowired
	JdbcTemplate template;
	
	@Override
	public Map<String, Object> selectUserDetails(String email) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "SELECT "
				+ "admin_id"
				+ ",password"
				+ ",salt"
				+ ",name "
				+ " FROM admin a"
				+ " WHERE email = ?";
				
		try {
			List<AdminModel> list = template.query(SQL, new Object[]{email},new AdminMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Exist");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("adminModel",list.get(0));
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}
	
	@Override
	public boolean updateAdminLastActive(int adminId) 
	{
		String SQL = " UPDATE admin "
				   + " SET "
				   + " last_active = STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') "
				   + " WHERE admin_id = ? ";
		
		return template.update(SQL, new Object[]{Util.Now(),adminId}) == 1;
	}

	@Override
	public String getAdminLastActive(int adminId) 
	{
		String SQL = " SELECT last_active "
				   + " FROM admin "
				   + " WHERE admin_id = ? ";
		return template.queryForObject(SQL, new Object[]{adminId} , String.class);
	}

	@Override
	public boolean forgotPassword(String userEmail, String originalPwdHash, String salt) 
	{
		String sql = " UPDATE admin SET password = ?,salt = ? WHERE email = ? ";
		return template.update(sql, new Object[] {originalPwdHash, salt, userEmail})==1;
	}
	
	@Override
	public String getSalt(String emailId) {
		String sql = " SELECT salt FROM admin WHERE email = ? ";
		return template.queryForObject(sql, new Object[]{ emailId }, String.class);
	}

	@Override
	public boolean changePassword(String emailId, String oldPasswordHash, String newPasswordHash) 
	{
		String sql = "UPDATE admin SET password = ? WHERE email = ? AND password = ?";
		return template.update(sql,new Object[]{newPasswordHash,emailId,oldPasswordHash})==1;
	}
}
