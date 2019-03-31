package com.ortakat.kudos.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ortakat.kudos.backend.api.firestore.BaseObject;
import com.ortakat.kudos.backend.api.firestore.FirebaseService;
import com.ortakat.kudos.backend.model.security.Authorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

	private final FirebaseService firebaseService;

	private String url = "https://slack.com/api/oauth.access";

	public static <T> HttpEntity<T> getHttpEntity(T content, MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		return new HttpEntity<>(content, headers);
	}

	@GetMapping("/signUp")
	public ResponseEntity signUp(@RequestParam("code") String code, @RequestParam("state") String hubId) {
		try {

			log.info("E1 ");

			if (Objects.isNull(hubId) || Objects.isNull(code)) {
				return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", "https://github.com/EemreYyalcin").body("Hub Not Found!!");
			}

			log.info("E2 ");
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>() {{
				add("client_id", "571141609170.571635956869");
				add("client_secret", "23444210dd2d2373fb6ef50cd47a04b9");
				add("code", code);
			}};

			Authorize authorize = new RestTemplate().postForObject(url, getHttpEntity(map, MediaType.APPLICATION_FORM_URLENCODED), Authorize.class);
			System.out.println(new ObjectMapper().writeValueAsString(authorize));

			if (Objects.isNull(authorize.getOk()) || !authorize.getOk().equals("true")) {
				System.out.println("Authenticate Fail 42");
				return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", "https://github.com/EemreYyalcin").body("Authenticate Fail 42");
			}
			log.info("E3 ");

			BaseObject hubs = BaseObject.getInstance("hubs").setKey(hubId).read(firebaseService);
			log.info("DDDDebug ", hubs);
			BaseObject integrations = hubs.getChildObject("integrations").setKey("slack").read(firebaseService);
			integrations.setInputs(new HashMap<String, Object>() {{
				put("access_token", authorize.getAccess_token());
				put("scope", authorize.getScope());
				put("user_id", authorize.getUser_id());
				put("team_name", authorize.getTeam_name());
				put("team_id", authorize.getTeam_id());
				put("bot_user_id", authorize.getBot().getBot_user_id());
				put("bot_access_token", authorize.getBot().getBot_access_token());
			}});
			integrations.save(firebaseService);
			log.info("E4 ");

			return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", "https://github.com/EemreYyalcin").body("Success");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
		}
	}


}
