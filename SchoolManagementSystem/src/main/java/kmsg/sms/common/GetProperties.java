package kmsg.sms.common;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetProperties 
{
	Map<String, String>  result =new HashMap<>();
	Properties prop;
	
	public Properties getPropValues() 
	{
 
		prop = new Properties();
		try 
		{
			String propFileName = "mail.properties";
			prop.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(propFileName),"UTF8"));
	    } 
		catch (Exception e) 
		{
			System.out.println("Exception: " + e);
		} 
		return prop;
	}
}