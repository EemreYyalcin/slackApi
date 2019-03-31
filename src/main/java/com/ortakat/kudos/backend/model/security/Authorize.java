package com.ortakat.kudos.backend.model.security;

import com.ortakat.kudos.backend.model.Bot;
import com.ortakat.kudos.backend.model.ResponseMetadata;
import lombok.Data;

@Data
public class Authorize {

	private String ok;

	private String access_token;

	private String scope;

	private String user_id;

	private String team_name;

	private String team_id;

	private Bot bot;

	private String warning;

	private ResponseMetadata response_metadata;


}
