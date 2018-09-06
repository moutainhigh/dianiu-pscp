package com.edianniu.pscp.cs.bean.needsorder;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderListVO;

import java.util.List;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-09-22 16:58
 */
public class ListResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<NeedsOrderListVO> needsOrderList;

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

    public List<NeedsOrderListVO> getNeedsOrderList() {
        return needsOrderList;
    }

    public void setNeedsOrderList(List<NeedsOrderListVO> needsOrderList) {
        this.needsOrderList = needsOrderList;
    }
}
