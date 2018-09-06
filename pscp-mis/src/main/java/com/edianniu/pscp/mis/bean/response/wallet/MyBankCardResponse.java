package com.edianniu.pscp.mis.bean.response.wallet;

import java.util.List;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.wallet.UserBankCardInfo;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 2002033)
public class MyBankCardResponse extends BaseResponse{

	private List<UserBankCardInfo>bankCards;

	public List<UserBankCardInfo> getBankCards() {
		return bankCards;
	}

	public void setBankCards(List<UserBankCardInfo> bankCards) {
		this.bankCards = bankCards;
	}
	

}
