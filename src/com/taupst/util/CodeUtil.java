package com.taupst.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CodeUtil {

	/*
	 * 从网络上下载图片，保存到本地，然后再从本地读出图片（也将他保存到内存中）返回
	 */
	public static boolean downloadImage(String codeUrl, String fileName,
			HttpServletRequest request) {
		boolean flag = false;

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		File file = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			file = new File(request.getRealPath("/image/code") + "/" + fileName);
			//file = new File("http://taupst.duapp.com/image/code/" + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			HttpGet get = new HttpGet(codeUrl);
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(get);
			
//			Header[] cookies = response.getHeaders("Set-Cookie");
//			String cookie = cookies[0].getValue();
//			cookie = cookie.substring(0, cookie.indexOf(";"));
			
			request.getSession().setAttribute("mHttpClient", httpClient);
			
			is = response.getEntity().getContent();
			/* 把图片加到内存的缓存中 */
			bis = new BufferedInputStream(is);

			os = new FileOutputStream(file);
			bos = new BufferedOutputStream(os);

			byte[] b = new byte[1024];
			int len = 0;
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/*
	 * 将图片写到文件里面去 以后可能改成保存到sdcard中去，毕竟手机本身存储器容量有限
	 */
	private void writeImageToFile(String fileName, InputStream is) {
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = null;
		try {
			/*
			 * OutputStream os = context.openFileOutput(fileName,
			 * Context.MODE_PRIVATE); bos = new BufferedOutputStream(os); byte[]
			 * buffer = new byte[1024]; int length; while ((length =
			 * bis.read(buffer)) != -1) { bos.write(buffer, 0, length); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* 最后的清理工作 */
		finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.flush();
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static CodeUtil getInstance() {
		return new CodeUtil();
	}

	public static void main(String[] args) {
//		CodeUtil c = new CodeUtil();
//		c.downloadImage("http://jwgl.mju.edu.cn/CheckCode.aspx","1111111.jpeg",null);
	}
}
