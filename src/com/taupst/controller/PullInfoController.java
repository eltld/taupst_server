package com.taupst.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.util.Object2JsonUtil;
import com.taupst.util.SessionUtil;


@Controller
@RequestMapping(value = "/data/pull", produces = "application/json;charset=UTF-8")
public class PullInfoController extends BaseController{

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public String svaePullInfo(String user_id,String channel_id, HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> reMap = new HashMap<String, Object>();
		
		if(user_id == null || user_id.equals("")){
			reMap.put("success", false);
			reMap.put("state", 3);
			reMap.put("msg", "user_id不能为空！");
		}
		else if(channel_id == null || channel_id.equals("")){
			reMap.put("success", false);
			reMap.put("state", 4);
			reMap.put("msg", "channel_id不能为空！");
		}
		else{
			Map<String, Object> user = (Map<String, Object>) SessionUtil.getUser(request);
			boolean flag = this.pullService.update((String)user.get("users_id"),user_id,channel_id);
			if(flag == true){
				reMap.put("success", true);
				reMap.put("state", 1);
				reMap.put("msg", "成功！");
			}else if(flag == false){
				reMap.put("success", false);
				reMap.put("state", 2);
				reMap.put("msg", "修改失败！");
			}
		}
		return Object2JsonUtil.Object2Json(reMap);
	}
	
	
}
