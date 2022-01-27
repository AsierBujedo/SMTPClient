package com.sender;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class clientSenderSMTP {
	public Session initSMTP(String from, String pass) {
		
		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(from, pass);

			}

		});
		session.setDebug(true);
		return session;
	}

	public void send(String from, String pass, String to, String text, String subject) {
		try {
			MimeMessage message = new MimeMessage(initSMTP(from, pass));
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text);
			System.out.println("SENDING...");
			Transport.send(message);
			System.out.println("MESSAGE SENT");
			JOptionPane.showMessageDialog(null, "Mensaje enviado", "Información", JOptionPane.INFORMATION_MESSAGE);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
