package app.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	public static int sendMail(String To,String Subject, String Body){
		
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		  // Get a Properties object
		     Properties props = System.getProperties();
		     props.setProperty("mail.smtp.host", "10.129.1.1");
		     props.put("mail.smtp.starttls.enable","false");
		     props.put("mail.smtp.ssl.trust", "10.129.1.1");
		     props.setProperty("mail.smtp.port", "25");
		     props.setProperty("mail.smtp.connectiontimeout", "30000");
		     props.setProperty("mail.smtp.timeout", "30000");
		     props.setProperty("mail.smtp.auth", "true");
		     props.setProperty("mail.debug", "true");
		     props.setProperty("mail.debug.auth", "true");
		     props.setProperty("mail.store.protocol", "pop3");
		     props.setProperty("mail.transport.protocol", "smtp");
		     final String username = "username";
		     final String password = "password";
		     try{
		    	 
		     Session session = Session.getDefaultInstance(props, 
		                          new Authenticator(){
		                             protected PasswordAuthentication getPasswordAuthentication() {
		                                return new PasswordAuthentication(username, password);
		                             }});

		   // -- Create a new message --
		     Message msg = new MimeMessage(session);

		  // -- Set the FROM and TO fields --
		     msg.setFrom(new InternetAddress("lokacart@cse.iitb.ac.in"));
		     msg.setRecipients(Message.RecipientType.TO, 
		                      InternetAddress.parse(To,false));
		     msg.setSubject(Subject);
		     msg.setText(Body);
		     msg.setSentDate(new Date());

		     Transport.send(msg);
		     System.out.println("Message sent.");
		     return 1;
		  }catch (MessagingException e){ System.out.println("Error, cause: " + e); return 0;}
		
	}
	
}
