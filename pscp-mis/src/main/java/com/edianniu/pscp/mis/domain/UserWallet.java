/**
 * 
 */
package com.edianniu.pscp.mis.domain;

import java.io.Serializable;

/**
 * @author cyl
 *
 */
public class UserWallet extends BaseDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long uid;
	private double amount;//余额
	private double freezingAmount;//冻结金额
	
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getFreezingAmount() {
		return freezingAmount;
	}
	public void setFreezingAmount(double freezingAmount) {
		this.freezingAmount = freezingAmount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	

}
