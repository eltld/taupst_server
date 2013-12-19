package test.model;

/**
 * UsersInfo entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer users_id; //
	private String student_id; //
	private String school;
	private String username;
	private String student_pwd;
	private String pwd;
	private Integer sex; //
	private String department; //
	private String classname; //
	private String photo;
	private String nickname;
	private String introduction;
	private Integer orientation;
	private String qq;
	private String email;
	private String phone;
	private Integer coins;
	private Integer praise; //
	private Integer nickname_tail;
	private Integer certification;
	private String channel_id;
	private String realname; //
	private String special;
	private String grade;
	private String open_id;

	// Constructors

	public Integer getUsers_id() {
		return this.users_id;
	}

	public User(Integer users_id, String student_id, String school,
			String username, String student_pwd, String pwd, Integer sex,
			String department, String classname, String photo, String nickname,
			String introduction, Integer orientation, String qq, String email,
			String phone, Integer coins, Integer praise, Integer nickname_tail,
			Integer certification, String channel_id, String realname) {
		super();
		this.users_id = users_id;
		this.student_id = student_id;
		this.school = school;
		this.username = username;
		this.student_pwd = student_pwd;
		this.pwd = pwd;
		this.sex = sex;
		this.department = department;
		this.classname = classname;
		this.photo = photo;
		this.nickname = nickname;
		this.introduction = introduction;
		this.orientation = orientation;
		this.qq = qq;
		this.email = email;
		this.phone = phone;
		this.coins = coins;
		this.praise = praise;
		this.nickname_tail = nickname_tail;
		this.certification = certification;
		this.channel_id = channel_id;
		this.realname = realname;
	}

	public void setUsers_id(Integer usersId) {
		this.users_id = usersId;
	}

	public String getStudent_id() {
		return this.student_id;
	}

	public void setStudent_id(String studentId) {
		this.student_id = studentId;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStudent_pwd() {
		return this.student_pwd;
	}

	public void setStudent_pwd(String studentPwd) {
		this.student_pwd = studentPwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getOrientation() {
		return orientation;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCoins() {
		return coins;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Integer getNickname_tail() {
		return this.nickname_tail;
	}

	public void setNickname_tail(Integer nicknameTail) {
		this.nickname_tail = nicknameTail;
	}

	public Integer getCertification() {
		return certification;
	}

	public void setCertification(Integer certification) {
		this.certification = certification;
	}

	public String getChannel_id() {
		return this.channel_id;
	}

	public void setChannel_id(String channelId) {
		this.channel_id = channelId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	/** default constructor */
	public User() {
	}

}