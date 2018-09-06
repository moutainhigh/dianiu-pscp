package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;

/**
 * 未缴信息--适用于月缴费租客
 * @author zhoujianjian
 * @date 2018年4月8日 上午9:37:02
 */
public class NopayInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long renterId;
	
	private Integer nopayCount;
	
	private Double nopayCharge;

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public Integer getNopayCount() {
		return nopayCount;
	}

	public void setNopayCount(Integer nopayCount) {
		this.nopayCount = nopayCount;
	}

	public Double getNopayCharge() {
		return nopayCharge;
	}

	public void setNopayCharge(Double nopayCharge) {
		this.nopayCharge = nopayCharge;
	}
	
	

}
