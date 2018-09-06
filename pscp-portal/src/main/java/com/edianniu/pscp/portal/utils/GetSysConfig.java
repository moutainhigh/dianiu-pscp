package com.edianniu.pscp.portal.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.edianniu.pscp.portal.service.SysConfigService;

public class GetSysConfig {
	
	public Logger logger = Logger.getLogger(GetSysConfig.class);
	
	@Autowired
	private SysConfigService sysConfigService;
	

	/**
	 * 获得Session有效期
	 */
	public String getValueByKey(){
		try {
			String value = sysConfigService.getValue("SESSION", "1800");
			return value;
		} catch (Exception e) {
			logger.error("SESSION_VALID_TIME get failed");
			return "1800";
		}
	}
	
	//Session有效期
	public final String SESSION_VALID_TIME = getValueByKey();
}
