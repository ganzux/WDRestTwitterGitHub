package com.ganzux.workday.rest;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.ganzux.workday.pojo.GitHubPojo;

@ComponentScan
public class GitHubRestController {
	///////////////////////////////////////////////////////////////
	//                        Attributes                         //
	///////////////////////////////////////////////////////////////
	private static Log log = LogFactory.getLog(GitHubRestController.class);
	
	@Value("${app.github.api.endpoint}")
	private static String githubEndPoint = "https://api.github.com/search/repositories?q="; // FIXME
	///////////////////////////////////////////////////////////////
	//                        /Attributes                        //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Public Methods                      //
	///////////////////////////////////////////////////////////////
	public static GitHubPojo getData(String query){
		RestTemplate restTemplate = new RestTemplate();
		
		String callId = System.currentTimeMillis() + "_node1";
		
		String finalEndPoint = githubEndPoint + query;
		
		log.debug(finalEndPoint);

		Object response = 
				restTemplate.getForObject(finalEndPoint, GitHubPojo.class);
		GitHubPojo responseJSonMapped = (GitHubPojo) response;

		return responseJSonMapped;
	}
}
