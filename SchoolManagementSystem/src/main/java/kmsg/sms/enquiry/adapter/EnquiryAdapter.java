package kmsg.sms.enquiry.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.enquiry.daoimpl.EnquiryDaoImpl;
import kmsg.sms.enquiry.model.Enquiry;

@Component
public class EnquiryAdapter implements SMSLogger {
	@Autowired
	EnquiryDaoImpl dao;	
	
	public void setSchoolId(int schoolId) {
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> getEnquiry() {
		return dao.selectEnquiry();
	}
	
	public Map<String, Object> saveEnquiry( String strEnquiry ) {
		Enquiry model = new Enquiry();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strEnquiry, Enquiry.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception in Enquiry data");
		}
		return dao.insertEnquiry(model);
	}
}
