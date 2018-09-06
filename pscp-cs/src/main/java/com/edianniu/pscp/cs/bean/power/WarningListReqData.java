package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

import com.edianniu.pscp.cs.commons.Constants;

/**
 * 报警列表
 * @author zhoujianjian
 * @date 2017年12月11日 下午4:23:18
 */
public class WarningListReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	private int offset;
	private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
	
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

	
}
