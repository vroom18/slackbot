package com.vroom.rig.slackbot.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vroom.rig.slackbot.message.model.MessageHistoryItem;
import com.vroom.rig.slackbot.message.model.MessageType;

public interface MessageHistoryItemRepository extends MongoRepository<MessageHistoryItem, String> {

	public MessageHistoryItem findByTypeInAndIdsIn(List<MessageType> types, List<Integer> playerIds);
	
}
