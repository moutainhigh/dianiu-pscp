package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WithdrawalscashPayConfirmReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String opUser;
	private Integer status;
	private String memo;
	private String payTransactionId;//交易号
	private Date payTime;
	public Long getId() {
		return id;
	}
	public String getOpUser() {
		return opUser;
	}
	public Integer getStatus() {
		return status;
	}
	public String getMemo() {
		return memo;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getPayTransactionId() {
		return payTransactionId;
	}
	public void setPayTransactionId(String payTransactionId) {
		this.payTransactionId = payTransactionId;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
