package com.edianniu.pscp.mis.bean.response.wallet;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.wallet.BankType;
import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 2002035)
public class GetBanksResponse  extends BaseResponse {
	
	
	private List<BankType>banks;

	
	public List<BankType> getBanks() {
		return banks;
	}

	public void setBanks(List<BankType> banks) {
		this.banks = banks;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	
}
