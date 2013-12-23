package com.taupst.service;

import java.util.Map;

import com.taupst.model.User;
import com.taupst.queryhelper.UserQueryHelper;
import com.taupst.util.Page;

public interface UserService {
	
	Page getUserByHelper(Page page,UserQueryHelper helper);
    User getUserById(String user_id);
    int test();
	boolean saveUserInfo(Map<String, String> stuInfo);
	boolean isUserExist(String student_id, String school);
	Map<String , String> login(String student_id, String pwd, String school);
	boolean update(User user);
}
