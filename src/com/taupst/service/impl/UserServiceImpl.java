package com.taupst.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.taupst.model.User;
import com.taupst.service.UserService;


@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		return this.userDao.getUserById(userId);
	}

	@Override
	public boolean saveUserInfo(Map<String, String> stuInfo) {
		return this.userDao.saveUserInfo(stuInfo);
	}

	@Override
	public int isUserExist(String student_id, String school) {
		return this.userDao.isUserExist(student_id, school);
	}

	@Override
	public Map<String, Object> login(String student_id, String pwd,
			String school) {
		return this.userDao.login(student_id, pwd, school);
	}

	@Override
	public boolean update(User user) {
		return this.userDao.update(user);
	}

	@Override
	public User getUser(String student_id, String pwd, String school) {
		return this.userDao.getUser(student_id, pwd, school);
	}

	@Override
	public boolean updateSignature(User u) {
		return this.userDao.updateSignature(u);
	}

	@Override
	public boolean update(User user, Map<String, Object> map) {
		return this.userDao.update(user, map);
	}
}
