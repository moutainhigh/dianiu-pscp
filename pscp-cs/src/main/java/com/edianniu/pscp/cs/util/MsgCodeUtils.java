package com.edianniu.pscp.cs.util;

import java.util.Random;

public class MsgCodeUtils {
	public static String getCode() {
		String code = "";

		Random random = new Random();

		code = "";
		for (int i = 0; i < 5; i++) {
			String rand = String.valueOf(random.nextInt(10));
			code = code + rand;
		}
		return code;
	}
}
