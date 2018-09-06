package com.edianniu.pscp.mis.bean.response.electricianworkorder;

import com.edianniu.pscp.mis.bean.electricianworkorder.ListQueryResultInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-04-13 09:38
 */
@JSONMessage(messageCode = 2002017)
public final class ListResponse extends BaseResponse {
    /**
     * 下一次请求的offset
     */
    private Integer nextOffset;
    /**
     * True:还有下一页。False:最后一页了。
     */
    private boolean hasNext;
    /**
     * 总记录数
     */
    private Long totalCount;
    /**
     * 工单信息
     */
    private List<ListQueryResultInfo> workOrders;

    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<ListQueryResultInfo> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<ListQueryResultInfo> workOrders) {
        this.workOrders = workOrders;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
