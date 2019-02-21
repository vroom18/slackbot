package com.vroom.rig.slackbot.message.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MessageHistoryItem {
	
	private MessageType type;
	private String message;
	private List<Integer> ids = new ArrayList<Integer>();
	private Date expiresAt;
	
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Date getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	@Override
	public String toString() {
		return type + " : (" + ids.stream()
				.map(id -> Integer.toString(id))
				.collect(Collectors.joining(",")) + ") - "  + message;
	}
	
}
