package com.edianniu.pscp.mis.bean.response.wallet;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailInfo;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002029)
public class DaybookResponse extends BaseResponse {

    private int nextOffset;
    private boolean hasNext;
    private int totalCount;
    private List<WalletDetailInfo> walletDetails;

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

   
    public List<WalletDetailInfo> getWalletDetails() {
		return walletDetails;
	}

	public void setWalletDetails(List<WalletDetailInfo> walletDetails) {
		this.walletDetails = walletDetails;
	}

	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
