package com.edianniu.pscp.cs.bean.request.needs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求列表
 * @author zhoujianjian
 * 2017年9月17日下午7:43:54
 */
@JSONMessage(messageCode = 1002124)
public class ListRequest extends TerminalRequest{

	//需求状态："verifying"（审核中）  "responding"（响应中）  ''quoting"（报价中）   "finished"（已结束）
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
