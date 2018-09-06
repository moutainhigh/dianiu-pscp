package com.edianniu.pscp.cs.bean.response.safetyequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房安全用具新增
 * @author zhoujianjian
 * @date 2017年10月31日 下午1:02:42
 */
@JSONMessage(messageCode =  2002132)
public class SaveResponse extends BaseResponse{
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
