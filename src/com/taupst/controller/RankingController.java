package com.taupst.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String rankingList(Integer type, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> rankList = new ArrayList<Map<String, Object>>();

		Map<String, Object> user = (Map<String, Object>) SessionUtil
				.getUser(request);
		String school = (String) user.get("school");

		if (type == 1 || type == 2) {
			rankList = rankingService.list(school, type);
		}

		Map<String, Object> userRank = this.getUsrtRank(
				(String) user.get("users_id"), (String) user.get("school"),
				type);

		// rankList.add(userRank);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("rankList", rankList);
		map.put("mRank", userRank);

		return Object2JsonUtil.Object2Json(map);
	}

	/**
	 * 
	 * @param type
	 *            排行榜类型 <br>
	 *            1：表示总榜 <br>
	 *            2：表示月榜
	 * @param user
	 *            传入的参数users_id and school
	 * @param request
	 * @param response
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rank", method = RequestMethod.GET)
	@ResponseBody
	public String userRank(Integer type, User user, HttpServletRequest request,
			HttpServletResponse response) {
		// 若是没有传则默认查总榜的排名
		if (type == null) {
			type = 1;
		}
		Map<String, Object> u = (Map<String, Object>) SessionUtil
				.getUser(request);
		String users_id = user.getUsers_id();
		String school = (String) u.get("school");
		Map<String, Object> userRank = new HashMap<String, Object>();

		if (users_id == null || users_id.equals("")) {

			userRank = this.getUsrtRank((String) u.get("users_id"), school,
					type);
		} else {
			userRank = rankingService.getRankByUserId(users_id, school, type);
		}

		return Object2JsonUtil.Object2Json(userRank);
	}

	private Map<String, Object> getUsrtRank(String users_id, String school,
			Integer type) {

		Map<String, Object> userRank = new HashMap<String, Object>();

		userRank = rankingService.getRankByUserId(users_id, school, type);

		return userRank;
	}
}
