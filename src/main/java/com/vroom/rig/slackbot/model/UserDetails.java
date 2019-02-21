package com.vroom.rig.slackbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.social.twitter.api.TwitterProfile;

public class UserDetails {
	
	@Id
	private long userId;
	private String name;
	private boolean filterReplies;
	
	public UserDetails() {
		filterReplies = true;
	}
	
	public UserDetails(long userId, String name, boolean filterReplies) {
		super();
		setUserId(userId);
		setName(name);
		setFilterReplies(filterReplies);
	}
	
	public UserDetails(long userId, String name) {
		super();
		setUserId(userId);
		setName(name);
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean getFilterReplies() {
		return filterReplies;
	}

	public void setFilterReplies(boolean filterReplies) {
		this.filterReplies = filterReplies;
	}
}
