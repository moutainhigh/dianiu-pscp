package com.edianniu.pscp.mis.bean.response.wallet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailInfo;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 2002030)
public class WalletDetailResponse  extends BaseResponse{
	private WalletDetailInfo walletDetail;
	
	

	
	public WalletDetailInfo getWalletDetail() {
		return walletDetail;
	}




	public void setWalletDetail(WalletDetailInfo walletDetail) {
		this.walletDetail = walletDetail;
	}




	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	

}
