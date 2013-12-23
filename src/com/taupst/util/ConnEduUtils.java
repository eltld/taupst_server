package com.taupst.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("deprecation")
public class ConnEduUtils {
	
	private String UserName;
	private String Password;
	private DefaultHttpClient httpClient;
	private Map<String, String> p;
	private String cookie;
	public String getUserName() {
		return UserName;
	}

	public String getPassword() {
		return Password;
	}


		
	public ConnEduUtils() {
		super();
	}

	public boolean login(String userName, String password) throws ClientProtocolException, IOException{
		this.UserName = userName;
		this.Password = password;
		this.httpClient = new DefaultHttpClient();
		this.p = new HashMap<String, String>();
		p.put("__VIEWSTATE", "dDwtMTIwMTU3OTE3Nzs7Ph6GPnIf6Kt7eQ8W2PWRAAIYLNw/");
		p.put("Button1", "");
		p.put("lbLanguage", "");
		p.put("RadioButtonList1", "学生");
		p.put("TextBox1", userName);
		p.put("TextBox2", password);
		
		
		HttpPost httpPost = new HttpPost("http://jiaowu1.fjut.edu.cn/default2.aspx");
		HttpResponse httpResponse = null;
		// httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);
		// 加入请求参数

			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for (String key : p.keySet()) {
				if (key != null) {
					paramList.add(new BasicNameValuePair(key, p.get(key)));
				}
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"gb2312");
			httpPost.setEntity(entity);
			httpResponse = httpClient.execute(httpPost);

			
			cookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
			cookie = cookie.split(";")[0];
			HttpEntity content = httpResponse.getEntity();
			String html = EntityUtils.toString(content);

			Document doc = Jsoup.parse(html); 
			Elements titles = doc.getElementsByTag("title"); 
			String titleContent = "";
			for (Element title : titles) { 
				  titleContent = title.text(); 

				 }
			content.consumeContent();

			if(titleContent.equals("Object moved")){
				System.out.println("登入成功！！！！！！");
				return true;
				
			}else {
				System.out.println("登入失败！！！！！！");
				return false;
			}
	}
	
	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}

	public Map<String, String> getP() {
		return p;
	}

	public Map<String,String> getinfo() throws ClientProtocolException, IOException{
		String html = "";
		String url_getName = "http://jiaowu1.fjut.edu.cn/xs_main.aspx?xh=" + this.UserName;
		String url = null;
		Map<String,String> info = new HashMap<String, String>();
		HttpGet httpGet_getName = new HttpGet(url_getName);
		HttpResponse httpResponse_getName = httpClient.execute(httpGet_getName);
		HttpEntity content_getName = httpResponse_getName.getEntity();
		html = EntityUtils.toString(content_getName);
		Document doc = Jsoup.parse(html);
		Element xhxm = doc.getElementById("xhxm"); 
		String xm = xhxm.text().replace(this.UserName + " ","").replace("同学", "");
		url = "http://jiaowu1.fjut.edu.cn/xsgrxx.aspx?xh=" + this.UserName + "&xm=" + xm + "&gnmkdm=gnmkdm";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Referer","http://jiaowu1.fjut.edu.cn/xs_main.aspx?xh=3100302414");
		HttpResponse httpResponse_result = httpResponse_getName = httpClient.execute(httpGet);
		HttpEntity content = httpResponse_result.getEntity();
		html = EntityUtils.toString(content);
		doc = Jsoup.parse(html);
		
		System.out.println("姓名：" + xm);
		info.put("xm", xm);
		System.out.println("学号：" + this.UserName);
		info.put("student_id", this.UserName);
		
		Element lbl_xb = doc.getElementById("lbl_xb"); 
		String sex = lbl_xb.text();
		if(sex.equals("男")) sex = "1";
		else if(sex.equals("女")) sex = "0";
		System.out.println("性别：" + lbl_xb.text());
		info.put("lbl_xb", sex);
		Element lbl_csrq = doc.getElementById("lbl_csrq"); 
		System.out.println("出生日期：" + lbl_csrq.text());
		info.put("lbl_csrq", lbl_csrq.text());
		Element lbl_byzx = doc.getElementById("lbl_byzx"); 
		System.out.println("毕业中学：" + lbl_byzx.text());
		info.put("lbl_byzx", lbl_byzx.text());
		Element lbl_mz = doc.getElementById("lbl_mz"); 
		System.out.println("名族：" + lbl_mz.text());
		info.put("lbl_mz", lbl_mz.text());
		Element lbl_zzmm = doc.getElementById("lbl_zzmm"); 
		System.out.println("政治面貌：" + lbl_zzmm.text());
		info.put("lbl_zzmm", lbl_zzmm.text());
		Element lbl_sfzh = doc.getElementById("lbl_sfzh"); 
		System.out.println("身份证号码：" + lbl_sfzh.text());
		info.put("lbl_sfzh", lbl_sfzh.text());
		Element lbl_xy = doc.getElementById("lbl_xy"); 
		System.out.println("学院：" + lbl_xy.text());
		info.put("lbl_xy", lbl_xy.text());
		Element lbl_zymc = doc.getElementById("lbl_zymc"); 
		System.out.println("专业：" + lbl_zymc.text());
		info.put("lbl_zymc", lbl_zymc.text());
		Element lbl_xzb = doc.getElementById("lbl_xzb"); 
		System.out.println("班级：" + lbl_xzb.text());
		info.put("lbl_xzb", lbl_xzb.text());
		Element lbl_dqszj = doc.getElementById("lbl_dqszj"); 
		System.out.println("入学年份：" + lbl_dqszj.text());
		info.put("lbl_dqszj", lbl_dqszj.text());
		
		
		content.consumeContent();
		return info;
	}
}