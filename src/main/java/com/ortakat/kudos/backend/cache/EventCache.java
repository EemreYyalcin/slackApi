package com.ortakat.kudos.backend.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class EventCache {

	@Bean
	LoadingCache<String, Map<String, String>> getMapCache() {
		CacheLoader<String, Map<String, String>> loader = new CacheLoader<String, Map<String, String>>() {
			@Override
			public Map<String, String> load(String s) throws Exception {
				return new HashMap<>();
			}
		};
		return CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(1, TimeUnit.HOURS).build(loader);
	}


}
