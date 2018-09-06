package com.edianniu.pscp.cs.bean.request.power;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 电费明细
 * @author zhoujianjian
 * @date 2017年12月8日 下午6:30:47
 */
@JSONMessage(messageCode = 1002187)
public class ChargeDetailRequest extends TerminalRequest{
	
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
