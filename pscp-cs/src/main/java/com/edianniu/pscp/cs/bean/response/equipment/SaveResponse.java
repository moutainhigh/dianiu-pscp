package com.edianniu.pscp.cs.bean.response.equipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房设备新增、编辑
 * @author zhoujianjian
 * 2017年9月28日下午4:21:12
 */
@JSONMessage(messageCode = 2002146)
public class SaveResponse extends BaseResponse{
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
