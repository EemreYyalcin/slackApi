package com.ortakat.kudos.backend.api.mailapi.mailserver;

import com.ortakat.kudos.backend.api.mailapi.model.MailPost;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class SMTP implements MailServer {

	private static Properties properties;

	public String sendMail(MailPost mailPost) throws Exception {
		String from = System.getenv("SOURCE_EMAIL");
		String password = System.getenv("EMAIL_PASSWORD");

		if (Objects.isNull(from) || Objects.isNull(password)) {
			throw new Exception("Initialize Source email or password to Lambda Function");
		}

		Session session = null;
		if (properties == null) {
			properties = new Properties();
			properties.putAll(new HashMap<String, Object>() {{
				put("mail.smtp.auth", true);
				put("mail.smtp.starttls.enable", "true");
				put("mail.smtp.host", "smtp.gmail.com");
				put("mail.smtp.port", "587");
				put("mail.smtp.ssl.trust", "smtp.gmail.com");
			}});

			session = Session.getDefaultInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
			});
		}
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailPost.getToMail()));
			message.setSubject(mailPost.getSubject());
			message.setText(mailPost.getContent());
			Transport.send(message);
		} catch (MessagingException mex) {
			throw new Exception("Message Not Send " + mex.getLocalizedMessage());
		}
		return "Success Send";
	}


}
