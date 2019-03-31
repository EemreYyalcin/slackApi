package com.ortakat.kudos.backend.api.mailapi;

import com.ortakat.kudos.backend.api.mailapi.enums.MailServerEnum;
import com.ortakat.kudos.backend.api.mailapi.mailserver.Grid;
import com.ortakat.kudos.backend.api.mailapi.mailserver.SMTP;
import com.ortakat.kudos.backend.api.mailapi.model.MailPost;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	public String sendMail(MailPost mailPost) throws Exception {
		String body;
		if (mailPost.getMailServer() == MailServerEnum.SMTP) {
			body = new SMTP().sendMail(mailPost);
		} else if (mailPost.getMailServer() == MailServerEnum.GRID) {
			body = new Grid().sendMail(mailPost);
		} else {
			return "Unexpected Mail Server";
		}
		return body;
	}

}
