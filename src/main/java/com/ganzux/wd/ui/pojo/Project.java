package com.ganzux.wd.ui.pojo;

import java.util.ArrayList;
import java.util.List;

import com.ganzux.wd.pojo.Item;

public class Project {

	private String name;
	private String url;
	private String description;
	private Integer id;
	private List<String[]> tweetsList = new ArrayList<String[]>();
	public Project(Item item) {
		this.name = item.getName();
		this.url = item.getUrl();
		this.description = item.getDescription();
		this.id = item.getId();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<String[]> getTweetsList() {
		return tweetsList;
	}
	public void setTweetsList(List<String[]> tweetsList) {
		this.tweetsList = tweetsList;
	}
	public void addTweets(List<String[]> tweetsList) {
		this.tweetsList.addAll(tweetsList);
	}
	
}
