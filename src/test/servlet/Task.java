package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.service.TaskService;
import test.util.Object2JsonUtil;
import test.util.Page;
import test.util.ServiceFactory;

/**
 * Servlet implementation class Task
 */
public class Task extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Task() {
		super();
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
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk");

		PrintWriter out = response.getWriter();
		
		String index = request.getParameter("index");
		
		Page page = new Page();
		page.setPageNo(Integer.parseInt(index));
		page.setPageSize(10);
		
		TaskService taskService = ServiceFactory.getTaskService();
		
		List<Map<String, Object>> taskList = taskService.getTaskList(page);
		
		String jsonString = Object2JsonUtil.Object2Json(taskList);
		
		System.out.println(jsonString);
		out.println(jsonString);
		out.flush();
	}

}
