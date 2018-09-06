package com.edianniu.pscp.renter.mis.util;

import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConfigFileReader {
	private static final Logger log = Logger.getLogger(ConfigFileReader.class);
	static Properties p = new Properties();

	static {
		try {
			InputStream ins = ConfigFileReader.class.getClassLoader()
					.getResourceAsStream("skyaaa4.properties");

			p.load(ins);
		} catch (Exception e) {
			log.error("Read ConfigFile fail");
		}
	}
}
