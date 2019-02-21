package com.vroom.rig.slackbot.config;

import java.nio.charset.Charset;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate();
		template.getMessageConverters().add(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("ISO-8859-1")));
		return template;
	}
	
	@Bean
	public Random random() {
		return new Random();
	}
	
}
