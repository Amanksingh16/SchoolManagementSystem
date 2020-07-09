package kmsg.sms.common;

public class EmailType {

	public final static int SCHOOL_VERIFICATION 		= 10;
	public final static int TEACHER_VERIFICATION     	= 20;
	public final static int STUDENT_VERIFICATION 		= 30;
	public final static int SCHOOL_FORGOTPASSWORD     	= 40;
	public final static int TEACHER_FORGOTPASSWORD 		= 50;
	public final static int STUDENT_FORGOTPASSWORD     	= 60;
	public final static int SUPPLIER_FORGOTPASSWORD 	= 70;
	
	public static String getEmailBody(int statusId)
	{
		String type = "";
		switch(statusId)
		{
			case SCHOOL_VERIFICATION: type = "SCHOOL VERIFICATION";	
			break;
			
			case SCHOOL_FORGOTPASSWORD: type = "SCHOOL FORGOTPASSWORD";	
			break;
			
			case TEACHER_VERIFICATION: type = "TEACHER VERIFICATION";	
			break;
			
			case TEACHER_FORGOTPASSWORD: type = "TEACHER FORGOTPASSWORD";	
			break;
			
			case STUDENT_VERIFICATION: type = "STUDENT VERIFICATION";	
			break;
			
			case STUDENT_FORGOTPASSWORD: type = "STUDENT FORGOTPASSWORD";	
			break;
			
			case SUPPLIER_FORGOTPASSWORD: type = "SUPPLIER FORGOTPASSWORD";	
			break;
		}
		return type;
	}
}
