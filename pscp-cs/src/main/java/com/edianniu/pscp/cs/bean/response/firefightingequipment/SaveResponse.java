package com.edianniu.pscp.cs.bean.response.firefightingequipment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房消防设施保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午4:33:55
 */
@JSONMessage(messageCode = 2002140)
public class SaveResponse extends BaseResponse{
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
