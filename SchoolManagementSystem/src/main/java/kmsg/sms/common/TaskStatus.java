package kmsg.sms.common;

public class TaskStatus 
{
	public final static int PENDING 		= 10;
	public final static int COMPLETED     	= 20;
	
	public static String getTaskStatus(int statusId)
	{
		String type = "";
		switch(statusId)
		{
			case PENDING: type = "PENDING";	
			break;
			
			case COMPLETED: type = "COMPLETED";	
			break;
		}
		return type;
	}
}
