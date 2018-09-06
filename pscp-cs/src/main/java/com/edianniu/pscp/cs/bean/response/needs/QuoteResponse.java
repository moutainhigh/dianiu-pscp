package com.edianniu.pscp.cs.bean.response.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求询价
 * @author zhoujianjian
 * 2017年9月14日下午11:57:45
 */
@JSONMessage(messageCode = 2002127)
public class QuoteResponse extends BaseResponse{
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
