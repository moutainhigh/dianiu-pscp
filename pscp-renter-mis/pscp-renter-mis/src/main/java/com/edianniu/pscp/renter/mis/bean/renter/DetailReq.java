package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

/**
 * 门户租客管理--租客详情
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:54:43
 */
public class DetailReq implements Serializable{

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
