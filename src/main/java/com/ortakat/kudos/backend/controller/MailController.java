package com.ortakat.kudos.backend.controller;

import com.ortakat.kudos.backend.api.firestore.FirebaseDbService;
import com.ortakat.kudos.backend.api.mailapi.MailService;
import com.ortakat.kudos.backend.api.mailapi.model.MailPost;
import com.ortakat.kudos.backend.service.TimerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/send")
@RequiredArgsConstructor
@Slf4j
public class MailController {

	private final MailService mailService;
	private final FirebaseDbService firebaseDbService;
	private final TimerService timerService;

	@Value("eemreyyalcin1@gmail.com")
	private String smtpMail;

	@Value("smtp.password")
	private String password;

	@Value("grid.api_key")
	private String gridApiKey;


	//For Test
	@PostMapping("/mail")
	public ResponseEntity sendMail(@RequestBody MailPost mailPost) {
		try {
			String body = mailService.sendMail(mailPost);
			if (body.contains("Fail")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
			}
			return ResponseEntity.ok(body);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
		}
	}

	@PostMapping("/code")
	public ResponseEntity sendCodeToMail(@RequestBody String email) throws Exception {
		String code = firebaseDbService.emailTempCode(email);
		timerService.setupTimer(3, () -> {
			try {
				firebaseDbService.timeoutEmailCode(code);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		});
		return null;
	}


}
