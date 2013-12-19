package test.service;

import java.util.List;
import java.util.Map;



import test.model.User;
import test.queryhelper.UserQueryHelper;
import test.util.Page;

public interface UserService {
	
	Page getUserByHelper(Page page,UserQueryHelper helper);
    User getUserById(String user_id);
    int test();
	boolean saveUserInfo(Map<String, String> stuInfo);
	boolean isUserExist(String student_id, String school);
	boolean login(String student_id, String pwd, String school);
}
