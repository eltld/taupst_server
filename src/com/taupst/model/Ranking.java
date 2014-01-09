package com.taupst.model;

public class Ranking {

	private String ranking_id;
	private String users_id;
	private Integer total_praise;
	private Integer month_praise;

	public String getRanking_id() {
		return ranking_id;
	}

	public void setRanking_id(String ranking_id) {
		this.ranking_id = ranking_id;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public Integer getTotal_praise() {
		return total_praise;
	}

	public void setTotal_praise(Integer total_praise) {
		this.total_praise = total_praise;
	}

	public Integer getMonth_praise() {
		return month_praise;
	}

	public void setMonth_praise(Integer month_praise) {
		this.month_praise = month_praise;
	}

}
