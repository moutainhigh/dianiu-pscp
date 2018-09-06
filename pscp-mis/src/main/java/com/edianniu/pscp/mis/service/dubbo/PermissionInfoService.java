package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.Result;


/**
 * PermissionInfoService
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0  2018年5月10日 下午5:30:42
 */
public interface PermissionInfoService {
	/**
	 * 权限校验
	 * @param uid
	 * @param action
	 * @return
	 */
	Result authc(Long uid,String action);

}
