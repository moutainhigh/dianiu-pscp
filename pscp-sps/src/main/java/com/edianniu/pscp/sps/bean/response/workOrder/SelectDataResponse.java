package com.edianniu.pscp.sps.bean.response.workOrder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.SelectDataVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: SelectDataResponse
 * Author: tandingbo
 * CreateTime: 2017-07-11 16:15
 */
@JSONMessage(messageCode = 2002112)
public final class SelectDataResponse extends BaseResponse {

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<SelectDataVO> workOrderList;

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

    public List<SelectDataVO> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<SelectDataVO> workOrderList) {
        this.workOrderList = workOrderList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
