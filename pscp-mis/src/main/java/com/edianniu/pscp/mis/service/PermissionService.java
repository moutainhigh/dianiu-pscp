/**
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0 2018年5月11日 下午4:08:53 
 */
package com.edianniu.pscp.mis.service;

import java.util.Set;

/**
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0  2018年5月11日 下午4:08:53 
 */
public interface PermissionService {
  Set<String> getPermissions(Long uid);
}
