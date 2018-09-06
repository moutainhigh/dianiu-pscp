package com.edianniu.pscp.mis.bean.response.wallet;



import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.pay.PayList;
import com.edianniu.pscp.mis.bean.response.BaseResponse;

@JSONMessage(messageCode = 2001078)
public class WalletRechargeResponse extends BaseResponse {
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
