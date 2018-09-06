package com.edianniu.pscp.cms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cms.entity.SysUserEntity;
import com.edianniu.pscp.cms.utils.ShiroUtils;

/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午6:08:27 
 * @version V1.0
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
}
