package com.edianniu.pscp.sps.bean.response.needsorder;

import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderListVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-09-26 10:24
 */
@JSONMessage(messageCode = 2002170)
public final class ListResponse extends BaseResponse {

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
