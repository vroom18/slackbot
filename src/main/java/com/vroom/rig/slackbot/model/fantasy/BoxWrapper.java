package com.vroom.rig.slackbot.model.fantasy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BoxWrapper {

	private BoxScore boxscore;

	public BoxScore getBoxscore() {
		return boxscore;
	}

	public void setBoxscore(BoxScore boxscore) {
		this.boxscore = boxscore;
	}
	
}
