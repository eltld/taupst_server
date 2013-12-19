package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import test.model.User;
import test.service.UserService;
import test.service.impl.UserServiceImpl;
import test.util.ServiceFactory;

/**
 * Servlet implementation class GetUserData
 */
public class GetUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUserData() {
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
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		
		PrintWriter out = response.getWriter();
		
		String users_id = request.getParameter("users_id");
		
		UserService userService = ServiceFactory.getUserService();
		
		User user = userService.getUserById(users_id);
		
		String jsonString = JSON.toJSONString(user);
		
		System.out.println(jsonString);
		out.println(jsonString);
		out.flush();
	}

}
