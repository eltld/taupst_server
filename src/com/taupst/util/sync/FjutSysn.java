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

@SuppressWarnings("deprecation")
public class FjutSysn implements Sysn {
	private String userName;
	private String password;
	private String txtSecretCode;
	
	private CloseableHttpClient httpClient;
	private CloseableHttpResponse httpResponse;

	public FjutSysn() {
		super();
	}

	public FjutSysn(String userName, String password, String txtSecretCode) {
		super();
		this.userName = userName;
		this.password = password;
		this.txtSecretCode = txtSecretCode;
	}

	public FjutSysn(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	@Override
	public Map<String, String> login(HttpServletRequest request) throws ClientProtocolException,
			IOException {
		this.httpClient = HttpClientUtil.getHttpClient(request, null);
		Map<String, String> loginParams = new HashMap<String, String>();
		Map<String, String> info = new HashMap<String, String>();
		loginParams.put("__VIEWSTATE",
				"dDwtMTIwMTU3OTE3Nzs7Ph6GPnIf6Kt7eQ8W2PWRAAIYLNw/");
		loginParams.put("Button1", "");
		loginParams.put("lbLanguage", "");
		loginParams.put("RadioButtonList1", "学生");
		loginParams.put("TextBox1", userName);
		loginParams.put("TextBox2", password);
		loginParams.put("TextBox3", txtSecretCode);

		HttpPost httpPost = new HttpPost(
				"http://jiaowu1.fjut.edu.cn/default2.aspx");
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
		httpResponse = httpClient.execute(httpPost);

		HttpEntity content = httpResponse.getEntity();
		String html = EntityUtils.toString(content);

		Document doc = Jsoup.parse(html);
		Elements titles = doc.getElementsByTag("title");
		String titleContent = "";
		for (Element title : titles) {
			titleContent = title.text();

		}
		content.consumeContent();

		try {

			if (titleContent.equals("Object moved")) {
				info.put("isLogined", "true");

				String url_getName = "http://jiaowu1.fjut.edu.cn/xs_main.aspx?xh="
						+ this.userName;
				String url = null;
				HttpGet httpGet_getName = new HttpGet(url_getName);
				CloseableHttpResponse httpResponse_getName = httpClient
						.execute(httpGet_getName);
				HttpEntity content_getName = httpResponse_getName.getEntity();
				html = EntityUtils.toString(content_getName);
				doc = Jsoup.parse(html);
				Element xhxm = doc.getElementById("xhxm");
				String xm = xhxm.text().replace(this.userName + " ", "")
						.replace("同学", "");
				url = "http://jiaowu1.fjut.edu.cn/xsgrxx.aspx?xh="
						+ this.userName + "&xm=" + xm + "&gnmkdm=gnmkdm";
				HttpGet httpGet = new HttpGet(url);
				httpGet.setHeader("Referer",
						"http://jiaowu1.fjut.edu.cn/xs_main.aspx?xh="
								+ this.userName);
				httpResponse = httpResponse_getName = httpClient
						.execute(httpGet);
				HttpEntity content1 = httpResponse.getEntity();
				html = EntityUtils.toString(content1);
				doc = Jsoup.parse(html);

				System.out.println("姓名：" + xm);
				info.put("xm", xm);
				System.out.println("学号：" + this.userName);
				info.put("student_id", this.userName);

				Element lbl_xb = doc.getElementById("lbl_xb");
				String sex = lbl_xb.text();
				System.out.println("性别：" + lbl_xb.text());
				info.put("lbl_xb", sex);
				Element lbl_xy = doc.getElementById("lbl_xy");
				System.out.println("学院：" + lbl_xy.text());
				info.put("lbl_xy", lbl_xy.text());

				Element lbl_zymc = doc.getElementById("lbl_zymc");
				System.out.println("专业：" + lbl_zymc.text());
				info.put("lbl_zymc", lbl_zymc.text());

				String str = lbl_zymc.text();
				if (str == null || str.equals("")) {
					info.put("isLogined", "false");
					info.put("state", "1");
					info.put("msg", "请登录教务系统完成教师评价后在登录!!");
				}

				Element lbl_xzb = doc.getElementById("lbl_xzb");
				System.out.println("班级：" + lbl_xzb.text());
				info.put("lbl_xzb", lbl_xzb.text());
				Element lbl_dqszj = doc.getElementById("lbl_dqszj");
				System.out.println("入学年份：" + lbl_dqszj.text());
				info.put("lbl_dqszj", lbl_dqszj.text());

				content1.consumeContent();

			} else {
				Elements els = doc.select("script");
				Element e = els.get(0);
				String err_html = e.html();
				String[] err = err_html.split(";");
				String err_info = err[0];
				err_info = err_info.substring(err_info.indexOf('\'') + 1,
						err_info.lastIndexOf('\''));
				if (err_info.equals("验证码不正确！！")) {
					info.put("isLogined", "false");
					info.put("state", "3");
					info.put("msg", "验证码不正确！！");
					System.out.println(err_info);
				} else if (err_info.equals("用户名不存在或未按照要求参加教学活动！！")) {
					info.put("isLogined", "false");
					info.put("state", "4");
					info.put("msg", "用户名不存在或未按照要求参加教学活动！！");
					System.out.println(err_info);
				} else if (err_info.equals("密码错误！！")) {
					info.put("isLogined", "false");
					info.put("state", "5");
					info.put("msg", "密码错误！！");
					System.out.println(err_info);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			info.put("isLogined", "false");
			info.put("state", "6");
			info.put("msg", "未知错误!!");
		}
		httpResponse.close();
		httpClient.close();
		return info;
	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		FjutSysn f = new FjutSysn("3100302414", "666666", "pqfx");
		Map<String, String> m = f.login(null);
		System.out.println(m);

	}

}
