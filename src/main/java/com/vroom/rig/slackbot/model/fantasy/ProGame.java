package com.vroom.rig.slackbot.model.fantasy;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProGame {

	private long gameId;
	private int awayProTeamId;
	private int homeProTeamId;
	private int homeScore;
	private int awayScore;
	private int period;
	private Date gameDate;
	private String timeRemainingInPeriod;
	
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public int getAwayProTeamId() {
		return awayProTeamId;
	}
	public void setAwayProTeamId(int awayProTeamId) {
		this.awayProTeamId = awayProTeamId;
	}
	public int getHomeProTeamId() {
		return homeProTeamId;
	}
	public void setHomeProTeamId(int homeProTeamId) {
		this.homeProTeamId = homeProTeamId;
	}
	public int getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}
	public int getAwayScore() {
		return awayScore;
	}
	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}
	public Date getGameDate() {
		return gameDate;
	}
	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}
	public String getTimeRemainingInPeriod() {
		return timeRemainingInPeriod;
	}
	public void setTimeRemainingInPeriod(String timeRemainingInPeriod) {
		this.timeRemainingInPeriod = timeRemainingInPeriod;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
}
