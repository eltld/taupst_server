package com.taupst.util.sync;

public class SysnFac {
	public static Sysn getConn(String school,String userName,String password){
		if (school.equals("00001")) {
			return new FjutSysn(userName, password);
		}else if (school.equals("00002")){
			return new FjnuSysn(userName, password);
		}else{
			return null;
		}
		
	}
}
