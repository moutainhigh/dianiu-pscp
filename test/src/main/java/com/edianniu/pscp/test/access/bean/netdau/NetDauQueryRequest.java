package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 心跳请求
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:30:06 
 * @version V1.0
 */
public class NetDauQueryRequest implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private CommonDo common;
	private NetQueryDo net_query;
	public CommonDo getCommon() {
		return common;
	}
	public void setCommon(CommonDo common) {
		this.common = common;
	}
	public NetQueryDo getNet_query() {
		return net_query;
	}

	public void setNet_query(NetQueryDo net_query) {
		this.net_query = net_query;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	
}
