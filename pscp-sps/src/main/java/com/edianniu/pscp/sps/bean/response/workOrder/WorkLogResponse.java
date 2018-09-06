package com.edianniu.pscp.sps.bean.response.workOrder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.workorder.worklog.ElectricianWorkLogInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: WorkLogResponse
 * Author: tandingbo
 * CreateTime: 2017-06-28 17:38
 */
@JSONMessage(messageCode = 2002098)
public final class WorkLogResponse extends BaseResponse {
    
    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<ElectricianWorkLogInfo> workLogInfoList;

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

    public List<ElectricianWorkLogInfo> getWorkLogInfoList() {
        return workLogInfoList;
    }

    public void setWorkLogInfoList(List<ElectricianWorkLogInfo> workLogInfoList) {
        this.workLogInfoList = workLogInfoList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
