package com.vroom.rig.slackbot.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vroom.rig.slackbot.model.UserDetails;

public interface UserDetailsRepository extends MongoRepository<UserDetails, Long>{

	public UserDetails findByName(String name);
	public List<UserDetails> findByFilterReplies(boolean filter);
	
}

