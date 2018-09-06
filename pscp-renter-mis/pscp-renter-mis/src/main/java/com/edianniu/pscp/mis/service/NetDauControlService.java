/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月13日 下午4:34:14 
 * @version V1.0
 */
package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.request.NetDauControlRequest;
import com.edianniu.pscp.mis.response.NetDauControlResponse;

import stc.skymobi.remote.annotation.ServiceConfig;

/**
 * @author yanlin.chen
 * @version V1.0
 */
@ServiceConfig(serverName = "pscp-mis",timeout=5000)
public interface NetDauControlService {
	
	@ServiceConfig(serverName = "pscp-mis",timeout=5000)
	public NetDauControlResponse control(NetDauControlRequest netDauControlRequest);
}
