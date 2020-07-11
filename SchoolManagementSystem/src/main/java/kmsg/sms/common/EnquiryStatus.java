package kmsg.sms.common;

import java.util.ArrayList;
import java.util.List;

public class EnquiryStatus {
	public static final int NEW = 10 ;
	public static final int ASSIGNED = 20 ;
	public static final int RE_ASSIGNED = 30 ;
	public static final int RESPONDED = 40 ;
	public static final int CLOSED  = 50 ;
	public static final int REGISTERED = 60 ;
	
	public static String get(int enquiryStatusId ) {
		switch( enquiryStatusId ) {
		case NEW: return "New";
		case ASSIGNED: return "Assigned";
		case RE_ASSIGNED: return "Re-Assigned";
		case RESPONDED: return "Responded";
		case CLOSED: return "Closed";
		case REGISTERED: return "Registered";
		default: return "-";
		}
	}

	public static List<EnquiryStatus> list() {
		List<EnquiryStatus> lst = new ArrayList<>(6);
		lst.add(new EnquiryStatus(NEW, get(NEW)));
		lst.add(new EnquiryStatus(ASSIGNED, get(ASSIGNED)));
		lst.add(new EnquiryStatus(RE_ASSIGNED, get(RE_ASSIGNED)));
		lst.add(new EnquiryStatus(RESPONDED, get(RESPONDED)));
		lst.add(new EnquiryStatus(CLOSED, get(CLOSED)));
		lst.add(new EnquiryStatus(REGISTERED, get(REGISTERED)));
		return lst;
	}

	public String getEnquiryStatus() {
		return enquiryStatus;
	}

	public void setEnquiryStatus(String enquiryStatus) {
		this.enquiryStatus = enquiryStatus;
	}

	public int getEnquiryStatusId() {
		return enquiryStatusId;
	}

	public void setEnquiryStatusId(int enquiryStatusId) {
		this.enquiryStatusId = enquiryStatusId;
	}

	private int enquiryStatusId;
	private String enquiryStatus;

	public EnquiryStatus(int enquiryStatusId, String enquiryStatus) {
		super();
		this.enquiryStatusId = enquiryStatusId;
		this.enquiryStatus = enquiryStatus ;
	}
}
