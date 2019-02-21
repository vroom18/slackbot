package com.vroom.rig.slackbot.model.fantasy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ScheduleItem {

	private List<BoxMatchup> matchups;

	public List<BoxMatchup> getMatchups() {
		return matchups;
	}

	public void setMatchups(List<BoxMatchup> matchups) {
		this.matchups = matchups;
	}
	
}
