package com.vroom.rig.slackbot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
public class TwitterConfig implements SocialConfigurer{
	
	//%s - user, %d - tweetId
	public static final String twitUrlFormat = "https://twitter.com/%s/status/%s";
	
	@Autowired
	private Environment env;
	
	@Bean
	public Twitter twitterTemplate() {
		String user = env.getProperty("twitter.user");
		String consumerKey = env.getProperty(user + ".consumerKey");
		String consumerSecret = env.getProperty(user + ".consumerSecret");
		String accessToken = env.getProperty(user + ".accessToken");
		String accessTokenSecret = env.getProperty(user + ".accessTokenSecret");
		return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}
	
	@Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		String user = env.getProperty("twitter.user");
        cfConfig.addConnectionFactory(new TwitterConnectionFactory(
            env.getProperty(user + ".consumerKey"),
            env.getProperty(user + ".consumerSecret")));
    }

	@Override
	public UserIdSource getUserIdSource() {
		return null;
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator arg0) {
		return null;
	}
	
}
