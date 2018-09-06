package com.edianniu.pscp.mis.bean.wallet;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.pay.PayList;

import java.io.Serializable;
import java.util.List;

public class WalletRechargeResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private String amount;
    private List<PayList> payList;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
	public List<PayList> getPayList() {
		return payList;
	}

	public void setPayList(List<PayList> payList) {
		this.payList = payList;
	}
}
