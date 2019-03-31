package com.ortakat.kudos.backend.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventRequest {

	private String token;
	private String team_id;
	private String api_app_id;
	private Event event;
	private String type;
	private String event_id;
	private String event_time;
	private String[] authed_users;
	private String challenge;
}
