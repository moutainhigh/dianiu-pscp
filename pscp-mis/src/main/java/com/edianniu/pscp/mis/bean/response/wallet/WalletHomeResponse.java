package com.edianniu.pscp.mis.bean.response.wallet;


import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailInfo;

@JSONMessage(messageCode = 2002028)
public class WalletHomeResponse extends BaseResponse {
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
