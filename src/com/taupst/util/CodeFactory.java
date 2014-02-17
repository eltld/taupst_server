package com.taupst.util;

public class CodeFactory {

	public static String getCode(int school) {

		String codeUrl = null;
		switch (school) {
		case 3:
			codeUrl = null;
		case 4:
			codeUrl = "http://jwgl.fjnu.edu.cn/CheckCode.aspx";
			break;
		case 7:
			codeUrl = "http://jiaowu1.fjut.edu.cn/CheckCode.aspx";
			break;
		case 9:
			codeUrl = "http://jwgl.mju.edu.cn/CheckCode.aspx";
			break;
		}
		return codeUrl;
	}

}
