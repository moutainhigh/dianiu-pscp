package com.edianniu.pscp.mis.bean.response.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.user.SimpleMemberInfo;
import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 2002027)
public final class GetUserCenterResponse extends BaseResponse {
	private SimpleMemberInfo simpleMemberInfo;
	
	private Integer messageNum;
	private String walletAmount;
	
	public SimpleMemberInfo getSimpleMemberInfo() {
		return simpleMemberInfo;
	}
	public void setSimpleMemberInfo(SimpleMemberInfo simpleMemberInfo) {
		this.simpleMemberInfo = simpleMemberInfo;
	}
	public Integer getMessageNum() {
		return messageNum;
	}
	public void setMessageNum(Integer messageNum) {
		this.messageNum = messageNum;
	}
	public String getWalletAmount() {
		return walletAmount;
	}
	public void setWalletAmount(String walletAmount) {
		this.walletAmount = walletAmount;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	

}
