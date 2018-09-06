package com.edianniu.pscp.mis.bean.pay;

import com.edianniu.pscp.mis.bean.Result;

import java.io.Serializable;
import java.util.List;

public class GoonPayResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String walletAmount;
	private List<PayList> payList;
	public String getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(String walletAmount) {
		this.walletAmount = walletAmount;
	}

	public List<PayList> getPayList() {
		return payList;
	}

	public void setPayList(List<PayList> payList) {
		this.payList = payList;
	}

}
