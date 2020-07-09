package kmsg.sms.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Constants
{
	public static String MESSAGE = "SvcMsg";
	public static String STATUS  = "SvcStatus";
	public static String SUCCESS = "Success";
	public static String FAILURE = "Failure";
	public static String MAIL_VERIFY_LINK = "#";
	public final static int PENDING = 10;
	public final static int SENT = 20;
	public final static int CLICKED = 30;
	public static Map<String,String> phoneOTP = new HashMap<>();
	public static Map<Integer,String> forgotPassword = new HashMap<>();
	public static DataSource dataSource = new DriverManagerDataSource();
	private static String saveFilePath;
	
	//public static final BuyerType[] = [{"buyerTypeId":1,"Buyer"},{"buyerTypeId"=2,"buyerType":"Merchant"},{"buyerTypeId":3,"buyerType":"Factory"}]
	public static String MSG_OOPS = "Oops! Something bad happened. Contact system administrator";
	
    public static final String getOTPAccessDate() 
	{
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM hh:mm a");
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY,3); // adds one hour
	    Date date = cal.getTime(); 
	    return format.format(date);
	}

	public static String getSaveFilePath() {
		return saveFilePath;
	}

	public static void setSaveFilePath(String saveFilePath) {
		Constants.saveFilePath = saveFilePath;
	}
    
    
	
}
