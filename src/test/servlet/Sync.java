package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.service.UserService;
import test.util.Object2JsonUtil;
import test.util.ServiceFactory;
import test.util.sync.Sysn;
import test.util.sync.SysnFac;
/**
 * Servlet implementation class Sync
 */
public class Sync extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sync() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("GBK");

		PrintWriter out = response.getWriter();

		String student_id = request.getParameter("student_id");
		String pwd = request.getParameter("pwd");
		String school = request.getParameter("school");
		
		UserService userService = ServiceFactory.getUserService();
		// 判断该用户在数据库中是否存在
		boolean isExist = userService.isUserExist(student_id,school);

		Map<String , String> returnMap = null;
		// 该用户在数据库中存在
		if(isExist == true){
			returnMap = userService.login(student_id,pwd,school);
		}else{
			// 该用户在数据库中不存在，到教务系统中获取用户信息保存到数据库中
			Sysn sysn = SysnFac.getConn(school, student_id, pwd);
			Map<String, String> stuInfo = sysn.login();
			// 该用户在教务系统中登录失败，表示用户名或密码错误。
			if(stuInfo.get("isLoginSuccess").equals("true")){
				stuInfo.put("pwd", pwd);
				stuInfo.put("school", school);
				// 该用户在教务系统中的信息保存到数据库中
				if(userService.saveUserInfo(stuInfo)){
					returnMap = userService.login(student_id,pwd,school);
				}else{
					returnMap = new HashMap<String, String>();
					returnMap.put("isLogined", "false");
				}
			}else{
				returnMap = new HashMap<String, String>();
				returnMap.put("isLogined", "false");
			}
		}
		String jsonString = Object2JsonUtil.Object2Json(returnMap);
		out.println(jsonString);
		out.flush();
	}
}
