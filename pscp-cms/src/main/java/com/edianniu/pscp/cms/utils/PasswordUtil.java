package com.edianniu.pscp.cms.utils;

import org.apache.commons.lang3.StringUtils;

public class PasswordUtil {
	public static String encode(String pwd) {
		if (StringUtils.isNotBlank(pwd)) {
			return Md5Utils.MD5(pwd.trim());
		}
		return null;
	}
	public static void main(String args[]){
		System.out.println("value:"+encode("admin"));
		//21232f297a57a5a743894a0e4a801fc3
		//8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918
		
	}
}
