package com.vroom.rig.slackbot.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;
import com.vroom.rig.slackbot.model.fantasy.BoxPlayer;
import com.vroom.rig.slackbot.model.fantasy.BoxScore;
import com.vroom.rig.slackbot.model.fantasy.BoxTeam;
import com.vroom.rig.slackbot.model.fantasy.HealthStatus;
import com.vroom.rig.slackbot.model.fantasy.ProGame;
import com.vroom.rig.slackbot.model.fantasy.Scoreboard;
import com.vroom.rig.slackbot.model.fantasy.ScoreboardMatchup;
import com.vroom.rig.slackbot.model.fantasy.ScoreboardTeam;

@Component
public class FantasyService {
	
	private static final String LEAGUE_ID="1213905";
	private static final String SEASON_ID="2018";
	private static final int IMMINENT_THRESHOLD=15;
	private static final int BENCH_START_INDEX=9;
	private static final int DOG_ADJUSTMENT=8;
	
	@Autowired
	private FantasyRestService rest;
	
	private static final Logger log = Logger.getLogger(FantasyService.class);
	
	public BoxScore getBoxScore(String teamId, String scoringPeriodId) throws JsonParseException, JsonMappingException, IOException {
		return rest.getBoxScore(LEAGUE_ID, SEASON_ID, teamId, scoringPeriodId);
	}
	
	public Scoreboard getScoreboard(String teamId, String scoringPeriodId) throws JsonParseException, JsonMappingException, IOException {
		return rest.getScoreboard(LEAGUE_ID, SEASON_ID, teamId, scoringPeriodId);
	}
	
	public Set<List<ScoreboardTeam>> getTightMatchups() throws JsonParseException, JsonMappingException, IOException {
		Scoreboard scoreboard = getScoreboard(null, null);
		Set<List<ScoreboardTeam>> matchups = new HashSet<List<ScoreboardTeam>>();
		for(ScoreboardMatchup matchup : scoreboard.getMatchups()) {
			ScoreboardTeam team1 = matchup.getTeams().get(0);
			ScoreboardTeam team2 = matchup.getTeams().get(1);
			if(Math.abs(team1.getScore() - team2.getScore()) < 10) {
				List<ScoreboardTeam> teams = new ArrayList<ScoreboardTeam>();
				teams.add(team1);
				teams.add(team2);
				matchups.add(teams);
			}
		}
		
		return matchups;
	}
	
	public Map<BoxTeam, List<BoxPlayer>> getImminentInactives() throws JsonParseException, JsonMappingException, IOException {
		Scoreboard scoreboard = getScoreboard(null, null);
		Map<BoxTeam, List<BoxPlayer>> inactiveMap = new HashMap<BoxTeam, List<BoxPlayer>>();
		for(ScoreboardMatchup matchup : scoreboard.getMatchups()) {
			BoxScore box = getBoxScore(Integer.toString(matchup.getTeams().get(0).getTeamId()), null);
			List<Integer> inactiveStatuses = Arrays.asList(new Integer[]{HealthStatus.OUT.id, HealthStatus.SUSPENDED.id, HealthStatus.UNKNOWN.id});
			for(BoxTeam team : box.getTeams()) {
				List<BoxPlayer> inactives = team.getSlots().subList(0, BENCH_START_INDEX).stream()
						.filter(p -> inactiveStatuses.contains(p.getPlayer().getHealthStatus()) && gameImminent(box, p))
						.collect(Collectors.toList());
				inactiveMap.put(team, inactives);
			}
		}
		
		return inactiveMap;
	}
	
