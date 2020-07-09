package kmsg.sms.common;

public class TaskTeacherStatus 
{
	public final static int ASSIGNED 		= 10;
	public final static int DELAYED 		= 20;
	public final static int COMPLETED     	= 30;
	
	public static String getTaskStatus(int statusId)
	{
		String type = "";
		switch(statusId)
		{
			case ASSIGNED: type = "ASSIGNED";	
			break;
			
			case DELAYED: type = "DELAYED";	
			break;
			
			case COMPLETED: type = "COMPLETED";	
			break;
		}
		return type;
	}
}
