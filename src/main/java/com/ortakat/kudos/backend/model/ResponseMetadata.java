package com.ortakat.kudos.backend.model;

import lombok.Data;

@Data
public class ResponseMetadata {

	private String[] warnings;

	private String next_cursor;
}
