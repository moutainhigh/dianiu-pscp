/**
 * 
 */
package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author cyl
 *
 */
public class DepositInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int needDeposit;//0 否，1是
	
	private String deposit;//保证金额度
	
	private String rechargeAmount;//建议充值金额
	@JSONField(serialize = false)
	private String availableAmount;//当前用户帐户可用余额
	
	public String getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public int getNeedDeposit() {
		return needDeposit;
	}
	public void setNeedDeposit(int needDeposit) {
		this.needDeposit = needDeposit;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
