package com.edianniu.pscp.cs.bean.request.power;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 用电负荷
 * @author zhoujianjian
 * @date 2017年12月7日 下午6:54:49
 */
@JSONMessage(messageCode = 1002182)
public class PowerLoadRequest extends TerminalRequest{

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
}
