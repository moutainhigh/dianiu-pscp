package com.edianniu.pscp.cs.bean.response.dutylog;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房客户值班日志新增、编辑
 * @author zhoujianjian
 * @date 2017年10月30日 下午1:33:31
 */
@JSONMessage(messageCode =  2002141)
public class SaveResponse extends BaseResponse{
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
