package com.edianniu.pscp.mis.bean.wallet;

import com.edianniu.pscp.mis.bean.Result;
import java.io.Serializable;
import java.util.List;

public class WalletHomeResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private String amount;
    private String freezingAmount;
    private List<WalletDetailInfo> walletDetails;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(String freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

	public List<WalletDetailInfo> getWalletDetails() {
		return walletDetails;
	}

	public void setWalletDetails(List<WalletDetailInfo> walletDetails) {
		this.walletDetails = walletDetails;
	}

	
}
