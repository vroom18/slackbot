package com.vroom.rig.slackbot.model.fantasy;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Player {

	private String firstName;
	private String lastName;
	private float percentOwned;
	private String jersey;
	private int playerId;
	private float percentStarted;
	private int positionRank;
	private boolean isActive;
	private int defaultPositionId;
	private int healthStatus;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public float getPercentOwned() {
		return percentOwned;
	}
	public void setPercentOwned(float percentOwned) {
		this.percentOwned = percentOwned;
	}
	public String getJersey() {
		return jersey;
	}
	public void setJersey(String jersey) {
		this.jersey = jersey;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public float getPercentStarted() {
		return percentStarted;
	}
	public void setPercentStarted(float percentStarted) {
		this.percentStarted = percentStarted;
	}
	public int getPositionRank() {
		return positionRank;
	}
	public void setPositionRank(int positionRank) {
		this.positionRank = positionRank;
	}
	
	public List<String> getPlayerDisplays() {
		List<String> ids = new ArrayList<String>();
		ids.add(firstName + " " + lastName);
		if(defaultPositionId != 16) {
			ids.add("#" + jersey);
		}
		return ids;
	}
	public int getDefaultPositionId() {
		return defaultPositionId;
	}
	public void setDefaultPositionId(int defaultPositionId) {
		this.defaultPositionId = defaultPositionId;
	}
	public int getHealthStatus() {
		return healthStatus;
	}
	public void setHealthStatus(int healthStatus) {
		this.healthStatus = healthStatus;
	}
	
}
