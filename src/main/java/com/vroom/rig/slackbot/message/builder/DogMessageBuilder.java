package com.vroom.rig.slackbot.message.builder;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import com.vroom.rig.slackbot.model.fantasy.BoxPlayer;
import com.vroom.rig.slackbot.model.fantasy.BoxTeam;

public class DogMessageBuilder extends MessageBuilder {
	
	private static final int NUM_MESSAGES = 3;
	private static int lastMessageVal = -1;

	public static String buildDogMessage(BoxPlayer player, BoxTeam team) {
		String message = "";
		Random random = new Random();
		String playerNickname = player.getPlayer().getPlayerDisplays().get(
				random.nextInt(player.getPlayer().getPlayerDisplays().size()));
		String playerName = player.getPlayer().getFirstName() + " " + player.getPlayer().getLastName();
		
		
		String slackId = slackIdMap.get(team.getTeam().getTeamId());
		List<String> aliases = aliasMap.get(team.getTeam().getTeamId());
		
		int next =  random.nextInt(NUM_MESSAGES);
		while(next == lastMessageVal) {
			next =  random.nextInt(NUM_MESSAGES);
		}
		lastMessageVal = next;
		switch (next) {
			default: 
				message += "WHOOPS";
				break;
		}
	
		return message;
	}
	
}
