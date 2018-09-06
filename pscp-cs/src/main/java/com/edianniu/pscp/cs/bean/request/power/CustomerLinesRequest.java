package com.edianniu.pscp.cs.bean.request.power;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 客户线路列表
 * @author zhoujianjian
 * @date 2017年12月7日 下午2:47:50
 */
@JSONMessage(messageCode = 1002190)
public class CustomerLinesRequest extends TerminalRequest{

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
}
