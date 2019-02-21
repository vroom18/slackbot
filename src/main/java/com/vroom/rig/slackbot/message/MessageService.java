package com.vroom.rig.slackbot.message;

import java.io.IOException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vroom.rig.slackbot.controller.FantasyController;
import com.vroom.rig.slackbot.message.builder.BenchMistakeMessageBuilder;
import com.vroom.rig.slackbot.message.builder.DogMessageBuilder;
import com.vroom.rig.slackbot.message.builder.InactiveMessageBuilder;
import com.vroom.rig.slackbot.message.builder.TightMatchupMessageBuilder;
import com.vroom.rig.slackbot.message.model.MessageHistoryItem;
import com.vroom.rig.slackbot.message.model.MessageType;
import com.vroom.rig.slackbot.model.fantasy.BoxPlayer;
import com.vroom.rig.slackbot.model.fantasy.BoxTeam;
import com.vroom.rig.slackbot.model.fantasy.ScoreboardTeam;
import com.vroom.rig.slackbot.repository.MessageHistoryItemRepository;
import com.vroom.rig.slackbot.service.FantasyService;
import com.vroom.rig.slackbot.service.SlackService;

@Component
public class MessageService {
	
	private static final boolean RETURN_ON_FIRST_MSG=true;

	@Autowired
	private FantasyService fantasy;
	
	@Autowired
	private SlackService slack;
	
	@Autowired
	private MessageHistoryItemRepository messageRepo;
	
	private Logger log = Logger.getLogger(MessageService.class);
	
	public void sendStatusMessages() throws JsonParseException, JsonMappingException, IOException {
		sendInactives();

		/* TODO: Need to evaluate players remaining
		LocalDateTime now = LocalDateTime.now();
		if(now.getDayOfWeek().equals(DayOfWeek.MONDAY) && now.getHour() > 19) {
			sendTightMatchups();
		}
		*/
	}
	
	public void sendContentMessages() throws ParseException, JsonParseException, JsonMappingException, IOException {
		if(RETURN_ON_FIRST_MSG) {
			Random random = new Random();
			int next = random.nextInt(2);
			switch(next) {
				case 0:
					sendBenchMistakes();
					break;
				case 1:
					sendDogs();
					break;
			}
		} else {
			sendBenchMistakes();
			sendDogs();
		}
	}
	
	private boolean sendTightMatchups() throws JsonParseException, JsonMappingException, IOException {
		Set<List<ScoreboardTeam>> matchups = fantasy.getTightMatchups();
		boolean found = false;
		for(List<ScoreboardTeam> matchup : matchups) {
			List<Integer> playerIds = matchup.stream().map(t -> t.getTeamId()).collect(Collectors.toList());
			MessageHistoryItem oldMsg = messageRepo.findByTypeInAndIdsIn(
				Arrays.asList(new MessageType[]{MessageType.TIGHT_MATCHUP}),
				playerIds);
			if(oldMsg == null) {
				String message = TightMatchupMessageBuilder.buildTightMatchupMessage(matchup);
				sendMessage(message, MessageType.TIGHT_MATCHUP, playerIds.toArray(new Integer[playerIds.size()]));
				found = true;
			}
		}
		
		return found;
	}
	
	private boolean sendInactives() throws JsonParseException, JsonMappingException, IOException {
		Map<BoxTeam, List<BoxPlayer>> inactives = fantasy.getImminentInactives();
		boolean found = false;
		for(BoxTeam team : inactives.keySet()) {
			for(BoxPlayer player : inactives.get(team)) {
				MessageHistoryItem oldMsg = messageRepo.findByTypeInAndIdsIn(
						Arrays.asList(new MessageType[]{MessageType.INACTIVE}), 
						Arrays.asList(new Integer[]{player.getPlayer().getPlayerId()}));
				if(oldMsg == null) {
					String message = InactiveMessageBuilder.getInactiveMessage(player, team);
					sendMessage(message, MessageType.INACTIVE, player.getPlayer().getPlayerId());
					found = true;
				}
			}
		}
		return found;
	}
	
	private boolean sendDogs() throws JsonParseException, JsonMappingException, IOException {
		Map<BoxTeam, List<BoxPlayer>> dogMap = fantasy.getDogs();
		boolean found = false;
		for(BoxTeam team : dogMap.keySet()) {
			for(BoxPlayer player : dogMap.get(team)) {
				MessageHistoryItem oldMsg = messageRepo.findByTypeInAndIdsIn(
						Arrays.asList(new MessageType[]{MessageType.DOG}), 
						Arrays.asList(new Integer[]{player.getPlayer().getPlayerId()}));
				if(oldMsg == null) {
					String message = DogMessageBuilder.buildDogMessage(player, team);
					sendMessage(message, MessageType.DOG, player.getPlayer().getPlayerId());
					if(RETURN_ON_FIRST_MSG) {
						return true;
					}
					found = true;
				}
			}
		}
		return found;
	}
	
	private boolean sendBenchMistakes() throws JsonParseException, JsonMappingException, ParseException, IOException {
		Map<BoxTeam, List<List<BoxPlayer>>> benchMistakeMap = fantasy.getBenchMistakes();
		boolean found = false;
		for(BoxTeam team : benchMistakeMap.keySet()) {
			for(List<BoxPlayer> playerList : benchMistakeMap.get(team)) {
				MessageHistoryItem oldMsg = messageRepo.findByTypeInAndIdsIn(
						Arrays.asList(new MessageType[]{MessageType.BENCH_MISTAKE}), 
						playerList.stream().map(p -> p.getPlayer().getPlayerId()).collect(Collectors.toList()));
				if(oldMsg == null) {
					String message = BenchMistakeMessageBuilder.buildBenchMistakeMessage(playerList, team);
					sendMessage(message, MessageType.BENCH_MISTAKE, playerList.get(0).getPlayer().getPlayerId(), playerList.get(1).getPlayer().getPlayerId());
					if(RETURN_ON_FIRST_MSG) {
						return true;
					}
					found = true;
				}
			}
		}
		return found;
	}
	
	private void sendMessage(String message, MessageType type, Integer... ids) {
		slack.postMessage(message);
		
		LocalDate nextWed = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
		MessageHistoryItem item = new MessageHistoryItem();
		item.getIds().addAll(Arrays.asList(ids));
		item.setMessage(message);
		item.setType(type);
		item.setExpiresAt(java.sql.Date.valueOf(nextWed));
		log.info("sending message: " + item);
		messageRepo.save(item);
	}
}
