package com.edianniu.pscp.cs.bean.response.room;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:10:14
 */
@JSONMessage(messageCode = 2002119)
public class SaveResponse extends BaseResponse{
	public String toString(){
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
