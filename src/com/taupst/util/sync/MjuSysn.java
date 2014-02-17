package com.taupst.util.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.taupst.util.SessionUtil;

public class MjuSysn implements Sysn {

	private String userName;
	private String password;
	private String txtSecretCode;

	private CloseableHttpClient httpClient;
	private CloseableHttpResponse httpResponse;

	public MjuSysn(String userName, String password, String txtSecretCode) {
		super();
		this.userName = userName;
		this.password = password;
		this.txtSecretCode = txtSecretCode;
	}

	@Override
	public Map<String, String> login(HttpServletRequest request)
			throws ClientProtocolException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		
		httpClient = HttpClientUtil.getHttpClient(request, null);
		HttpEntity httpEntity = null;
		String html = null;

		// 动态获取__VIEWSTATE的值
		HttpGet httpGet = new HttpGet("http://jwgl.mju.edu.cn/default2.aspx");
		httpGet.setHeader("Host", "jwgl.mju.edu.cn");

		httpResponse = httpClient.execute(httpGet);
		httpEntity = httpResponse.getEntity();
		html = EntityUtils.toString(httpEntity);

		Document d = Jsoup.parse(html);
		Elements es = d.select("input[name=__VIEWSTATE]");
		String __VIEWSTATE = es.val();

		// 模拟登陆开始,构造登陆的请求url
		Map<String, String> loginParams = new HashMap<String, String>();

		loginParams.put("__VIEWSTATE", __VIEWSTATE);// 要动态获取
		loginParams.put("TextBox2", password);
		loginParams.put("txtUserName", userName);
		loginParams.put("RadioButtonList1", "学生");
		loginParams.put("txtSecretCode", txtSecretCode);// 验证码
		loginParams.put("lbLanguage", "");
		loginParams.put("Button1", "");
		HttpPost httpPost = new HttpPost("http://jwgl.mju.edu.cn/default2.aspx");

