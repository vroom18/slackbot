package com.vroom.rig.slackbot.model.fantasy;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BoxScore {
	
	private List<ScheduleItem> scheduleItems;
	private List<BoxTeam> teams;
	private int scoringPeriodId;
	private Map<String, ProGame> progames;
	
	public List<ScheduleItem> getScheduleItems() {
		return scheduleItems;
	}
	public void setScheduleItems(List<ScheduleItem> scheduleItems) {
		this.scheduleItems = scheduleItems;
	}
	public List<BoxTeam> getTeams() {
		return teams;
	}
	public void setTeams(List<BoxTeam> teams) {
		this.teams = teams;
	}
	public int getScoringPeriodId() {
		return scoringPeriodId;
	}
	public void setScoringPeriodId(int scoringPeriodId) {
		this.scoringPeriodId = scoringPeriodId;
	}
	public Map<String, ProGame> getProgames() {
		return progames;
	}
	public void setProgames(Map<String, ProGame> progames) {
		this.progames = progames;
	}
}
