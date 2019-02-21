package com.vroom.rig.slackbot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vroom.rig.slackbot.service.TwitterService;

@RestController
public class SearchTwitterController {

	@Autowired
	private Twitter twitter;
	
	@RequestMapping("/tweet/{id}")
	public Tweet getTweet(@PathVariable String id) {
		return null;
	}
	
}
