package com.edianniu.pscp.cs.bean.request.firefightingequipment;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 消防设施详情
 * @author zhoujianjian
 * @date 2017年11月1日 下午9:20:20
 */
@JSONMessage(messageCode = 1002137)
public class DetailsRequest extends TerminalRequest{
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}

}
