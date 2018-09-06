package com.edianniu.pscp.cs.bean.response.needs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 确认合作
 * @author zhoujianjian
 * 2017年9月15日上午11:55:35
 */
@JSONMessage(messageCode = 2002127)
public class ConfirmCooperationResponse extends BaseResponse{
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
