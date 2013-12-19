package test.model;

public class TaskQueryConditions {

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

}
