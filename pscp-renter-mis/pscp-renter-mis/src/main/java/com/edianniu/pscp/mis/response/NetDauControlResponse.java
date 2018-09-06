package com.edianniu.pscp.mis.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * netdau 透传命令相应
 * 
 * @author yanlin.chen
 * @version V1.0
 */
@JSONMessage(messageCode = 2003006)
public final class NetDauControlResponse extends BaseResponse {
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
