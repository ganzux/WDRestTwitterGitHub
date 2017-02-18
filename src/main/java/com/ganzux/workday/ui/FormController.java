package com.ganzux.workday.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ganzux.workday.pojo.GitHubPojo;
import com.ganzux.workday.pojo.Item;
import com.ganzux.workday.rest.GitHubRestController;
import com.ganzux.workday.rest.TwitteRestController;
import com.ganzux.workday.rest.exception.ConnectionException;
import com.ganzux.workday.ui.pojo.Data;
import com.ganzux.workday.ui.pojo.Project;

@ComponentScan
@Controller
@EnableAutoConfiguration
public class FormController {
	///////////////////////////////////////////////////////////////
	//                        Attributes                         //
	///////////////////////////////////////////////////////////////
	private static Log log = LogFactory.getLog(FormController.class);
	
	@Autowired
	GitHubRestController gitHuhController;
	
	@Autowired
	TwitteRestController twitterController;
	///////////////////////////////////////////////////////////////
	//                        /Attributes                        //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Public Methods                      //
	///////////////////////////////////////////////////////////////
	@GetMapping("/")
    public String getMainForm(Model model) {
		log.debug("GET: " + model);
        model.addAttribute("data", new Data());
        model.addAttribute("json", "");
        return "data";
    }

	@PostMapping("/")
    public String postMainForm(@ModelAttribute Data data) {
		retrieveData(data);
        return "result";
    }
	
	@GetMapping("/json")
    public String getMainFormJSON(Model model) {
		log.debug("GET: " + model);
		model.addAttribute("data", new Data());
		model.addAttribute("json", "json");
        return "data";
    }
	
	@PostMapping("/json")
    public ResponseEntity<Data> postMainFormJSON(@ModelAttribute Data data) {
    	return retrieveData(data);
    }
	///////////////////////////////////////////////////////////////
	//                      /Public Methods                      //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Private Methods                      //
	///////////////////////////////////////////////////////////////
	private ResponseEntity<Data> retrieveData(@ModelAttribute Data data) {
    	log.debug("POST: " + data);
    	HttpStatus status = HttpStatus.OK;
   
    	final String projectName = data.getContent();
    	final String gitHubEndPoint = projectName;
    	
    	GitHubPojo projects = null;
    	
    	try{
	    	// Call to GitHub to get all the projects
	    	projects = gitHuhController.getData(gitHubEndPoint);

    	} catch(ConnectionException ce){
    		data.setError("Error connecting to GitHub. Check log files and contact with your sysadmin");
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}


    	// Connect to twitter
    	try{
	    	if (projects != null && projects.getTotalCount() > 0){

	    		for (Item item : projects.getItems()){

	    			Project project = new Project(item);

	    			String lookForInTwitter = item.getFullName();
	    			project.addTweets(twitterController.getData(lookForInTwitter));

	    			data.addProject(project);
	    		}

	    	}
    	} catch(ConnectionException ce){
    		data.setError("Error connecting to Twitter. Check log files and contact with your sysadmin");
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}

    	return new ResponseEntity<Data>(data, status);
    }
	///////////////////////////////////////////////////////////////
	//                     /Private Methods                      //
	///////////////////////////////////////////////////////////////
}