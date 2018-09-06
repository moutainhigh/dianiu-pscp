package com.edianniu.pscp.cs.bean.workorder;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderVO;

import java.util.List;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-08-07 16:54
 */
public class ListResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<WorkOrderVO> workOrderList;

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

    public List<WorkOrderVO> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<WorkOrderVO> workOrderList) {
        this.workOrderList = workOrderList;
    }
}
