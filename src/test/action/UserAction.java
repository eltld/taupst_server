package test.action;

import java.util.Map;

import test.model.User;
import test.queryhelper.UserQueryHelper;
import test.service.UserService;
import test.util.Page;
import test.util.ServiceFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService userService;
    private String user_id;
    private UserQueryHelper helper;
    private String pageNo;
	
    

	public UserAction() {
		super();
		this.userService = ServiceFactory.getUserService();
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public UserQueryHelper getHelper() {
		return helper;
	}

	public void setHelper(UserQueryHelper helper) {
		this.helper = helper;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	/*public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}*/

	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public String findAllUsers() {
		
		Page page=new Page();
		page.setPageSize(10);        
		if (this.pageNo!=null)
			page.setPageNo(Integer.parseInt(this.pageNo));     
		try {
			
			page = userService.getUserByHelper(page, helper);
            int test = userService.test();
			Map request = (Map) ActionContext.getContext().get("request");
			request.put("pagedUsers", page);
          request.put("test",test);
			return this.SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
          
			return this.ERROR;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public String findUserById() {
		
		try {
			User user = userService.getUserById(this.user_id);
			Map request = (Map) ActionContext.getContext().get("request");
			request.put("user", user);
			return this.SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return this.ERROR;
		}
	}
	
    
		@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
		public String test() {
		
		try {
			User user = userService.getUserById("1");
			Map request = (Map) ActionContext.getContext().get("request");
			request.put("user", user);
			return this.SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return this.ERROR;
		}
	}
	
	

}
