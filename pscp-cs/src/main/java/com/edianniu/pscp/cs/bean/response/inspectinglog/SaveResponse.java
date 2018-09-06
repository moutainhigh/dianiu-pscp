package com.edianniu.pscp.cs.bean.response.inspectinglog;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 客户设备巡检日志保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:46:22
 */
@JSONMessage(messageCode = 2002135)
public class SaveResponse extends BaseResponse{
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
