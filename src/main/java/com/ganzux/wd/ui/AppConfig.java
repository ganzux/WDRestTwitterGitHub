package com.ganzux.wd.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.ganzux.wd.rest.GitHubRestController;
import com.ganzux.wd.rest.TwitteRestController;


@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {
	///////////////////////////////////////////////////////////////
	//                        Attributes                         //
	///////////////////////////////////////////////////////////////
	private static Log log = LogFactory.getLog(Main.class);
	
	@Value("${app.github.api.endpoint}")
	private String githubEndPoint;
	
	@Value("${app.projects.max}")
	private  int maxProjects;
	
	@Value("${app.twitter.app.only.authentication}")
	private String authentication;

	@Value("${app.twitter.request.token.url}")
	private String requestToken;

	@Value("${app.twitter.authorize.url}")
	private String authorizeUrl;

	@Value("${app.twitter.access.token.url}")
	private String accessToken;

	@Value("${app.twitter.consumer.key}")
	private String consumerKey;

	@Value("${app.twitter.consumer.secret}")
	private  String consumerSecret;
	
	@Value("${app.twitter.access.token.key}")
	private String accessTokenKey;

	@Value("${app.twitter.access.token.secret}")
	private  String accessTokenSecret;

	///////////////////////////////////////////////////////////////
	//                        /Attributes                        //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Public Methods                      //
	///////////////////////////////////////////////////////////////
	@Bean
	public GitHubRestController getGitHubRestController() {
		log.info("Register GitHubRestController...");
		GitHubRestController ghrc = new GitHubRestController(githubEndPoint, maxProjects);
		log.info("Register GitHubRestController OK");
		return ghrc;
	}

	@Bean
	public TwitteRestController getTwitterRestController() {
		log.info("Register TwitteRestController...");
		TwitteRestController ghrc = new TwitteRestController(githubEndPoint,
				requestToken, authorizeUrl, accessToken, consumerKey, consumerSecret,
				accessTokenKey, accessTokenSecret);
		log.info("Register TwitteRestController OK");
		return ghrc;
	}
	
	// config file
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	///////////////////////////////////////////////////////////////
	//                      /Public Methods                      //
	///////////////////////////////////////////////////////////////
}