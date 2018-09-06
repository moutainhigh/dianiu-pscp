package com.edianniu.pscp.cs.bean.request.power;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 电量分布
 * @author zhoujianjian
 * @date 2017年12月8日 上午10:21:18
 */
@JSONMessage(messageCode = 1002184)
public class PowerDistributeRequest extends TerminalRequest{
	
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
}
