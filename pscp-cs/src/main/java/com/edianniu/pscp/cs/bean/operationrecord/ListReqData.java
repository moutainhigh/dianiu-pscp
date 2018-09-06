package com.edianniu.pscp.cs.bean.operationrecord;

import java.io.Serializable;

import com.edianniu.pscp.cs.commons.Constants;

/**
 * 配电房操作记录列表
 * @author zhoujianjian
 * @date 2017年10月29日 下午8:26:31
 */
public class ListReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	private int offset;
	private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
	private Long roomId;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

}
