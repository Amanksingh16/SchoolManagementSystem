package kmsg.sms.common;

import java.util.ArrayList;
import java.util.List;

public class EnquiryType {
	public static final int ONLINE 	= 10 ;
	public static final int WALKIN	= 20 ;
	public static final int PHONE 	= 30 ;
	public static final int EVENT 	= 40 ;
	public static final int CUSTOMER_CARE  = 50 ;
	
	public static String get(int enquiryTypeId ) {
		switch( enquiryTypeId ) {
		case ONLINE: return "Online";
		case WALKIN: return "Walkin";
		case PHONE: return "Phone";
		case EVENT: return "Event";
		case CUSTOMER_CARE: return "Customer Care";
		default: return "-";
		}
	}

	public static List<EnquiryType> list() {
		List<EnquiryType> lst = new ArrayList<>(5);
		lst.add(new EnquiryType(ONLINE, get(ONLINE)));
		lst.add(new EnquiryType(WALKIN, get(WALKIN)));
		lst.add(new EnquiryType(PHONE, get(PHONE)));
		lst.add(new EnquiryType(EVENT, get(EVENT)));
		lst.add(new EnquiryType(CUSTOMER_CARE, get(CUSTOMER_CARE)));
		return lst;
	}

	public String getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}

	public int getEnquiryTypeId() {
		return enquiryTypeId;
	}

	public void setEnquiryTypeId(int enquiryTypeId) {
		this.enquiryTypeId = enquiryTypeId;
	}

	private int enquiryTypeId;
	private String enquiryType;

	public EnquiryType(int enquiryTypeId, String enquiryType) {
		super();
		this.enquiryTypeId = enquiryTypeId;
		this.enquiryType = enquiryType ;
	}
}
