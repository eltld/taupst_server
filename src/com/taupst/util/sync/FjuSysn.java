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
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FjuSysn implements Sysn {

	private String userName;
	private String password;
	//private String txtSecretCode;
	//private String cookie;
	
	private CloseableHttpClient httpClient;
	private CloseableHttpResponse httpResponse;
	
	public FjuSysn() {
		super();
	}

	public FjuSysn(String userName, String password, String txtSecretCode,
			String cookie) {
		super();
		this.userName = userName;
		this.password = password;
		//this.txtSecretCode = txtSecretCode;
		//this.cookie = cookie;
	}

	public FjuSysn(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;

	}

	@Override
	public Map<String, String> login(HttpServletRequest request) throws ClientProtocolException,
			IOException {
		Map<String, String> map = new HashMap<String, String>();

		// HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// CloseableHttpClient httpClient =httpClientBuilder.build(); 这种方式也可以。
		this.httpClient = HttpClients.createDefault();

		Map<String, String> loginParams = new HashMap<String, String>();

		loginParams.put("muser", this.userName);
		loginParams.put("passwd", this.password);
		loginParams.put("x", "30");
		loginParams.put("y", "20");
		HttpPost httpPost = new HttpPost("http://59.77.226.32/logincheck.asp");
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
		httpPost.setHeader("Referer", "http://jwch.fzu.edu.cn/");
		httpPost.setHeader("Host", "59.77.226.32");
		httpResponse = httpClient.execute(httpPost);

		HttpEntity httpEntity = httpResponse.getEntity();
		String html = EntityUtils.toString(httpEntity);
		// System.out.println(html);

		Document doc = Jsoup.parse(html);

		Elements els = doc.select("a");
		String url_get = els.attr("href");
		// System.out.println(url_get);

		doc = Jsoup.parse(html);

		Elements titles = doc.getElementsByTag("title");
		String titleContent = "";
		for (Element title : titles) {
			titleContent = title.text();

		}

		// 判断账号是否错误，
		// titleContent.equals("Object moved")==true 表示账号正确，密码待定
		// titleContent.equals("Object moved")==false 表示账号错误，
		if (titleContent.equals("Object moved")) {

			HttpGet httpGet = new HttpGet(url_get);
			httpGet.setHeader("Referer", "http://jwch.fzu.edu.cn/");
			httpGet.setHeader("Host", "59.77.226.34");
			httpResponse = httpClient.execute(httpGet);
			// httpResponse.setHeader("Content-type",
			// "text/html;charset=gb2312");
			httpEntity = httpResponse.getEntity();
			html = EntityUtils.toString(httpEntity);

			// System.out.println(new
			// String(html.getBytes("ISO8859-1"),"GB2312"));

			HttpGet httpGet1 = new HttpGet(
					"http://59.77.226.34/xszy/jcxx/xsxx/grxx_view.asp");// 获取用户信息URL

			httpResponse = httpClient.execute(httpGet1);
			httpEntity = httpResponse.getEntity();
			html = EntityUtils.toString(httpEntity);
			// System.out.println(new String(html.getBytes("ISO8859-1"),
			// "GB2312"));
			httpResponse.close();
			httpClient.close();
			html = new String(html.getBytes("ISO8859-1"), "GB2312");

			doc = Jsoup.parse(html);

			Elements es = doc.select("title");
			// 判断密码是否错误，es.hasText()==true则密码正确，es.hasText()==false则密码错误
			if (es.hasText()) {
				map.put("isLogined", "true");

				els = doc.select("table");
				// 获取第三个table
				Element el = els.get(2);

				els = el.select("tr");
				Element elTmp = null;
				Elements elTmps = null;

				// 获取第一个tr
				elTmp = els.get(0);
				elTmps = elTmp.children();
				// 学号
				elTmp = elTmps.get(1);
				String student_id = elTmp.html();
				map.put("student_id", student_id);
				System.out.println("学号:" + student_id);
				
				if(student_id == null || student_id.equals("")){
					map.put("isLogined", "false");
					map.put("state", "1");
					map.put("msg", "请登录教务系统完成教师评价后在登录!!");
				}
				
				// 姓名
				elTmp = elTmps.get(3);
				String xm = elTmp.html();
				map.put("xm", xm);
				System.out.println("姓名:" + xm);
				// 获取第二个tr
				elTmp = els.get(1);
				elTmps = elTmp.children();
				// 性别
				elTmp = elTmps.get(3);
				String lbl_xb = elTmp.html();
				map.put("lbl_xb", lbl_xb);
				System.out.println("性别:" + lbl_xb);

				// 获取第九个tr
				elTmp = els.get(8);
				elTmps = elTmp.children();
				// 学院
				elTmp = elTmps.get(1);
				String lbl_xy = elTmp.html();
				map.put("lbl_xy", lbl_xy);
				System.out.println("学院:" + lbl_xy);
				// 专业
				elTmp = elTmps.get(3);
				String lbl_zymc = elTmp.html();
				map.put("lbl_zymc", lbl_zymc);
				System.out.println("专业:" + lbl_zymc);

				// 获取第十个tr
				elTmp = els.get(9);
				elTmps = elTmp.children();
				// 年级
				elTmp = elTmps.get(1);
				String lbl_dqszj = elTmp.html();
				map.put("lbl_dqszj", lbl_dqszj);
				System.out.println("年级:" + lbl_dqszj);
				// 班级
				elTmp = elTmps.get(3);
				String lbl_xzb = elTmp.html();
				map.put("lbl_xzb", lbl_xzb);
				System.out.println("班级:" + lbl_xzb);
			} else {
				map.put("isLogined", "false");
				map.put("state", "5");
				map.put("msg", "密码错误！！");
			}
		} else {
			map.put("isLogined", "false");
			map.put("state", "4");
			map.put("msg", "用户名不存在或未按照要求参加教学活动！！");
		}
		httpResponse.close();
		httpClient.close();
		return map;
	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		FjuSysn f = new FjuSysn("021000804", "159753");
		Map<String, String> m = f.login(null);
		System.out.println(m);
	}
}
