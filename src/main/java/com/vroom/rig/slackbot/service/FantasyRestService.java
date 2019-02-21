package com.vroom.rig.slackbot.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroom.rig.slackbot.model.fantasy.BoxScore;
import com.vroom.rig.slackbot.model.fantasy.BoxWrapper;
import com.vroom.rig.slackbot.model.fantasy.Scoreboard;
import com.vroom.rig.slackbot.model.fantasy.ScoreboardWrapper;

@Component
public class FantasyRestService {

	@Autowired
	private RestTemplate template;
	
	@Cacheable(value="boxscores")
	public BoxScore getBoxScore(String leagueId, String seasonId, String teamId, String scoringPeriodId) throws JsonParseException, JsonMappingException, IOException {
		URIBuilder builder = URIBuilder.fromUri("http://games.espn.com/ffl/api/v2/boxscore")
				.queryParam("teamId", teamId)
				.queryParam("leagueId", leagueId)
				.queryParam("seasonId", seasonId);
		
		if(scoringPeriodId != null) {
			builder.queryParam("scoringPeriodId", scoringPeriodId);
		}

		ResponseEntity<String> resp = template.getForEntity(builder.build(), String.class);
		String body = resp.getBody();
		body = body.replace("â\\u0080\\u0099", "' ");
		
		ObjectMapper mapper = new ObjectMapper();
		BoxWrapper wrapper = mapper.readValue(body, BoxWrapper.class);
		return wrapper.getBoxscore();
	}
	
	@Cacheable(value="scoreboards")
	public Scoreboard getScoreboard(String leagueId, String seasonId, String teamId, String scoringPeriodId) throws JsonParseException, JsonMappingException, IOException {
		URIBuilder builder = URIBuilder.fromUri("http://games.espn.com/ffl/api/v2/scoreboard")
				.queryParam("leagueId", leagueId)
				.queryParam("seasonId", seasonId);
		
		if(teamId != null) {
			builder.queryParam("teamId", teamId);
		}
		if(scoringPeriodId != null) {
			builder.queryParam("scoringPeriodId", scoringPeriodId);
		}

		ResponseEntity<String> resp = template.getForEntity(builder.build(), String.class);
		String body = resp.getBody();
		body = body.replace("â\\u0080\\u0099", "' ");
		
		ObjectMapper mapper = new ObjectMapper();
		ScoreboardWrapper wrapper = mapper.readValue(body, ScoreboardWrapper.class);
		return wrapper.getScoreboard();
	}
}
