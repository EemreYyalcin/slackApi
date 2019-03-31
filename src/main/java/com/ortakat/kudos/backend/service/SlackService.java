package com.ortakat.kudos.backend.service;

import com.ortakat.kudos.backend.model.security.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SlackService {


	public User getUsers(String accessToken) {

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> content = new LinkedMultiValueMap<>();
		content.add("token", accessToken);
		return restTemplate.postForObject("https://slack.com/api/users.list", getHttpEntity(content, MediaType.APPLICATION_FORM_URLENCODED), User.class);

	}

	public Map<String, Object> getUserProfile(String accessToken, String userId) {

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("https://slack.com/api/users.profile.get?token=" + accessToken + "&user=" + userId, Map.class);

	}

	private <T> HttpEntity<T> getHttpEntity(T content, MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		return new HttpEntity<>(content, headers);
	}

//	public static void main(String[] args) throws JsonProcessingException {
//		User user = getUsers("xoxp-571141609170-571021135907-572491892311-5ee957cf9f3cec3b442f8db324e10b48");
//		System.out.println(new ObjectMapper().writeValueAsString(user));
//
//		System.out.println(new ObjectMapper().writeValueAsString(getUserProfile("xoxp-571141609170-571021135907-572491892311-5ee957cf9f3cec3b442f8db324e10b48", "UGT0M3ZSP")));
//
//
//	}

}
