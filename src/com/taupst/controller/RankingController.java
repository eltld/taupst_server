package com.taupst.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.User;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.SessionUtil;

@Controller
@RequestMapping(value = "/data/ranking", produces = "application/json;charset=UTF-8")
public class RankingController extends BaseController {

	/**
	 * 
	 * @param type
	 *            排行榜类型 <br>
	 *            1：表示总榜 <br>
	 *            2：表示月榜
	 * @param request
	 * @param response
	 * @return 排行榜列表集合
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String rankingList(Integer type, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> rankList = new ArrayList<Map<String, Object>>();

		User user = (User) SessionUtil.getUser(request);
		String school = user.getSchool();

		if (type == 1 || type == 2) {
			rankList = rankingService.list(school, type);
		}

		return Object2JsonUtil.Object2Json(rankList);
	}

}
