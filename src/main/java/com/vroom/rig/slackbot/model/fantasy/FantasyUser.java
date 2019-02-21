package com.vroom.rig.slackbot.model.fantasy;

import java.util.List;

public class FantasyUser {

	private String teamId;
	private String slackId;
	private List<String> aliases;
	
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getSlackId() {
		return slackId;
	}
	public void setSlackId(String slackId) {
		this.slackId = slackId;
	}
	public List<String> getAliases() {
		return aliases;
	}
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}
	
}
