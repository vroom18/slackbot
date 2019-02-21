package com.vroom.rig.slackbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vroom.rig.slackbot.service.TwitterService;

@RestController
public class UserDetailsController {
	
	@Autowired
	private TwitterService twitter;

	@RequestMapping(value="/users/add/{screenName}", method=RequestMethod.GET)
	public ResponseEntity<HttpStatus> addUser(@PathVariable String screenName) {
		twitter.addUser(screenName);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/users/remove/{screenName}", method=RequestMethod.GET)
	public ResponseEntity<HttpStatus> removeUser(@PathVariable String screenName) {
		twitter.removeUser(screenName);
		return ResponseEntity.ok().build();
	}
	
}
