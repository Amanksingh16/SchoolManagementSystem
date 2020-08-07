package kmsg.sms.common;

public class SupplierResponse {
	public static final int PENDING = 10 ;
	public static final int ACCEPTED = 20 ;
	public static final int QUERY = 30 ;
	public static final int REJECTED = 40 ;
	
	public static String get(int supplierResponseId ) {
		switch( supplierResponseId ) {
		case PENDING: return "Pending";
		case ACCEPTED: return "Accepted";
		case QUERY: return "Query";
		case REJECTED: return "Rejected";
		default: return "-";
		}
	}
}
