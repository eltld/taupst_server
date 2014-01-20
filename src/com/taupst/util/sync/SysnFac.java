package com.taupst.util.sync;

public class SysnFac {
	/**
	 * 
	 * @param school
	 * 			00007 -> 福建工程学院<br>
	 * 			00004 -> 福建师范大学<br>
	 * 			00003 -> 福州大学<br>
	 * 			00009 -> 闽江学院<br>
	 * @param userName
	 * @param password
	 * @param txtSecretCode
	 * @param cookie
	 * @return
	 */
	public static Sysn getConn(String school, String userName, String password,
			String txtSecretCode) {
		if (school.equals("00007")) {
			return new FjutSysn(userName, password,txtSecretCode);
		} else if (school.equals("00004")) {
			return new FjnuSysn(userName, password,txtSecretCode);
		} else if (school.equals("00003")) {
			return new FjuSysn(userName, password,txtSecretCode);
		}else if (school.equals("00009")) {
			return new MjuSysn(userName, password,txtSecretCode);
		} else {
			return null;
		}

	}
}
