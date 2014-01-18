package com.taupst.util.sync;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;

public interface Sysn {
	public Map<String, String> login(HttpServletRequest request) throws ClientProtocolException, IOException ;
}
