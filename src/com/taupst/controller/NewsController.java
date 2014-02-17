package com.taupst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.News;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.SessionUtil;

@Controller
@RequestMapping(value = "/data/news", produces = "application/json;charset=UTF-8")
public class NewsController extends BaseController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/newsList2Down", method = RequestMethod.GET)
	@ResponseBody
	public String newsListByDown(News news, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> u = (Map<String, Object>) SessionUtil
				.getUser(request);
		String users_id = (String) u.get("users_id");
		String news_id = news.getNews_id();
		int type = 0;
		// 若是news_id == null 就查所有
		if (news_id != null && !news_id.equals("")) {
			type = 1;
		}

		List<Map<String, Object>> map = newsService.list(users_id, news_id,
				type);

		return Object2JsonUtil.Object2Json(map);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/newsList2Up", method = RequestMethod.GET)
	@ResponseBody
	public String newsListByUp(News news, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> u = (Map<String, Object>) SessionUtil
				.getUser(request);
		String users_id = (String) u.get("users_id");
		String news_id = news.getNews_id();
		int type = 0;
		// 若是news_id == null 就查所有
		if (news_id != null && !news_id.equals("")) {
			type = 2;
		}

		List<Map<String, Object>> map = newsService.list(users_id, news_id,
				type);

		return Object2JsonUtil.Object2Json(map);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	public String newsDetail(News news, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> user = (Map<String, Object>) SessionUtil.getUser(request);
		String myName = (String) user.get("username");
		
		Map<String, Object> tmList = new HashMap<String, Object>();
		String source = news.getSource();
		Integer type = news.getType();
		switch (type) {
		case 1:
			tmList = newsService.tmList(source);
			break;
		case 2:
			tmList = newsService.signInfo(source);
			tmList.put("my_name", myName);
			break;
		case 3:
			tmList = newsService.signObject(source);
			tmList.put("my_name", myName);
			break;
		case 4:
			tmList = newsService.aftetTaskInfo(source);
			tmList.put("my_name", myName);
			break;
		}
		return Object2JsonUtil.Object2Json(tmList);
	}

}
