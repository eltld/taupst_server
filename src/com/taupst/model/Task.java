package com.taupst.model;

public class Task {
	private String task_id;
	private String user_id;
	private String title;
	private String content;
	private String rewards;
	private String release_time;
	private String end_of_time;
	private Integer task_state;
	private Integer task_level;

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public String getRelease_time() {
		return release_time;
	}

	public void setRelease_time(String release_time) {
		this.release_time = release_time;
	}

	public String getEnd_of_time() {
		return end_of_time;
	}

	public void setEnd_of_time(String end_of_time) {
		this.end_of_time = end_of_time;
	}

	public Integer getTask_state() {
		return task_state;
	}

	public void setTask_state(Integer task_state) {
		this.task_state = task_state;
	}

	public Integer getTask_level() {
		return task_level;
	}

	public void setTask_level(Integer task_level) {
		this.task_level = task_level;
	}

}
