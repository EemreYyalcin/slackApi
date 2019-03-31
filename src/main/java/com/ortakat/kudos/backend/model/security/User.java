package com.ortakat.kudos.backend.model.security;

import com.ortakat.kudos.backend.model.ResponseMetadata;
import lombok.Data;

@Data
public class User {

	private String ok;

	private Member[] members;

	private String cache_ts;

	private ResponseMetadata response_metadata;

}
