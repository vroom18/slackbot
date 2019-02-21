package com.vroom.rig.slackbot.model.fantasy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BoxPlayer {

	private Stats currentPeriodRealStats;
	private Stats currentPeriodProjectedStats;
	private int opponentProTeamId;
	private List<String> proGameIds;
	private int lockStatus;
	private Player player;
	
	public Stats getCurrentPeriodRealStats() {
		return currentPeriodRealStats;
	}
	public List<String> getProGameIds() {
		return proGameIds;
	}
	public void setProGameIds(List<String> proGameIds) {
		this.proGameIds = proGameIds;
	}
	public void setCurrentPeriodRealStats(Stats currentPeriodRealStats) {
		this.currentPeriodRealStats = currentPeriodRealStats;
	}
	public Stats getCurrentPeriodProjectedStats() {
		return currentPeriodProjectedStats;
	}
	public void setCurrentPeriodProjectedStats(Stats currentPeriodProjectedStats) {
		this.currentPeriodProjectedStats = currentPeriodProjectedStats;
	}
	public int getOpponentProTeamId() {
		return opponentProTeamId;
	}
	public void setOpponentProTeamId(int opponentProTeamId) {
		this.opponentProTeamId = opponentProTeamId;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(int lockStatus) {
		this.lockStatus = lockStatus;
	}
	
	public float getCurrentScore() {
		return currentPeriodRealStats.getAppliedStatTotal();
	}
	
}
