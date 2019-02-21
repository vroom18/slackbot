package com.vroom.rig.slackbot.model.fantasy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ScoreboardMatchup {
	
	private String winner;
	private List<ScoreboardTeam> teams;
	
	private boolean bye;
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public List<ScoreboardTeam> getTeams() {
		return teams;
	}
	public void setTeams(List<ScoreboardTeam> teams) {
		this.teams = teams;
	}
	public boolean isBye() {
		return bye;
	}
	public void setBye(boolean bye) {
		this.bye = bye;
	}
	
}
