/**
 * 
 */
package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.AppCheckUpdate;

/**
 * @author cyl
 *
 */
public interface AppCheckUpdateService {
	
	public AppCheckUpdate getAppCheckUpdate(String osType,String appPkg,Long appVer);
}
