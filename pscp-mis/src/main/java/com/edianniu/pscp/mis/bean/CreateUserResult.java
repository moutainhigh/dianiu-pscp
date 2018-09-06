package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

public class CreateUserResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean isCoupon;//是否有优惠券
	private String couponFee;//具体优惠券信息
	private Long uid;
	
	public boolean isCoupon() {
		return isCoupon;
	}
	public void setCoupon(boolean isCoupon) {
		this.isCoupon = isCoupon;
	}
	public String getCouponFee() {
		return couponFee;
	}
	public void setCouponFee(String couponFee) {
		this.couponFee = couponFee;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
}
