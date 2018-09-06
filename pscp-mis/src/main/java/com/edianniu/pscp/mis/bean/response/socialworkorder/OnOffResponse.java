package com.edianniu.pscp.mis.bean.response.socialworkorder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.response.BaseResponse;

@JSONMessage(messageCode = 2002044)
public final class OnOffResponse extends BaseResponse {
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
