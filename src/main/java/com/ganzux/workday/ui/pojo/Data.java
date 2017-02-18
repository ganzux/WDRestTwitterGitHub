package com.ganzux.workday.ui.pojo;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private long id;
    private String content;
    private List<Project> projects = new ArrayList<Project>();
    
    public void addProject(Project project){
    	projects.add(project);
    }
    
    public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	private String error;

    public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public int getNumberProjects() {
		return projects.size();
	}


}