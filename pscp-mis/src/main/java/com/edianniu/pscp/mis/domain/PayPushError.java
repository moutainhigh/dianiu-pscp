package com.edianniu.pscp.mis.domain;

import java.io.Serializable;

/**
 * @author AbnerElk
 */
public class PayPushError extends BaseDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String push;
	private String errMsg;
	private int  payType;

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPush() {
		return push;
	}

	public void setPush(String push) {
		this.push = push;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
