package com.taupst.dao;

import java.util.Map;

import com.taupst.model.User;
import com.taupst.queryhelper.UserQueryHelper;

public interface UserDao {

	User getUserById(String user_id);

	User getUser(String student_id, String pwd, String school);

	int isUserExist(String student_id, String school);

	int getUserCountByHelper(UserQueryHelper helper);

	boolean saveUserInfo(Map<String, String> stuInfo);

	boolean update(User user);

	boolean update(User user, Map<String, Object> map);

	boolean updateSignature(User u);

	Map<String, Object> login(String student_id, String pwd, String school);

}
