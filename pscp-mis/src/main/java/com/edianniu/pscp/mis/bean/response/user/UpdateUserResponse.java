package com.edianniu.pscp.mis.bean.response.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002012)
public final class UpdateUserResponse extends BaseResponse {

	
	
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
