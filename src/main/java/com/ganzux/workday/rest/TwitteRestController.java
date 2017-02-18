package com.ganzux.workday.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;

import com.ganzux.workday.rest.exception.ConnectionException;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@ComponentScan
public class TwitteRestController {
	///////////////////////////////////////////////////////////////
	//                        Attributes                         //
	///////////////////////////////////////////////////////////////
	private static Log log = LogFactory.getLog(TwitteRestController.class);

	private String authentication;
	private String requestToken;
	private String authorizeUrl;
	private String accessToken;
	private String consumerKey;
	private String consumerSecret;
	private String accessTokenKey;
	private String accessTokenSecret;
	
	Twitter twitter = null;
	///////////////////////////////////////////////////////////////
	//                        /Attributes                        //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Public Methods                      //
	///////////////////////////////////////////////////////////////
	public TwitteRestController(String authentication, String requestToken, String authorizeUrl, String accessToken,
			String consumerKey, String consumerSecret, String accessTokenKey, String accessTokenSecret) {
		super();
		this.authentication = authentication;
		this.requestToken = requestToken;
		this.authorizeUrl = authorizeUrl;
		this.accessToken = accessToken;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessTokenKey = accessTokenKey;
		this.accessTokenSecret = accessTokenSecret;
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		   cb.setDebugEnabled(true)
		       .setOAuthConsumerKey(this.consumerKey)
		       .setOAuthConsumerSecret(this.consumerSecret)
		       .setOAuthAccessToken(this.accessTokenKey)
		       .setOAuthAccessTokenSecret(this.accessTokenSecret);
		   TwitterFactory tf = new TwitterFactory(cb.build());
		   twitter = tf.getInstance();	   
	}
	
	public List<String[]> getData(String queryTxt) throws ConnectionException{
		
		List<String[]> tweetsList = new ArrayList<String[]>();
		
		   try {
		       Query query = new Query(queryTxt);
		       QueryResult result;
		       do {
		           result = twitter.search(query);
		           List<Status> tweets = result.getTweets();
		           for (Status tweet : tweets) {
		        	   String[] data = new String[2];
		        	   String user = tweet.getUser().getScreenName();
		        	   String tweetTxt = tweet.getText();
		        	   data[0] = user;
		        	   data[1] = tweetTxt;
		        	   tweetsList.add(data);
		        	   log.debug("@" + user + ": " + tweetTxt);
		           }
		       } while ((query = result.nextQuery()) != null);

		   } catch (TwitterException te) {
		       log.error("Error connection with Twitter REST Service." + te.getMessage());
		       throw new ConnectionException("Error connection with Twitter REST Service.", te);
		   }
		
		return tweetsList;
	}
	///////////////////////////////////////////////////////////////
	//                      /Public Methods                      //
	///////////////////////////////////////////////////////////////
}
