package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

import com.edianniu.pscp.renter.mis.commons.Constants;

/**
 * 租客账单
 * @author zhoujianjian
 * @date 2018年4月8日 下午5:54:35
 */
public class OrderListReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Integer offset;
	
	private Long renterId;
	
	private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;

	private Integer payStatus;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

}
