package com.dxc.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
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

/**
 * This class is used for email reporting
 * 
 * @author Vidyasagar Mada
 *
 */
public class MailUtil {

	/**
	 * This method will send an email to specified people with an attachment
	 * @param to
	 * @param cc
	 * @param attachmentSource
	 * @throws AddressException 
	 * @throws MessagingException
	 * @author Vidyasagar Mada
	 */
	public void sendEmail(String to, String cc, String attachmentSource) throws AddressException, MessagingException  {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");
		final String from = "socialmodule@gmail.com";
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "@lt12345");
			}
		});
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
		
		message.setSubject("Automation_Execution_Report " + CustomReporter.getDateFormat(CustomReporter.dateType4));
		BodyPart messageBodyPart = new MimeBodyPart();
	
		messageBodyPart.setText(props.getProperty("mail.body"));

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();

		String filename = attachmentSource;
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(
				"Automation_Execution_Report_" + CustomReporter.getDateFormat(CustomReporter.dateType4) + ".zip");
		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);

		Transport.send(message);
		System.out.println("Mail Sent to " + to + "," + cc + " successfully");
	}
}