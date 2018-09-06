package com.edianniu.pscp.mis.bean.response.wallet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002031)
public class PreWithdrawalsResponse extends BaseResponse {

	private String amount;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
