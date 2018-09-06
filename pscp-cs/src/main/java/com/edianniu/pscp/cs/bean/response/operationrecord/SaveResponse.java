package com.edianniu.pscp.cs.bean.response.operationrecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房操作记录新增和编辑
 * @author zhoujianjian
 * @date 2017年10月18日 下午4:55:05
 */
@JSONMessage(messageCode =  2002151)
public class SaveResponse extends BaseResponse{
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
