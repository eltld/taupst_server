package test.service.impl;

import java.util.Map;

import test.dao.UserDao;
import test.model.User;
import test.queryhelper.UserQueryHelper;
import test.service.UserService;
import test.util.DaoFactory;
import test.util.Page;

public class UserServiceImpl implements UserService {
	private UserDao userDao = DaoFactory.getUserDao();

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		return this.userDao.getUserById(userId);
	}

	@Override
	public Page getUserByHelper(Page page, UserQueryHelper helper) {

		page.setTotalRecNum((long) this.userDao.getUserCountByHelper(helper));
		page.setPageContent(this.userDao.getUserByHelper(helper,
				page.getStartIndex(), page.getEndIndex()));
		return page;
	}

	@Override
	public int test() {
		return this.userDao.test();
	}

	@Override
	public boolean saveUserInfo(Map<String, String> stuInfo) {
		return this.userDao.saveUserInfo(stuInfo);
	}

	@Override
	public boolean isUserExist(String student_id, String school) {
		return this.userDao.isUserExist(student_id, school);
	}

	@Override
	public Map<String , String> login(String student_id, String pwd, String school) {
		return this.userDao.login(student_id, pwd, school);
	}
}
