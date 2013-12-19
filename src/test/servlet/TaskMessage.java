package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import test.service.TaskMesService;
import test.service.impl.TaskMesServiceImpl;
import test.util.Page;
import test.util.ServiceFactory;

/**
 * Servlet implementation class TaskMessage
 */
public class TaskMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskMessage() {
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
		
		String task_id = request.getParameter("task_Id");
		String index = request.getParameter("index");
		
		Page page = new Page();
		page.setPageNo(Integer.parseInt(index));
		page.setPageSize(10);
		
		TaskMesService taskMesService = ServiceFactory.getTaskMesService();
		
		List<Map<String,Object>> tm = taskMesService.getTaskMesList(task_id,page);
		
		String jsonString = JSON.toJSONString(tm);
		
		System.out.println(jsonString);
		out.println(jsonString);
		out.flush();
	}

}
