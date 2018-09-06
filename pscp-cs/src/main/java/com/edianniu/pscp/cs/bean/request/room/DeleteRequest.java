package com.edianniu.pscp.cs.bean.request.room;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:10:07
 */
@JSONMessage(messageCode = 1002121)
public class DeleteRequest extends TerminalRequest {
	
	//配电房主键
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	 public String toString() {
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	 }

}
