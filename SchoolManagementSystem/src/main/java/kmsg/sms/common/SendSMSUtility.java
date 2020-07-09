package kmsg.sms.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMSUtility 
{
	public static boolean sendSms(String contact,String OTP) {
		try {
			// Construct data
			String lastAccessDate = Constants.getOTPAccessDate();
			String apiKey = "apikey=" + "DVNPUE5MW4M-juMEgb5fiNgXu1nuUXRs3rALhpCcfQ";
			String message = "&message=" +OTP+" is the OTP for login to AutoOnline CRM. It is valid upto "+lastAccessDate+". DO NOT disclose your OTP to anyone.";
			String sender = "&sender=" + "KMSGTE";
			String numbers = "&numbers=" + "91"+contact;
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			
			rd.close();
			
			return true;
		} 
		catch (Exception e) 
		{
			System.out.println("Error SMS "+e);
			return false;
		}
	}	
}
