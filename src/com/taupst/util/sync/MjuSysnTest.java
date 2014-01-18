package com.taupst.util.sync;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MjuSysnTest implements Sysn {

	private String userName;
	private String password;
	private String txtSecretCode;
	private String cookie;
	private String vs;
	
	private CloseableHttpClient httpClient;
	private CloseableHttpResponse httpResponse;

	public MjuSysnTest(String userName, String password, String txtSecretCode,
			String cookie,String vs) {
		super();
		this.userName = userName;
		this.password = password;
		this.txtSecretCode = txtSecretCode;
		this.cookie = cookie;
		this.vs = vs;

	}

	@Override
	public Map<String, String> login(HttpServletRequest request) throws ClientProtocolException,
			IOException {
		Map<String, String> map = new HashMap<String, String>();
		httpClient = HttpClients.createDefault();
		HttpEntity httpEntity = null;
		String html = null;


		try {
			
				// 登录成功后进入首页
				String url_getName = "http://jwgl.mju.edu.cn/xs_main.aspx?xh="
						+ this.userName;
				HttpGet httpGet_getName = new HttpGet(url_getName);
				httpGet_getName.setHeader("Host", "jwgl.mju.edu.cn");
				httpGet_getName.setHeader("Referer",
						"http://jwgl.mju.edu.cn/default2.aspx");
				httpGet_getName.setHeader("Cookie", this.cookie);
				httpResponse = httpClient.execute(httpGet_getName);
				HttpEntity content_getName = httpResponse.getEntity();
				html = EntityUtils.toString(content_getName);

				Document doc = Jsoup.parse(html);
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
				httpGet_getName.setHeader("Cookie", this.cookie);
				httpResponse = httpClient.execute(httpGet_getName);
				HttpEntity content_userinfo = httpResponse.getEntity();
				html = EntityUtils.toString(content_userinfo);
				// System.out.println(html);

				map.put("isLogined", "true");
				map.put("state", "0");
				map.put("msg", "登录成功");

				doc = Jsoup.parse(html);
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

				if (lbl_zymc == null || lbl_zymc.equals("")) {
					map.put("isLogined", "false");
					map.put("state", "1");
					map.put("msg", "请登录教务系统完成教师评价后在登录!!");
				}

				Element el_lbl_xzb = doc.getElementById("lbl_xzb");
				String lbl_xzb = el_lbl_xzb.html();
				map.put("lbl_xzb", lbl_xzb);
				System.out.println("班级：" + lbl_xzb);

				Element el_lbl_dqszj = doc.getElementById("lbl_dqszj");
				String lbl_dqszj = el_lbl_dqszj.html();
				map.put("lbl_dqszj", lbl_dqszj);
				System.out.println("年级：" + lbl_dqszj);

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
		} catch (Exception e) {
			map.put("isLogined", "false");
			map.put("state", "6");
			map.put("msg", "未知错误！！");
		}

		httpResponse.close();
		httpClient.close();

		return map;
	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		MjuSysnTest f = new MjuSysnTest("120091101201", "hou52346945", "603u",
				"ASP.NET_SessionId=qyork43ybvfczl554j1s5e55","");
//		MjuSysn f = new MjuSysn("120091101201", "hou52346945", "vxuv",
//				"ASP.NET_SessionId=zu2a2u550kqkuo45e353dby3");
//		MjuSysn f = new MjuSysn("120101104109", "350128199011024351", "pqfx",
//				"ASP.NET_SessionId=nu2g0pqhbuauuaazfttbe245");
		Map<String, String> m = f.login(null);
		System.out.println(m);

	}
}