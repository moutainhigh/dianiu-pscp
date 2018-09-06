package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

/**
 * 租客首页
 * @author zhoujianjian
 * @date 2018年4月8日 下午2:48:35
 */
public class HomeReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Long renterId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}
	
	
}
