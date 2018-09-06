package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;


import com.edianniu.pscp.mis.bean.Result;

public class PreWithdrawalsResult extends Result implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String amount;

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
