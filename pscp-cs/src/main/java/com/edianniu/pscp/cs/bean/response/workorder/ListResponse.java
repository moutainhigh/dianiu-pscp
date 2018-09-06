package com.edianniu.pscp.cs.bean.response.workorder;

import com.edianniu.pscp.cs.bean.response.BaseResponse;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-08-07 17:00
 */
@JSONMessage(messageCode = 2002115)
public final class ListResponse extends BaseResponse {
    
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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
