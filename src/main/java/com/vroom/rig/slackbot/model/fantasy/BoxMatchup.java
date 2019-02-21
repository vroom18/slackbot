package com.vroom.rig.slackbot.model.fantasy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BoxMatchup {

	private Team awayTeam;
	private Team homeTeam;
	private List<Float> awayTeamScores;
	private List<Float> homeTeamScores;
	private int matchupPeriodId;
	
	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public List<Float> getAwayTeamScores() {
		return awayTeamScores;
	}

	public void setAwayTeamScores(List<Float> awayTeamScores) {
		this.awayTeamScores = awayTeamScores;
	}

	public List<Float> getHomeTeamScores() {
		return homeTeamScores;
	}

	public void setHomeTeamScores(List<Float> homeTeamScores) {
		this.homeTeamScores = homeTeamScores;
	}

	public int getMatchupPeriodId() {
		return matchupPeriodId;
	}

	public void setMatchupPeriodId(int matchupPeriodId) {
		this.matchupPeriodId = matchupPeriodId;
	}
	
}
