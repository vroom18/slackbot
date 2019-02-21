package com.vroom.rig.slackbot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vroom.rig.slackbot.model.fantasy.BoxScore;
import com.vroom.rig.slackbot.model.fantasy.BoxTeam;
import com.vroom.rig.slackbot.model.fantasy.Scoreboard;
import com.vroom.rig.slackbot.service.FantasyService;
import com.vroom.rig.slackbot.service.SlackService;

@RestController
public class FantasyController {
	
	@Autowired
	private FantasyService fantasy;
	
	@Autowired
	private SlackService slack;
	
	private Logger log = Logger.getLogger(FantasyController.class);
	
	@RequestMapping(value="/boxscore", method=RequestMethod.GET)
	public BoxScore getBox(@RequestParam String teamId,
			@RequestParam(required=false) String scoringPeriodId) throws JsonProcessingException, IOException {
		
		return fantasy.getBoxScore(teamId, scoringPeriodId);
	}
	
	@RequestMapping(value="/scoreboard", method=RequestMethod.GET)
	public Scoreboard getScoreboard(@RequestParam(required=false) String teamId,
			@RequestParam(required=false) String scoringPeriodId) throws JsonParseException, JsonMappingException, IOException {

		return fantasy.getScoreboard(teamId, scoringPeriodId);
	}
	
	@RequestMapping(value="/roster", method=RequestMethod.GET)
	public List<String> printRoster(@RequestParam String teamId,
			@RequestParam(required=false) String scoringPeriodId) throws JsonParseException, JsonMappingException, IOException {
		BoxScore box = fantasy.getBoxScore(teamId, scoringPeriodId);
		List<String> roster = new ArrayList<String>();
		box.getTeams().stream()
			.filter(t -> teamId.equals(Integer.toString(t.getTeam().getTeamId())))
			.findAny()
			.orElse(new BoxTeam())
			.getSlots()
			.forEach(p -> {
				roster.add(String.format("%3s %s %s", 
						p.getPlayer().getJersey() != null ? "#" + p.getPlayer().getJersey() : "", 
								p.getPlayer().getFirstName(),
								p.getPlayer().getLastName()));
			});
		
		return roster;
	}
}
