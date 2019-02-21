package com.vroom.rig.slackbot.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.social.twitter.api.Tweet;

public class TweetDetails {
	
	@Id
	private String tweetId;
	private String username;
	private Date createdAt;
	private boolean hasBeenSlacked;
	private String opId;
	boolean isReply;
	
	List<TweetStats> stats;
	
	public TweetDetails() {
		setHasBeenSlacked(false);
		setStats(new ArrayList<TweetStats>());
	}
	
	public TweetDetails(Tweet tweet) {
		this();
		setTweetId(tweet.getIdStr());
		setUsername(tweet.getFromUser());
		setCreatedAt(tweet.getCreatedAt());
		setOpId(tweet.getInReplyToStatusId() != null 
				? tweet.getInReplyToStatusId().toString() : null);
		setIsReply(this.opId != null);
	}
	
	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean getHasBeenSlacked() {
		return hasBeenSlacked;
	}

	public void setHasBeenSlacked(boolean hasBeenSlacked) {
		this.hasBeenSlacked = hasBeenSlacked;
	}

	public List<TweetStats> getStats() {
		return stats;
	}

	public void setStats(List<TweetStats> stats) {
		this.stats = stats;
	}
	
	public void addStats(TweetStats tweetStats) {
		stats.add(tweetStats);
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public boolean isReply() {
		return isReply;
	}

	public void setIsReply(boolean isReply) {
		this.isReply = isReply;
	}
}
