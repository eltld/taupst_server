package com.taupst.util.sync;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.taupst.util.SessionUtil;

public class HttpClientUtil {

	public static CloseableHttpClient getHttpClient(HttpServletRequest request,
			String attrName) {
		
		if (attrName == null) {
			attrName = "mHttpClient";
		}
		CloseableHttpClient httpClient = (CloseableHttpClient) SessionUtil
				.getAttr(request, attrName);
		if (httpClient == null) {
			httpClient = HttpClients.createDefault();
		}
		return httpClient;
	}

}
