package kmsg.sms.mst.daoint;

import java.util.Map;

import kmsg.sms.mst.model.Supplier;

public interface SupplierDaoInt {

	 Map<String, Object> getSupplierList();
	 
	 Map<String, Object> saveSupplier(Supplier model);
	 
	 Map<String, Object> updateSupplier(Supplier model);
	 
	 void setSchoolId(int schoolId);

	int getId(String emailId);

	int forgotPassword(String email, String originalPwdHash, String salt);
}
