package com.vroom.rig.slackbot.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.social.twitter.api.Tweet;

import com.vroom.rig.slackbot.config.TwitterConfig;

public class SlackMessage {

	private String text;
	
	public SlackMessage(String text) {
		setText(text);
	}

	public SlackMessage(TweetDetails tweet) {
		setText(String.format(TwitterConfig.twitUrlFormat, tweet.getUsername(), tweet.getTweetId()));
	}
	
	public SlackMessage(List<TweetDetails> tweets) {
		setText(tweets.stream()
				.map(t -> String.format(TwitterConfig.twitUrlFormat, t.getUsername(),t.getTweetId()))
				.collect(Collectors.joining(" ")));
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
