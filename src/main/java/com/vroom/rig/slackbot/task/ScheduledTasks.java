package com.vroom.rig.slackbot.task;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vroom.rig.slackbot.message.MessageService;
import com.vroom.rig.slackbot.service.SlackService;
import com.vroom.rig.slackbot.service.TwitterService;

@Component
public class ScheduledTasks {

	@Autowired
	private TwitterService twitter;
	
	@Autowired
	private SlackService slack;
	
	@Autowired
	private MessageService messages;
	
	private Logger log = Logger.getLogger(ScheduledTasks.class);
	
	@Scheduled(fixedDelay = 20000)
	public void checkForTweets() {
		twitter.refreshTweets();
		slack.postRecentTweets();
	}
	
	@Scheduled(fixedDelay = 6000)
	public void postAlerts() throws ParseException, JsonParseException, JsonMappingException, IOException {
		log.info("getting messages...");
		messages.sendStatusMessages();
		messages.sendContentMessages();
	}
	
}
