package com.edianniu.pscp.sps.bean.response.workOrder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.WorkOrderInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-06-26 09:44
 */
@JSONMessage(messageCode = 2002094)
public final class ListResponse extends BaseResponse {

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
