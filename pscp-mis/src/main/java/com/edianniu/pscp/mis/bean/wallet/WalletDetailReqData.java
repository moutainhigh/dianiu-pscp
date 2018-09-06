package com.edianniu.pscp.mis.bean.wallet;

import java.io.Serializable;

public class WalletDetailReqData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long userWalleDetailId;
	private Long uid;

	public Long getUserWalleDetailId() {
		return userWalleDetailId;
	}

	public void setUserWalleDetailId(Long userWalleDetailId) {
		this.userWalleDetailId = userWalleDetailId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	
	
	
	

}
