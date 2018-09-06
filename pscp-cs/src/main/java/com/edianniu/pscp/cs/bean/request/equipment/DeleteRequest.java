package com.edianniu.pscp.cs.bean.request.equipment;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房设备删除
 * @author zhoujianjian
 * 2017年9月29日下午10:32:18
 */
@JSONMessage(messageCode = 1002148)
public class DeleteRequest extends TerminalRequest{
	
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
