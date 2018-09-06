package com.edianniu.pscp.cs.bean.response.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 
 * @author zhoujianjian
 * 2017年9月13日下午4:49:29
 */
@JSONMessage(messageCode = 2002122)
public class SaveResponse extends BaseResponse{
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