	public Map<BoxTeam, List<BoxPlayer>> getDogs() throws JsonParseException, JsonMappingException, IOException {
		Scoreboard scoreboard = getScoreboard(null, null);
		Map<BoxTeam, List<BoxPlayer>> dogMap = new HashMap<BoxTeam, List<BoxPlayer>>();
		for(ScoreboardMatchup matchup : scoreboard.getMatchups()) {
			BoxScore box = getBoxScore(Integer.toString(matchup.getTeams().get(0).getTeamId()), null);
			for(BoxTeam team : box.getTeams()) {
				List<List<BoxPlayer>> roster = Lists.partition(team.getSlots(), BENCH_START_INDEX);
				List<BoxPlayer> starters = roster.get(0);
				
				List<BoxPlayer> dogPlayers = new ArrayList<BoxPlayer>();
				for(BoxPlayer player : starters.stream().filter(p -> p.getLockStatus() > 0).collect(Collectors.toList())) {
					ProGame game = box.getProgames().get(player.getProGameIds().get(0));
					if(getMinsRemaining(game) == 0.0 && player.getCurrentScore() + 10 < player.getCurrentPeriodProjectedStats().getAppliedStatTotal()) {
						dogPlayers.add(player);
					}
				}
				if(!dogPlayers.isEmpty()) {
					dogMap.put(team, dogPlayers);
				}
			}
		}
		return dogMap;
	}
	
	public Map<BoxTeam, List<List<BoxPlayer>>> getBenchMistakes() throws ParseException, JsonParseException, JsonMappingException, IOException {
		Scoreboard scoreboard = getScoreboard(null, null);
		Map<BoxTeam, List<List<BoxPlayer>>> mistakeMap = new HashMap<BoxTeam, List<List<BoxPlayer>>>();
		for(ScoreboardMatchup matchup : scoreboard.getMatchups()) {
			BoxScore box = getBoxScore(Integer.toString(matchup.getTeams().get(0).getTeamId()), null);
			for(BoxTeam team : box.getTeams()) {
				List<List<BoxPlayer>> roster = Lists.partition(team.getSlots(), BENCH_START_INDEX);
				List<BoxPlayer> starters = roster.get(0);
				List<BoxPlayer> bench = roster.get(1);
				
				List<List<BoxPlayer>> playersList = new ArrayList<List<BoxPlayer>>();
				for(BoxPlayer player : starters.stream().filter(p -> p.getLockStatus() > 0).collect(Collectors.toList())) {
					BoxPlayer better = getBetterPlayer(box.getProgames(), player, bench);
					if(better != null) {
						ArrayList<BoxPlayer> playerList = new ArrayList<BoxPlayer>();
						playerList.add(player);
						playerList.add(better);
						playersList.add(playerList);
					}
				}
				
				mistakeMap.put(team, playersList);
			}
		}
		return mistakeMap;
	}
	
	private BoxPlayer getBetterPlayer(Map<String, ProGame> progames, BoxPlayer player, List<BoxPlayer> potentialUpgrades) throws ParseException {
		ProGame game = progames.get(player.getProGameIds().get(0));
		double minsRemaining = getMinsRemaining(game);
		if(minsRemaining > 50) {
			return null;
		}
		
		List<BoxPlayer> players = potentialUpgrades.stream().filter(other -> 
			player.getPlayer().getDefaultPositionId() == other.getPlayer().getDefaultPositionId()
				&& (player.getCurrentScore() + DOG_ADJUSTMENT) < other.getCurrentScore()
				&& getMinsRemaining(game) <= getMinsRemaining(progames.get(other.getProGameIds().get(0))))
				.collect(Collectors.toList());

			return players.stream().max(new Comparator<BoxPlayer>() {
				public int compare(BoxPlayer p1, BoxPlayer p2) {
					return p1.getCurrentScore() >= p2.getCurrentScore() ? 1 : -1;
				}
			})
			.orElse(null);
	}
	
	private double getMinsRemaining(ProGame game) {
		String[] splits = game.getTimeRemainingInPeriod().split(":");
		double timeInQuarter = Integer.parseInt(splits[0]);
		timeInQuarter += Integer.parseInt(splits[1]) / 60.0;
		double val = ((4.00 - game.getPeriod()) * 15.00) + timeInQuarter;
		return val > 0 ? val : 0;
	}
	
	private boolean gameImminent(BoxScore box, BoxPlayer player) {
		ProGame game = box.getProgames().values().stream()
				.filter(g -> player.getOpponentProTeamId() == g.getAwayProTeamId() 
					||player.getOpponentProTeamId() == g.getHomeProTeamId())
				.findAny().get();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, IMMINENT_THRESHOLD);
		Date imminent = cal.getTime();
		return game.getGameDate().before(imminent);
	}
	
	
}
