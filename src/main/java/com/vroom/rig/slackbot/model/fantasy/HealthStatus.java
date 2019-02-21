package com.vroom.rig.slackbot.model.fantasy;

public enum HealthStatus {
	OUT(11),QUESTIONABLE(2),HEALTHY(0),SUSPENDED(13),UNKNOWN(4);
	
	public final int id;
	
	HealthStatus(int id) {
		this.id = id;
	}
}
