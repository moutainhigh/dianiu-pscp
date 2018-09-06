/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.log.NetQueryInfo;
import com.edianniu.pscp.mis.bean.request.NetDauRequest;

/**
 * netdau query
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19 
 * @version V1.0
 */
@JSONMessage(messageCode = 1003002)
public final class NetDauQueryRequest extends NetDauRequest {
	private NetQueryInfo net_query;
	public NetQueryInfo getNet_query() {
		return net_query;
	}
	public void setNet_query(NetQueryInfo net_query) {
		this.net_query = net_query;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
