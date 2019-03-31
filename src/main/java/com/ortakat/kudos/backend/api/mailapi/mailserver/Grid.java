package com.ortakat.kudos.backend.api.mailapi.mailserver;

import com.ortakat.kudos.backend.api.mailapi.model.MailPost;
import com.sendgrid.*;

import java.util.Objects;

public class Grid implements MailServer {

	@Override
	public String sendMail(MailPost mailPost) throws Exception {
		Mail mail = new Mail(new Email(mailPost.getFromMail()), mailPost.getSubject(), new Email(mailPost.getToMail()), new Content("text/plain", mailPost.getContent()));
		String apiKey = System.getenv("API_KEY");
		if (Objects.isNull(apiKey)) {
			throw new Exception("Initialize GRID Api Key to Lambda Function");
		}
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			return new SendGrid(apiKey).api(request).getBody();
		} catch (Exception ex) {
			throw ex;
		}
	}
}
