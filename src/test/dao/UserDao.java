package test.dao;

import java.util.List;
import java.util.Map;

import test.model.User;
import test.queryhelper.UserQueryHelper;

public interface UserDao {
    List<User> getUserByHelper(UserQueryHelper helper, int startIndex, int endIndex);
	int getUserCountByHelper(UserQueryHelper helper);
	User getUserById(String user_id);
  	int test();
	boolean saveUserInfo(Map<String, String> stuInfo);
	boolean isUserExist(String student_id, String school);
	Map<String , String> login(String student_id, String pwd, String school);

}
