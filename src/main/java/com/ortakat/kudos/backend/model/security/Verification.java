package com.ortakat.kudos.backend.model.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Verification {

	private String token;

	private String challenge;

	private String type;

	private String team_id;

	private String api_app_id;

	private Map<String, String> event;

	private Map<String, String> authed_users;

	private String event_id;

	private String event_time;

}
