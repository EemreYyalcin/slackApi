package com.ortakat.kudos.backend.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Event {

	private String client_msg_id;
	private String type;
	private String text;
	private String user;
	private String ts;
	private String channel;
	private String channel_type;
	private String event_ts;

}
