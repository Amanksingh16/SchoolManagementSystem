package kmsg.sms.common;

public class BuildingRoomType {

	final static int CLASSROOM 		= 1;
	final static int LAB 			= 2;
	final static int LIBRARY 		= 3;
	final static int STAFFROOM  	= 4;
	final static int STORE  		= 5;
	final static int WASHROOMBOYS 	= 6;
	final static int WASHROOMGIRLS  = 7;
	final static int WASHROOMSTAFF  = 8;
	
	public static String getRoomType(int roomTypeId)
	{
		String type = "";
		switch(roomTypeId)
		{
			case CLASSROOM: type = "Classroom";
			break;
			
			case LAB: type = "Lab";
			break;
			
			case LIBRARY: type = "Library";
			break;
			
			case STAFFROOM: type = "Staffroom";
			break;
			
			case STORE: type = "Store";
			break;
			
			case WASHROOMBOYS: type = "Washroom Boys";
			break;
			
			case WASHROOMGIRLS: type = "Washroom Girls";
			break;
			
			case WASHROOMSTAFF: type = "Washroom Staff";
			break;
		}
		
		return type;
	}
}
