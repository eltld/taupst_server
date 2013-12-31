package com.taupst.util;

import com.alibaba.fastjson.JSON;

public class Object2JsonUtil {

	public static String Object2Json(Object obj){
		return JSON.toJSONString(obj);
	}
	
}
