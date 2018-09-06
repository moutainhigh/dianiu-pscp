package com.edianniu.pscp.sps.bean.customer;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.domain.CompanyCustomer;

import java.util.List;

/**
 * 
 * @author zhoujianjian
 * 2017年9月26日下午7:03:37
 */
public class CustomerInfoListPageResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<CustomerInfo> customerInfoList;

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

	public List<CustomerInfo> getCustomerInfoList() {
		return customerInfoList;
	}

	public void setCustomerInfoList(List<CustomerInfo> customerInfoList) {
		this.customerInfoList = customerInfoList;
	}

}
