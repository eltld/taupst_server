package com.taupst.util.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
	private CloseableHttpClient httpClient;

	public FjutSysn() {
		super();
	}

	public FjutSysn(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	@Override
	public Map<String, String> login() throws ClientProtocolException,
			IOException {
		this.httpClient = HttpClients.createDefault();
		Map<String, String> loginParams = new HashMap<String, String>();
		Map<String, String> info = new HashMap<String, String>();
		loginParams.put("__VIEWSTATE",
				"dDwtMTIwMTU3OTE3Nzs7Ph6GPnIf6Kt7eQ8W2PWRAAIYLNw/");
		loginParams.put("Button1", "");
		loginParams.put("lbLanguage", "");
		loginParams.put("RadioButtonList1", "学生");
		loginParams.put("TextBox1", userName);
		loginParams.put("TextBox2", password);

		HttpPost httpPost = new HttpPost(
				"http://jiaowu1.fjut.edu.cn/default2.aspx");
		CloseableHttpResponse httpResponse = null;
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
			url = "http://jiaowu1.fjut.edu.cn/xsgrxx.aspx?xh=" + this.userName
					+ "&xm=" + xm + "&gnmkdm=gnmkdm";
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Referer",
					"http://jiaowu1.fjut.edu.cn/xs_main.aspx?xh="
							+ this.userName);
			CloseableHttpResponse httpResponse_result = httpResponse_getName = httpClient
					.execute(httpGet);
			HttpEntity content1 = httpResponse_result.getEntity();
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

			content1.consumeContent();

		} else {
			System.out.println("登入失败！！！！！！");
			info.put("isLoginSuccess", "false");
		}
		return info;
	}

}
