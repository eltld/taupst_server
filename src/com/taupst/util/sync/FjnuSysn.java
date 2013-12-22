package com.taupst.util.sync;

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
public class FjnuSysn implements Sysn {
	private String userName;
	private String password;

	private DefaultHttpClient httpClient;

	public FjnuSysn() {
		super();
	}

	public FjnuSysn(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;

	}

	@Override
	public Map<String, String> login() throws ClientProtocolException,
			IOException {
		this.httpClient = new DefaultHttpClient();

		Map<String, String> loginParams = new HashMap<String, String>();
		Map<String, String> info = new HashMap<String, String>();
		loginParams
				.put("__VIEWSTATE",
						"dDwtNTgxODgzNDk1O3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDQ+Oz47bDx0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cuY2xvc2UoKVw7Oz4+Pjs7Pjs+Pjs+Pjs+YZDt8uVppmy6IujMAtzp1hlsMkQ=");
		loginParams.put("Button1", "");
		loginParams.put("RadioButtonList1", "学生");
		loginParams.put("TextBox1", userName);
		loginParams.put("TextBox2", password);

		HttpPost httpPost = new HttpPost(
				"http://jwgl.fjnu.edu.cn/default5.aspx");
		HttpResponse httpResponse = null;
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

		if (titleContent.equals("Object moved")) {
			System.out.println("登入成功！！！！！！");
			info.put("isLoginSuccess", "true");

			String url_getName = "http://jwgl.fjnu.edu.cn/xs_main.aspx?xh="
					+ this.userName;
			String url = null;
			HttpGet httpGet_getName = new HttpGet(url_getName);
			// httpGet_getName.setHeader("Referer",
			// "http://jwgl.fjnu.edu.cn/default5.aspx");
			HttpResponse httpResponse_getName = httpClient
					.execute(httpGet_getName);
			HttpEntity content_getName = httpResponse_getName.getEntity();
			html = EntityUtils.toString(content_getName);
			doc = Jsoup.parse(html);
			Element xhxm = doc.getElementById("xhxm");
			String xm = xhxm.text().replace(this.userName + " ", "")
					.replace("同学", "");
			url = "http://jwgl.fjnu.edu.cn/xsgrxx.aspx?xh=" + this.userName
					+ "&xm=" + xm + "&gnmkdm=N121501";
			HttpGet httpGet = new HttpGet(url);
			// httpGet.setHeader("Host","jwgl.fjnu.edu.cn");
			httpGet.setHeader("Referer",
					"http://jwgl.fjnu.edu.cn/xs_main.aspx?xh=" + this.userName);
			HttpResponse httpResponse_result = httpResponse_getName = httpClient
					.execute(httpGet);
			HttpEntity content1 = httpResponse_result.getEntity();
			html = EntityUtils.toString(content1);
			System.out.println(html);
			doc = Jsoup.parse(html);

			System.out.println("姓名：" + xm);
			info.put("xm", xm);
			System.out.println("学号：" + this.userName);
			info.put("student_id", this.userName);

			Element XB = doc.getElementById("XB");
			String sex = XB.text();
			System.out.println("性别：" + XB.text());
			info.put("lbl_xb", sex);
			Element lbl_csrq = doc.getElementById("csrq");
			System.out.println("出生日期：" + lbl_csrq.text());
			info.put("lbl_csrq", lbl_csrq.text());
			Element lbl_byzx = doc.getElementById("byzx");
			System.out.println("毕业中学：" + lbl_byzx.text());
			info.put("lbl_byzx", lbl_byzx.text());
			Element lbl_mz = doc.getElementById("mz");
			System.out.println("名族：" + lbl_mz.text());
			info.put("lbl_mz", lbl_mz.text());
			Element lbl_zzmm = doc.getElementById("zzmm");
			System.out.println("政治面貌：" + lbl_zzmm.text());
			info.put("lbl_zzmm", lbl_zzmm.text());
			Element lbl_sfzh = doc.getElementById("sfzh");
			System.out.println("身份证号码：" + lbl_sfzh.text());
			info.put("lbl_sfzh", lbl_sfzh.text());
			Element lbl_xy = doc.getElementById("xy");
			System.out.println("学院：" + lbl_xy.text());
			info.put("lbl_xy", lbl_xy.text());
			Element lbl_zymc = doc.getElementById("zymc");
			System.out.println("专业：" + lbl_zymc.text());
			info.put("lbl_zymc", lbl_zymc.text());
			Element lbl_xzb = doc.getElementById("xzb");
			System.out.println("班级：" + lbl_xzb.text());
			info.put("lbl_xzb", lbl_xzb.text());
			Element lbl_dqszj = doc.getElementById("dqszj");
			System.out.println("入学年份：" + lbl_dqszj.text());
			info.put("lbl_dqszj", lbl_dqszj.text());

			content1.consumeContent();

		} else {
			System.out.println("登入失败！！！！！！");
			info.put("isLoginSuccess", "false");
		}
		return info;
	}

}
