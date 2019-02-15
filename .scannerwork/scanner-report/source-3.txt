package org.rupesh.demo.extentReports;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailTransmitter {

	String[] to = { "rupesharyan250@gmail.com" };
	static String from = "rupeshkumar2525@gmail.com";
	static String bcc = "rupesharyan253@gmail.com";
	static String host = "smtp.gmail.com";
	static String port = "587";
	static String userName = "rupeshkumar2525@gmail.com";
	static String password = "Rupi@0253";
	static String extentReportPath = System.getProperty("user.dir") + "\\extent.html";

	public void startTransmission() throws AddressException, MessagingException, IOException {
		try {
			// SetMailServerProperties
			Properties properties = System.getProperties();
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", true);
			properties.put("mail.smtp.starttls.enable", true);
			properties.put("mail.smtp.ssl.trust", host);

			// CreateEmailMessage
			Session session = Session.getDefaultInstance(properties);
			MimeMessage mail = new MimeMessage(session);
			mail.setFrom(new InternetAddress(from));
			for (int i = 0; i < to.length; i++) {
				mail.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
			}
			mail.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));

			String emailSubject = "Java Email";
			String emailBody = "This is an email sent by JavaMail api.";
			mail.setSubject(emailSubject);
			
			Multipart multipart = new MimeMultipart();
			
			BodyPart mailBodyPart = new MimeBodyPart();
			mailBodyPart.setText("PFA for extent report.");
			multipart.addBodyPart(mailBodyPart);

			MimeBodyPart extentReportAttachment = new MimeBodyPart();
			DataSource source = new FileDataSource(extentReportPath);
			extentReportAttachment.setDataHandler(new DataHandler(source));
			extentReportAttachment.setFileName("Extent Reports.html");
			multipart.addBodyPart(extentReportAttachment);
			mail.setContent(multipart);
		

			// SendEmail
			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, password);
			transport.sendMessage(mail, mail.getAllRecipients());
			transport.close();
			System.out.println("Email sent successfully.");
		} catch (Exception ex) {
			System.out.println("It seems there was a problem in Email transmission with the error message \n"
					+ ex.getMessage() + "\n and stacktrace \n" + ex.getStackTrace());
		}
	}

}
