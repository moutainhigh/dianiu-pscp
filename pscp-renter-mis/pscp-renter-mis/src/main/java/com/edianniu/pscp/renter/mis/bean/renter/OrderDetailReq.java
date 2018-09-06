package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

/**
 * 租客账单详情
 * @author zhoujianjian
 * @date 2018年4月8日 下午5:54:35
 */
public class OrderDetailReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Long id;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
