package com.vroom.rig.slackbot.model.fantasy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Team {

	private String teamLocation;
	private String teamNickname;
	private String teamAbbrev;
	private int teamId;
	
	
	public String getTeamLocation() {
		return teamLocation;
	}

	public void setTeamLocation(String teamLocation) {
		this.teamLocation = teamLocation;
	}

	public String getTeamNickname() {
		return teamNickname;
	}

	public void setTeamNickname(String teamNickname) {
		this.teamNickname = teamNickname;
	}

	public String getTeamAbbrev() {
		return teamAbbrev;
	}

	public void setTeamAbbrev(String teamAbbrev) {
		this.teamAbbrev = teamAbbrev;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Override
	public String toString() {
		return teamLocation + " " + teamNickname;
	}
	
}
