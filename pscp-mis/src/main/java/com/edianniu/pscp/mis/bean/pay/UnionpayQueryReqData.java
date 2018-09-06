package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

public class UnionpayQueryReqData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String oederId;
	
	private String txmTime;
	
	private String queryId;
	
	private String encoding;
	
	private String signMethod;
	
	private String txnType;
	
	private String txnSubType;
	
	private String bizType;
	
	private String merId;
	
	private String accessType;

	public String getOederId() {
		return oederId;
	}

	public void setOederId(String oederId) {
		this.oederId = oederId;
	}

	public String getTxmTime() {
		return txmTime;
	}

	public void setTxmTime(String txmTime) {
		this.txmTime = txmTime;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	
	
}
