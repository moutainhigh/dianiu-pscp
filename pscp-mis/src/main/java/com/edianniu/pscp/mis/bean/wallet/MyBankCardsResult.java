package com.edianniu.pscp.mis.bean.wallet;

import java.util.List;

import com.edianniu.pscp.mis.bean.Result;

public class MyBankCardsResult extends Result{
	private static final long serialVersionUID = 1L;
	
	private List<UserBankCardInfo> bankCards;

	public List<UserBankCardInfo> getBankCards() {
		return bankCards;
	}
	public void setBankCards(List<UserBankCardInfo> bankCards) {
		this.bankCards = bankCards;
	}
}
