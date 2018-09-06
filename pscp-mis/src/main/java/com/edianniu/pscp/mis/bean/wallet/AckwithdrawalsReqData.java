package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;

public class AckwithdrawalsReqData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private String amount;
	
	private String alipayAccount;
	
    private Integer type;
	
	private Long bankCardId;
	
	private String msgcodeid;
	
	private String msgcode;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Long bankCardId) {
		this.bankCardId = bankCardId;
	}


	public String getMsgcodeid() {
		return msgcodeid;
	}

	public void setMsgcodeid(String msgcodeid) {
		this.msgcodeid = msgcodeid;
	}

	public String getMsgcode() {
		return msgcode;
	}

	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	
	
}
