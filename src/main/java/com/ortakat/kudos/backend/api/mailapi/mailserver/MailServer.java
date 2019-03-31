package com.ortakat.kudos.backend.api.mailapi.mailserver;


import com.ortakat.kudos.backend.api.mailapi.model.MailPost;

public interface MailServer {
	String sendMail(MailPost mailPost) throws Exception;
}
