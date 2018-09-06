package com.edianniu.pscp.cs.bean.response.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求取消
 * @author zhoujianjian
 * 2017年9月14日下午10:39:11
 */
@JSONMessage(messageCode = 2002126)
public class CancelResponse extends BaseResponse{
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
