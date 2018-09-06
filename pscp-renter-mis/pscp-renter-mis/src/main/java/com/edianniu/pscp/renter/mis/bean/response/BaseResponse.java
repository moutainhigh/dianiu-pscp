package com.edianniu.pscp.renter.mis.bean.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.AbstractCommonBean;
import stc.skymobi.bean.json.JSONSignal;

public abstract class BaseResponse extends AbstractCommonBean implements
		JSONSignal {
	private int resultCode = 200;
	private String resultMessage = "成功";

	public int getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
