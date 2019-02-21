package com.vroom.rig.slackbot.message.builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageBuilder {
	
	public static final boolean TAG_IN_SLACK=true;

	public static Map<Integer, List<String>> aliasMap = new HashMap<Integer, List<String>>() {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
	    }
	};
	
	public static Map<Integer, String> slackIdMap = new HashMap<Integer, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(1, "<@U141ADKQF>");
			put(12, "<@U18GWR5U6>");
			put(3, "<@U21L709C7>");
			put(5, "<@U17R4SU0P>");
			put(11, "<@U25QQL563>");
			put(2, "<@U190S077E>");
			put(8, "<@U21L1PX1T>");
			put(6, "<@U21KYK7PW>");
			put(4, "<@U25NGBX6X>");
			put(13, "<@U17UH9F0D>");
			put(7, "<@U22190ZKQ>");
			put(9, "<@U25M6R55G>");
		}
	};
}
