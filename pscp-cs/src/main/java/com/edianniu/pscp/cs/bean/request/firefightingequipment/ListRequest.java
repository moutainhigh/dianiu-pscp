package com.edianniu.pscp.cs.bean.request.firefightingequipment;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 消防设施
 * @author zhoujianjian
 * @date 2017年11月1日 下午6:51:45
 */
@JSONMessage(messageCode = 1002139)
public class ListRequest extends TerminalRequest{

	//配电房ID，-1则获取所有配电房消防设施
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

	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
