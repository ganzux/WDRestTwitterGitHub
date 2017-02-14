package com.ganzux.workday.ui;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ganzux.workday.pojo.GitHubPojo;
import com.ganzux.workday.pojo.Item;
import com.ganzux.workday.rest.GitHubRestController;
import com.ganzux.workday.ui.pojo.Data;

@ComponentScan
@Controller
@EnableAutoConfiguration
public class FormController {

	@GetMapping("/")
    public String getMainForm(Model model) {
        model.addAttribute("data", new Data());
        return "data";
    }

    @PostMapping("/")
    public String postMainForm(@ModelAttribute Data data) {
    	
    	final String projectName = data.getContent();
    	final String gitHubEndPoint = projectName;
    	
    	// Call to GitHub to get all the projects
    	GitHubPojo projects = GitHubRestController.getData(gitHubEndPoint);
    	
    	data.setNumberProjects(projects.getTotalCount());
    	
    	// Connect to twitter
    	if (projects.getTotalCount() > 0){
    		for (Item item : projects.getItems()){
    			
    		
    		}
    	}
    	
        return "result";
    }
}
