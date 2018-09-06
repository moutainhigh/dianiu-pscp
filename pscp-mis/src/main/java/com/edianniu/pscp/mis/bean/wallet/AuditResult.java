package com.edianniu.pscp.mis.bean.wallet;

public class AuditResult {
	
	private boolean couponFlag ;
	
	private boolean auditFlag;
	
	private String couponFee;

	

	public boolean isCouponFlag() {
		return couponFlag;
	}

	public void setCouponFlag(boolean couponFlag) {
		this.couponFlag = couponFlag;
	}

	public String getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(String couponFee) {
		this.couponFee = couponFee;
	}

	public boolean isAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(boolean auditFlag) {
		this.auditFlag = auditFlag;
	}
	
	
}
