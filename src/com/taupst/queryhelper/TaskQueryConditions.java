package com.taupst.queryhelper;

public class TaskQueryConditions {
	
	private String task_id;
	private String level = "%%";
	private String keywords = "%%";

	public TaskQueryConditions() {
		super();
	}

	public TaskQueryConditions(String level, String keywords) {
		super();
		if(level != null){
			this.level = "%" + level + "%";
		}
		if(keywords != null){
			this.keywords = "%" + keywords + "%";
		}
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = "%" + level + "%";
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = "%" + keywords + "%";
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

}
