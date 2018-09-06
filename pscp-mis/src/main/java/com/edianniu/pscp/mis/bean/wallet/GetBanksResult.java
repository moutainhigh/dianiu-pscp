package com.edianniu.pscp.mis.bean.wallet;

import java.util.List;

import com.edianniu.pscp.mis.bean.Result;

public class GetBanksResult extends Result {
	private static final long serialVersionUID = 1L;
	
	private List<BankType>  banks;

	public List<BankType> getBanks() {
		return banks;
	}

	public void setBanks(List<BankType> banks) {
		this.banks = banks;
	}
}
