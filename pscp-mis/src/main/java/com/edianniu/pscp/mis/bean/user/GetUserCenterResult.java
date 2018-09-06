package com.edianniu.pscp.mis.bean.user;


import com.edianniu.pscp.mis.bean.Result;
public class GetUserCenterResult extends Result{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer messageNum;
	private String walletAmount;
	private SimpleMemberInfo simpleMemberInfo;

	public Integer getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(Integer messageNum) {
		this.messageNum = messageNum;
	}

	public SimpleMemberInfo getSimpleMemberInfo() {
		return simpleMemberInfo;
	}

	public void setSimpleMemberInfo(SimpleMemberInfo simpleMemberInfo) {
		this.simpleMemberInfo = simpleMemberInfo;
	}

	public String getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(String walletAmount) {
		this.walletAmount = walletAmount;
	}
	
	

}
