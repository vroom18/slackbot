package com.vroom.rig.slackbot.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vroom.rig.slackbot.model.TweetDetails;

public interface TweetDetailsRepository extends MongoRepository<TweetDetails, String>{
	
	public List<TweetDetails> findByHasBeenSlackedAndCreatedAtAfter(boolean slacked, Date after);
	public void deleteByUsername(String username);
}
