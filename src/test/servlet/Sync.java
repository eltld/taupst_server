package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.service.UserService;
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

		boolean isExist = userService.isUserExist(student_id,school);

		boolean isSucceed = false;
		if(isExist == true){
			isSucceed = userService.login(student_id,pwd,school);
		}else{
			Sysn sysn = SysnFac.getConn(school, student_id, pwd);
			Map<String, String> stuInfo = stuInfo = sysn.login();
			stuInfo.put("pwd", pwd);
			stuInfo.put("school", school);
			if(stuInfo.get("isLoginSuccess").equals("true")){
				isSucceed = userService.saveUserInfo(stuInfo);
			}
		}
		
		out.println(isSucceed);
		out.flush();
	}
}
