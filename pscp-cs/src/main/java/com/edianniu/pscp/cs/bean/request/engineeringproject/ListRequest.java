package com.edianniu.pscp.cs.bean.request.engineeringproject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 项目列表
 * @author zhoujianjian
 * 2017年9月19日下午2:24:14
 */
@JSONMessage(messageCode = 1002153)
public class ListRequest extends TerminalRequest{
	
	//项目状态：  "progressing"：进行中         "finished"：已结束
	private String status;
	
	private Integer offset;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
