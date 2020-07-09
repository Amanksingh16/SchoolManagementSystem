package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.Constants;
import kmsg.sms.common.EmailType;
import kmsg.sms.common.SHAHandler;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.common.Util;
import kmsg.sms.common.AutoEmail.AutomaticMailsDao;
import kmsg.sms.common.AutoEmail.AutomaticMailsModel;
import kmsg.sms.mst.daoimpl.SupplierDaoImpl;
import kmsg.sms.mst.model.Supplier;

@Component
public class SupplierAdapter implements SMSLogger
{	
	@Autowired
	SupplierDaoImpl dao;
	
	@Autowired
	AutomaticMailsDao emailDao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> getSupplierList() 
	{	
		return dao.getSupplierList();
	}
	
	public Map<String, Object> addSupplier(String supplier)
	{	
		Supplier model = new Supplier();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(supplier, Supplier.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occurred in saving Supplier.");
		}
		if(model.getSupplierId() == 0)
			return dao.saveSupplier(model);
		else
			return dao.updateSupplier(model);
	}

	public Map<String, Object> generateSupplierPassword(String userEmail) 
	{
		String newGeneratedPwd		=	Util.generateRandomPassword();
		String salt 				= 	SHAHandler.getNextSalt().toString();
		String originalPwdHash 		=	SHAHandler.generateHash(newGeneratedPwd + salt);
		
		try
		{
			if(dao.forgotPassword(userEmail,originalPwdHash,salt))
			{
				int schoolId = dao.getId(userEmail);
				
				Constants.forgotPassword.put(schoolId, newGeneratedPwd);
				
				AutomaticMailsModel model = new AutomaticMailsModel();
				model.setEmail(userEmail);
				model.setType(EmailType.SUPPLIER_FORGOTPASSWORD);
				model.setIdValue(schoolId);
				
				if(emailDao.addNewMail(model))
					return SvcStatus.GET_SUCCESS("Email Sent Succesfully");
			}
			return SvcStatus.GET_FAILURE("Something Went Wrong in Forgot Password");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception Caught in Forgot Password,Due to: "+e);
			return SvcStatus.GET_FAILURE("Oops,Something Went Wrong,Please Contact System Administrator");
		}
	}
}