		// httpPost.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);
		// 加入请求参数

		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		for (String key : loginParams.keySet()) {
			if (key != null) {
				paramList
						.add(new BasicNameValuePair(key, loginParams.get(key)));
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,
				"gb2312");
		httpPost.setEntity(entity);
		httpPost.setHeader("Referer", "http://jwgl.mju.edu.cn/");
		httpPost.setHeader("Host", "jwgl.mju.edu.cn");

		httpResponse = httpClient.execute(httpPost);

		httpEntity = httpResponse.getEntity();
		html = EntityUtils.toString(httpEntity);
		// System.out.println(html);
		// 登陆完成，判断登录是否成功
		Document doc = Jsoup.parse(html);
		Elements title = doc.getElementsByTag("title");
		Element el_title = title.get(0);
		String str_title = el_title.html();

		try {
			if (str_title.equals("Object moved")) {
				// 登录成功后进入首页
				String url_getName = "http://jwgl.mju.edu.cn/xs_main.aspx?xh="
						+ this.userName;
				HttpGet httpGet_getName = new HttpGet(url_getName);
				httpGet_getName.setHeader("Host", "jwgl.mju.edu.cn");
				httpGet_getName.setHeader("Referer",
						"http://jwgl.mju.edu.cn/default2.aspx");
				httpResponse = httpClient.execute(httpGet_getName);
				HttpEntity content_getName = httpResponse.getEntity();
				html = EntityUtils.toString(content_getName);

				doc = Jsoup.parse(html);
				Element el_xhxm = doc.getElementById("xhxm");
				String xhxm = el_xhxm.html();
				xhxm = xhxm.substring(xhxm.lastIndexOf(' ') + 1);
				xhxm = xhxm.replace("同学", "");
				// 进入个人信息页面
				String url_userinfo = "http://jwgl.mju.edu.cn/xsgrxx.aspx?xh="
						+ this.userName + "&xm=" + xhxm + "&gnmkdm=N121501";
				httpGet_getName = new HttpGet(url_userinfo);
				httpGet_getName.setHeader("Host", "jwgl.mju.edu.cn");
				httpGet_getName.setHeader("Referer",
						"http://jwgl.mju.edu.cn/xs_main.aspx?xh="
								+ this.userName + "");
				httpResponse = httpClient.execute(httpGet_getName);
				HttpEntity content_userinfo = httpResponse.getEntity();
				html = EntityUtils.toString(content_userinfo);
				// System.out.println(html);

				doc = Jsoup.parse(html);
				
				// 获取提示信息，判断是否完成教师评价
				Elements es_tmp = doc.select("script");
				String err_msg = es_tmp.html();
				if(err_msg != null && !err_msg.equals("")){
					String[] err_str = err_msg.split(";");
					if(err_str.length != 0){
						err_msg = err_str[0];
						int begin_index = err_msg.indexOf("\'") ;
						int end_index = err_msg.lastIndexOf("\'");
						if(begin_index != -1 && end_index != -1){
							err_msg = err_msg.substring(begin_index+1, end_index-1);
						}
					}
				}
				
				// 未完成教师评价
				if (err_msg
						.equals("你还没有进行本学期的教学质量评价,在本系统的“教学质量评价”栏中完成评价工作后，才能进入系统")) {
					map.put("isLogined", "false");
					map.put("state", "1");
					map.put("msg", "请登录教务系统完成教师评价后在登录!!");
					map.remove("student_id");
					map.remove("xm");
				} else {
					// 已完成教师评价
					map.put("isLogined", "true");
					map.put("state", "0");
					map.put("msg", "登录成功");

					// 获取用户信息
					map.put("student_id", this.userName);
					System.out.println("学号：" + this.userName);

					map.put("xm", xhxm);
					System.out.println("姓名：" + xhxm);

					Elements lbl_xb = doc.select("#XB > option[selected]");

					String sex = lbl_xb.attr("value");
					map.put("lbl_xb", sex);
					System.out.println("性别：" + sex);

					Element el_lbl_xy = doc.getElementById("lbl_xy");
					String lbl_xy = el_lbl_xy.html();
					map.put("lbl_xy", lbl_xy);
					System.out.println("学院：" + lbl_xy);

					Element el_lbl_zymc = doc.getElementById("lbl_zymc");
					String lbl_zymc = el_lbl_zymc.html();
					map.put("lbl_zymc", lbl_zymc);
					System.out.println("专业：" + lbl_zymc);

					Element el_lbl_xzb = doc.getElementById("lbl_xzb");
					String lbl_xzb = el_lbl_xzb.html();
					map.put("lbl_xzb", lbl_xzb);
					System.out.println("班级：" + lbl_xzb);

					Element el_lbl_dqszj = doc.getElementById("lbl_dqszj");
					String lbl_dqszj = el_lbl_dqszj.html();
					map.put("lbl_dqszj", lbl_dqszj);
					System.out.println("年级：" + lbl_dqszj);
				}

			} else {
				Elements els = doc.select("script");
				Element e = els.get(els.size() - 1);
				String err_html = e.html();
				String[] err = err_html.split(";");
				String err_info = err[0];
				err_info = err_info.substring(err_info.indexOf('\'') + 1,
						err_info.lastIndexOf('\''));
				if (err_info.equals("验证码不正确！！")) {
					map.put("isLogined", "false");
					map.put("state", "3");
					map.put("msg", "验证码不正确！！");
					System.out.println(err_info);
				} else if (err_info.equals("用户名不存在或未按照要求参加教学活动！！")) {
					map.put("isLogined", "false");
					map.put("state", "4");
					map.put("msg", "用户名不存在或未按照要求参加教学活动！！");
					System.out.println(err_info);
				} else if (err_info.equals("密码错误！！")) {
					map.put("isLogined", "false");
					map.put("state", "5");
					map.put("msg", "密码错误！！");
					System.out.println(err_info);
				}
			}
		} catch (Exception e) {
			map.put("isLogined", "false");
			map.put("state", "6");
			map.put("msg", "未知错误！！");
		}

		httpResponse.close();
		httpClient.close();
		SessionUtil.removeAttr(request, "mHttpClient");
		return map;
	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		//MjuSysn f = new MjuSysn("120091101201", "hou52346945", "603u");
		// MjuSysn f = new MjuSysn("120091101201", "hou52346945", "vxuv",
		// "ASP.NET_SessionId=zu2a2u550kqkuo45e353dby3");
		// MjuSysn f = new MjuSysn("120101104109", "350128199011024351", "pqfx",
		// "ASP.NET_SessionId=nu2g0pqhbuauuaazfttbe245");
		// Map<String, String> m = f.login();
		// System.out.println(m);

	}
}