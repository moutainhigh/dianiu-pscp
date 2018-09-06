package com.edianniu.pscp.mis.bean.wallet;

import com.edianniu.pscp.mis.bean.Result;
import java.io.Serializable;
import java.util.List;

public class DaybookResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<WalletDetailInfo> getWalletDetails() {
        return walletDetails;
    }

    public void setWalletDetails(List<WalletDetailInfo> walletDetails) {
        this.walletDetails = walletDetails;
    }
}
