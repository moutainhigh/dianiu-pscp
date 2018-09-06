package com.edianniu.pscp.mis.bean.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-04-13 10:26
 */
public class ListResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ListQueryResultInfo> workOrders;

    private int nextOffset;
    private long totalCount;
    private boolean hasNext = false;

    public List<ListQueryResultInfo> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<ListQueryResultInfo> workOrders) {
        this.workOrders = workOrders;
    }

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
