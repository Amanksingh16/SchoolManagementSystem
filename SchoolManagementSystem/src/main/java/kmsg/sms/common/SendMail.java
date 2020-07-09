package kmsg.sms.common;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class SendMail 
{
	public static boolean sendEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message)
            throws AddressException, MessagingException 
    {
       // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
        
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() 
        {
            public PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);      

       // creates a new e-mail message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));
        msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        //msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccAddress));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html; charset=utf-8");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        msg.setContent(multipart);
      
        // sends the e-mail
        try
        {
        	Transport.send(msg);
        	return true;
        }
        catch(Exception ex)
        {
        	System.out.println("Exception raised in sending mail from :" + userName + ":" + ex);
        	ex.printStackTrace();
        	return false;
        }
	}

}