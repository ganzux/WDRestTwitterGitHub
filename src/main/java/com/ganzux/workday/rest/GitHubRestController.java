package com.ganzux.workday.rest;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import com.ganzux.workday.pojo.GitHubPojo;
import com.ganzux.workday.pojo.Item;
import com.ganzux.workday.rest.exception.ConnectionException;

@ComponentScan
public class GitHubRestController {
	///////////////////////////////////////////////////////////////
	//                        Attributes                         //
	///////////////////////////////////////////////////////////////
	private static Log log = LogFactory.getLog(GitHubRestController.class);

	private String githubEndPoint;
	private int maxProjects;
	///////////////////////////////////////////////////////////////
	//                        /Attributes                        //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Public Methods                      //
	///////////////////////////////////////////////////////////////
	public GitHubRestController(String githubEndPoint, int maxProjects){
		this.githubEndPoint = githubEndPoint;
		this.maxProjects = maxProjects;
	}

	public GitHubPojo getData(String query) throws ConnectionException{
		log.debug(query);

		try{
			RestTemplate restTemplate = new RestTemplate();
			
			String callId = System.currentTimeMillis() + "_node1";
			
			String finalEndPoint = githubEndPoint + query;
	
			log.info(finalEndPoint + "-" + callId);
	
			Object response = 
					restTemplate.getForObject(finalEndPoint, GitHubPojo.class);
			GitHubPojo responseJSonMapped = (GitHubPojo) response;
	
			log.debug(responseJSonMapped);
	
			int count = 0;
			if (responseJSonMapped != null && responseJSonMapped.getTotalCount() != null 
					&& responseJSonMapped.getTotalCount() > 0){
				Iterator<Item> it = responseJSonMapped.getItems().iterator();
				 
			    while(it.hasNext()) {
			    	it.next();
			    	if (count >= 10){
			    		it.remove();
			    	}
			    	count ++;
			    }
			    responseJSonMapped.setTotalCount(responseJSonMapped.getItems().size());
			}
			
			log.debug(responseJSonMapped);
			
			return responseJSonMapped;
		} catch (Exception e){
			log.error("Error connection with GitHub REST Service." + e.getMessage());
			throw new ConnectionException("Error connection with GitHub REST Service.", e);
		}
	}
	///////////////////////////////////////////////////////////////
	//                      /Public Methods                      //
	///////////////////////////////////////////////////////////////

}
