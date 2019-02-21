package com.vroom.rig.slackbot.model.fantasy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BoxTeam {
	
	private float appliedActiveRealTotal;
	private float appliedActiveProjectedTotal;
	private float appliedInactiveRealTotal;
	private float appliedInctiveProjectedTotal;
	private List<BoxPlayer> slots;
	private Team team;
	
	public float getAppliedActiveRealTotal() {
		return appliedActiveRealTotal;
	}
	public void setAppliedActiveRealTotal(float appliedActiveRealTotal) {
		this.appliedActiveRealTotal = appliedActiveRealTotal;
	}
	public float getAppliedActiveProjectedTotal() {
		return appliedActiveProjectedTotal;
	}
	public void setAppliedActiveProjectedTotal(float appliedActiveProjectedTotal) {
		this.appliedActiveProjectedTotal = appliedActiveProjectedTotal;
	}
	public float getAppliedInactiveRealTotal() {
		return appliedInactiveRealTotal;
	}
	public void setAppliedInactiveRealTotal(float appliedInactiveRealTotal) {
		this.appliedInactiveRealTotal = appliedInactiveRealTotal;
	}
	public float getAppliedInctiveProjectedTotal() {
		return appliedInctiveProjectedTotal;
	}
	public void setAppliedInctiveProjectedTotal(float appliedInctiveProjectedTotal) {
		this.appliedInctiveProjectedTotal = appliedInctiveProjectedTotal;
	}
	public List<BoxPlayer> getSlots() {
		return slots;
	}
	public void setSlots(List<BoxPlayer> slots) {
		this.slots = slots;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
}
