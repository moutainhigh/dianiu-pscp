package com.edianniu.pscp.cs.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Md5Utils {
	private static Logger logger = LoggerFactory.getLogger(Md5Utils.class);

	public static String MD5(String inStr) {
		if (StringUtils.isBlank(inStr)) {
			return "";
		}
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			logger.error("error [{}]!", e);
			return "";
		}
		byte[] x = new byte[0];
		try {
			x = inStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("error [{}]!", e);
			return "";
		}
		byte[] md5Bytes = md5.digest(x);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = md5Bytes[i] & 0xFF;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	public static void main(String args[]){
		System.out.println(Md5Utils.MD5("7809tj1069011705360"));
	}
}
