package com.edianniu.pscp.cs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

public class MethodUtils {
	private static Logger logger = LoggerFactory.getLogger(MethodUtils.class);
	public static String content2key(byte[] content) {
		try {
			MessageDigest md5inst = MessageDigest.getInstance("MD5");
			md5inst.update(content);
			byte[] digest = md5inst.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", Byte.valueOf(b)));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String list2Line(List<String> list, String sp) {
		Preconditions.checkArgument(list != null && list.size() > 0);
		Preconditions.checkArgument(sp != null && sp.length() > 0);

		Joiner joiner = Joiner.on(sp).skipNulls();
		return joiner.join(list);

	}
	
}
