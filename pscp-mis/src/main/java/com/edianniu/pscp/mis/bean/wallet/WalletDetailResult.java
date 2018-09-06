package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.Result;

public class WalletDetailResult extends Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WalletDetailInfo walletDetail;

	public WalletDetailInfo getWalletDetail() {
		return walletDetail;
	}

	public void setWalletDetail(WalletDetailInfo walletDetail) {
		this.walletDetail = walletDetail;
	}
	
	

}
