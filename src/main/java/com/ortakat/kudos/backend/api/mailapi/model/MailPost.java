package com.ortakat.kudos.backend.api.mailapi.model;


import com.ortakat.kudos.backend.api.mailapi.enums.MailServerEnum;

public class MailPost {

	private String fromMail;

	private String toMail;

	private String subject;

	private String content;

	private MailServerEnum mailServer;

	public String getFromMail() {
		return fromMail;
	}

	public MailPost setFromMail(String fromMail) {
		this.fromMail = fromMail;
		return this;
	}

	public String getToMail() {
		return toMail;
	}

	public MailPost setToMail(String toMail) {
		this.toMail = toMail;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public MailPost setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getContent() {
		return content;
	}

	public MailPost setContent(String content) {
		this.content = content;
		return this;
	}

	public MailServerEnum getMailServer() {
		return mailServer;
	}

	public MailPost setMailServer(MailServerEnum mailServer) {
		this.mailServer = mailServer;
		return this;
	}
}
