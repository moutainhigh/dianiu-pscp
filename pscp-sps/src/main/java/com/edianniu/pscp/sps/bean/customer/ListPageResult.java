package com.edianniu.pscp.sps.bean.customer;

import com.edianniu.pscp.sps.bean.Result;

import java.util.List;

/**
 * ClassName: ListPageResult
 * Author: tandingbo
 * CreateTime: 2017-08-18 11:39
 */
public class ListPageResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<CustomerInfo> customerList;

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

    public List<CustomerInfo> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerInfo> customerList) {
        this.customerList = customerList;
    }
}
