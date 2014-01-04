package com.taupst.model;

public class Sign {
	private String sign_id;
	private String task_id;
	private String users_id;
	private String sign_time;
	private String open_mes;
	private Integer isexe;
	private String message;

	public String getSign_id() {
		return sign_id;
	}

	public void setSign_id(String sign_id) {
		this.sign_id = sign_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public String getSign_time() {
		return sign_time;
	}

	public void setSign_time(String sign_time) {
		this.sign_time = sign_time;
	}

	public String getOpen_mes() {
		return open_mes;
	}

	public void setOpen_mes(String open_mes) {
		this.open_mes = open_mes;
	}

	public Integer getIsexe() {
		return isexe;
	}

	public void setIsexe(Integer isexe) {
		this.isexe = isexe;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
