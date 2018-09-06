package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.bean.Result;

import java.util.List;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:42
 */
public class ListResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<WorkOrderInfo> workOrderInfoList;

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

    public List<WorkOrderInfo> getWorkOrderInfoList() {
        return workOrderInfoList;
    }

    public void setWorkOrderInfoList(List<WorkOrderInfo> workOrderInfoList) {
        this.workOrderInfoList = workOrderInfoList;
    }
}
