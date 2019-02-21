package com.vroom.rig.slackbot.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.vroom.rig.slackbot.config.SlackConfig;
import com.vroom.rig.slackbot.model.SlackMessage;
import com.vroom.rig.slackbot.model.TweetDetails;
import com.vroom.rig.slackbot.model.UserDetails;
import com.vroom.rig.slackbot.repository.TweetDetailsRepository;
import com.vroom.rig.slackbot.repository.UserDetailsRepository;

@Component
public class SlackService {

	@Autowired
	private RestTemplate template;
	
	@Autowired
	private TweetDetailsRepository tweetRepo;
	
	@Autowired
	private UserDetailsRepository userRepo;
	
	private static final int MINUTE_OFFSET=5;
	
	public void postMessage(String msg) {
		template.postForEntity(SlackConfig.WEBHOOK_URL, new SlackMessage(msg), String.class);
	}
	
	public void postTweet(TweetDetails tweet) {
		if(!tweet.getHasBeenSlacked()) {
			SlackMessage msg = new SlackMessage(tweet);
			template.postForEntity(SlackConfig.WEBHOOK_URL, msg, String.class);
		}
	}
	
	public void postRecentTweets() {
		List<TweetDetails> tweets = tweetRepo.findByHasBeenSlackedAndCreatedAtAfter(false, 
				Date.from(LocalDateTime.now()
						.minusMinutes(MINUTE_OFFSET)
						.atZone(ZoneId.systemDefault())
						.toInstant()));	
		List<String> replyFilterUsers = userRepo.findByFilterReplies(true).stream().map(UserDetails::getName).collect(Collectors.toList());
	
		List<List<TweetDetails>> sublists = Lists.partition(tweets.stream()
					.filter(t -> !(replyFilterUsers.contains(t.getUsername()) && t.isReply()))
					.collect(Collectors.toList())
				,SlackConfig.MAX_EXPANDABLE_LINKS);
		for(List<TweetDetails> sublist : sublists) {
			SlackMessage msg = new SlackMessage(sublist);
			ResponseEntity<String> response = template.postForEntity(SlackConfig.WEBHOOK_URL, msg, String.class);
			if(response.getStatusCode().equals(HttpStatus.OK)) {
				sublist.stream().forEach(t -> {
					t.setHasBeenSlacked(true);
					tweetRepo.save(t);
				});
			}
		}
	}
}
