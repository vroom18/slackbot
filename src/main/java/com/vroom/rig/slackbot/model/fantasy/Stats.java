package com.vroom.rig.slackbot.model.fantasy;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Stats {

	private float appliedStatTotal;
	private Map<String, Float> appliedStats;
	private Map<String, Float> rawStats;
	
	public float getAppliedStatTotal() {
		return appliedStatTotal;
	}
	public void setAppliedStatTotal(float appliedStatTotal) {
		this.appliedStatTotal = appliedStatTotal;
	}
	public Map<String, Float> getAppliedStats() {
		return appliedStats;
	}
	public void setAppliedStats(Map<String, Float> appliedStats) {
		this.appliedStats = appliedStats;
	}
	public Map<String, Float> getRawStats() {
		return rawStats;
	}
	public void setRawStats(Map<String, Float> rawStats) {
		this.rawStats = rawStats;
	}
	
	
}
