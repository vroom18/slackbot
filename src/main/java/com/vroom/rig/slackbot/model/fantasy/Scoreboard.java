package com.vroom.rig.slackbot.model.fantasy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Scoreboard {

	private List<ScoreboardMatchup> matchups;
	private int scoringPeriodId;
	
	public List<ScoreboardMatchup> getMatchups() {
		return matchups;
	}
	public void setMatchups(List<ScoreboardMatchup> matchups) {
		this.matchups = matchups;
	}
	public int getScoringPeriodId() {
		return scoringPeriodId;
	}
	public void setScoringPeriodId(int scoringPeriodId) {
		this.scoringPeriodId = scoringPeriodId;
	}

}
