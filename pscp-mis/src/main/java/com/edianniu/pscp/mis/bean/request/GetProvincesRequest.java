package com.edianniu.pscp.mis.bean.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 1002062)
public class GetProvincesRequest  extends TerminalRequest {
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
}
