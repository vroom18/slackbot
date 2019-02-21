package com.vroom.rig.slackbot.model;

import java.util.Date;

public class TweetStats {
	
	private Date time;
	private int retweets;
	private int favorites;
	private int userFollowers;
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getRetweets() {
		return retweets;
	}
	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}
	public int getFavorites() {
		return favorites;
	}
	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}
	public int getUserFollowers() {
		return userFollowers;
	}
	public void setUserFollowers(int userFollowers) {
		this.userFollowers = userFollowers;
	}
}

