package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

import com.edianniu.pscp.renter.mis.commons.Constants;

/**
 * 租客列表
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:51:13
 */
public class RenterMetersReq implements Serializable{

	private static final long serialVersionUID = 1L;
	// 客户uid
	private Long uid;
	
	private Integer offset = 0;
	
	private Integer limit = Constants.DEFAULT_PAGE_SIZE;
	
	private Long renterId;
	
	public Long getRenterId() {
		return renterId;
	}
	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	

}
