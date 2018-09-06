package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.SelectDataVO;

import java.util.List;

/**
 * ClassName: SelectDataResult
 * Author: tandingbo
 * CreateTime: 2017-07-11 16:17
 */
public class SelectDataResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<SelectDataVO> workOrderList;

    public List<SelectDataVO> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<SelectDataVO> workOrderList) {
        this.workOrderList = workOrderList;
    }

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
}
