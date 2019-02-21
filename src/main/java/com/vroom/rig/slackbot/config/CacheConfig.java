package com.vroom.rig.slackbot.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
public class CacheConfig {
	
	@Bean
	public CacheManager cacheManager() {
	    GuavaCacheManager cacheManager = new GuavaCacheManager();
	    cacheManager.setCacheBuilder(
	        CacheBuilder.
	        	newBuilder().
	        	expireAfterWrite(30, TimeUnit.SECONDS));
	    return cacheManager;
	}
}
