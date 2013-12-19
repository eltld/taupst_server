package test.queryhelper;

import java.io.UnsupportedEncodingException;

public class UserQueryHelper {

	 private String student_id;
	 private String realname;
	 private String department;
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String studentId) {
		student_id = studentId;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) throws UnsupportedEncodingException {
		this.realname = new String(realname.getBytes("iso-8859-1"),"GBK");
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) throws UnsupportedEncodingException {
		this.department = new String(department.getBytes("iso-8859-1"),"GBK");
	}
	 
	 
	 
}
