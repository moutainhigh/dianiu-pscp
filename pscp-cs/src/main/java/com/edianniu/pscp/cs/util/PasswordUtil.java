package com.edianniu.pscp.cs.util;

import org.apache.commons.lang3.StringUtils;

public class PasswordUtil {
	public static String encode(String pwd) {
		if (StringUtils.isNotBlank(pwd)) {
			return Md5Utils.MD5(pwd.trim());
		}
		return null;
	}
}
