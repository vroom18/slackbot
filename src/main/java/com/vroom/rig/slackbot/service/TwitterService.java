package com.vroom.rig.slackbot.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.vroom.rig.slackbot.model.TweetDetails;
import com.vroom.rig.slackbot.model.TweetStats;
import com.vroom.rig.slackbot.model.UserDetails;
import com.vroom.rig.slackbot.repository.TweetDetailsRepository;
import com.vroom.rig.slackbot.repository.UserDetailsRepository;


@Component
public class TwitterService {
	
	@Autowired
	private Twitter twitter;
	
	@Autowired 
	private TweetDetailsRepository tweetRepo;
	
	@Autowired
	private UserDetailsRepository userRepo;
	
	private static final int PARTITION_SIZE=5;
	private static final int MINUTE_OFFSET=5;
	private static final int SEARCH_COUNT=100;

	private Logger log = Logger.getLogger(TwitterService.class);
	
	public void addUser(String username) {
		UserDetails details = userRepo.findByName(username);
		if(details == null) {
			TwitterProfile profile = twitter.userOperations().getUserProfile(username);
			details = new UserDetails(profile.getId(), profile.getScreenName());
			userRepo.save(details);
			initializeTweets(details);
		}
	}
	
	public void removeUser(String username) {
		UserDetails details = userRepo.findByName(username);
		if(details != null) {
			userRepo.delete(details.getUserId());
			tweetRepo.deleteByUsername(username);
		}
	}
	
	/*
	 * Filter old tweets on user add
	 */
	public void initializeTweets(UserDetails user) {
		String query = "from:" + user.getName();
		SearchParameters search = new SearchParameters(query).count(100);
		List<Tweet> tweets = twitter.searchOperations().search(search).getTweets();
		for(Tweet tweet : tweets) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, MINUTE_OFFSET);
			Date offset = cal.getTime();
			TweetDetails details = new TweetDetails(tweet);
			if(tweet.getCreatedAt().before(offset)) {
				details.setHasBeenSlacked(true);
			}
			tweetRepo.save(details);
		}
	}
	
	public void refreshTweets() {
		List<String> following = userRepo.findAll().stream()
				.map(u -> u.getName())
				.collect(Collectors.toList());
		Collections.shuffle(following);
		List<List<String>> namePartitions = Lists.partition(following, PARTITION_SIZE);
		
		List<Tweet> tweets = new ArrayList<Tweet>();
		for(List<String> partition : namePartitions) {
			String usersQuery = "from:" + partition.get(0);
			for(int i = 1; i < partition.size(); i++) {
				usersQuery += " OR from:" + partition.get(i);
			}
			log.info("User search query:" + usersQuery);
			SearchParameters search = new SearchParameters(usersQuery).count(SEARCH_COUNT);
			List<Tweet> partitionTweets = twitter.searchOperations().search(search).getTweets();
			if(partitionTweets != null) {
				tweets.addAll(partitionTweets);
			}
			
		}

		for(Tweet tweet : tweets) {
			TweetDetails tweetDetails = tweetRepo.findOne(tweet.getIdStr());
			if(tweetDetails == null) {
				tweetDetails = new TweetDetails(tweet);
				tweetRepo.save(tweetDetails);
			}
		}
	}
	
	public TweetStats getTweetStats(Tweet tweet, Date time) {
		TweetStats tweetStats = new TweetStats();
		tweetStats.setTime(time);
		tweetStats.setFavorites(tweet.getFavoriteCount());
		tweetStats.setRetweets(tweet.getRetweetCount());
		tweetStats.setUserFollowers(tweet.getUser().getFollowersCount());
		return tweetStats;
	}
	
}
