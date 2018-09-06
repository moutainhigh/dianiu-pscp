package com.edianniu.pscp.sps.bean.response.electricianworkorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.workorder.electrician.ElectricianWorkOrderInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-07-11 11:36
 */
@JSONMessage(messageCode = 2002110)
public final class ListResponse extends BaseResponse {

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<ElectricianWorkOrderInfo> electricianWorkOrders;

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

    public List<ElectricianWorkOrderInfo> getElectricianWorkOrders() {
        return electricianWorkOrders;
    }

    public void setElectricianWorkOrders(List<ElectricianWorkOrderInfo> electricianWorkOrders) {
        this.electricianWorkOrders = electricianWorkOrders;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
