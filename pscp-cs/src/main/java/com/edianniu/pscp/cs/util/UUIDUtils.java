package com.edianniu.pscp.cs.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class UUIDUtils {

	private final static long MIGIC_NUMBER = 1230719496953L; 
	private static AtomicInteger uuid = new AtomicInteger(0);
	
	public static String generateUUID(String systemId) {
		int no = uuid.incrementAndGet();
		while (no >= 100) {
			uuid.compareAndSet(no, 0);
			no = uuid.incrementAndGet();
		}
		return systemId + (System.currentTimeMillis() - MIGIC_NUMBER) + no;
	}

	public static String generateUUID() {
		String source = UUID.randomUUID().toString();
		return source.replaceAll("-", "");
	}
}
