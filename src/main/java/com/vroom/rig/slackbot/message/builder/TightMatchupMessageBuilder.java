package com.vroom.rig.slackbot.message.builder;

import java.util.List;
import java.util.Random;

import com.vroom.rig.slackbot.model.fantasy.ScoreboardTeam;

public class TightMatchupMessageBuilder extends MessageBuilder {

	private static final int NUM_MESSAGES = 1;
	private static int lastMessageVal = -1;

	
	public static String buildTightMatchupMessage(List<ScoreboardTeam> matchup) {
		Random random = new Random();
		ScoreboardTeam team1 = matchup.get(0);
		List<String> t1Aliases = aliasMap.get(team1.getTeam().getTeamId());
		ScoreboardTeam team2 = matchup.get(1);
		List<String> t2Aliases = aliasMap.get(team2.getTeam().getTeamId());
		
		int next = 0;
		String message = "";
		switch (next) {
			case 0:
				message += t1Aliases.get(random.nextInt(t1Aliases.size())) + " and "  
					+ t2Aliases.get(random.nextInt(t1Aliases.size())) + " are in a real barn burner. "
					+ "Only " + String.format("%.1f", Math.abs(team1.getScore() - team2.getScore())) + " separate them.";
				break;
			default: 
				message += "WHOOPS";
				break;
		}
		return message;
	}
}
