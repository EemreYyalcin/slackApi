package com.ortakat.kudos.backend.model.security;

import lombok.Data;

@Data
public class Member {

	private String id;

	private String team_id;

	private String name;

	private String deleted;

	private String color;

	private String real_name;

	private String tz;

	private String tz_label;

	private String tz_offset;

	private Boolean is_admin;

	private Boolean is_owner;

	private Boolean is_primary_owner;

	private Boolean is_restricted;

	private Boolean is_ultra_restricted;

	private Boolean is_bot;

	private String updated;

	private Boolean is_app_user;

	private Boolean has_2fa;

	private Profile profile;

}
