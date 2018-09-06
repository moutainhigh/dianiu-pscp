package com.edianniu.pscp.cs.bean.request.operationrecord;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房操作记录列表
 * @author zhoujianjian
 * @date 2017年10月29日 下午8:15:08
 */
@JSONMessage(messageCode = 1002149)
public class ListRequest extends TerminalRequest{

	//配电房ID，-1则获取所有配电房设备
	private Long roomId;
	
	private Integer offset;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
