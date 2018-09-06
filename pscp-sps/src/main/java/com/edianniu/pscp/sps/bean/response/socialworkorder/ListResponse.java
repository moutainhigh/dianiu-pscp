package com.edianniu.pscp.sps.bean.response.socialworkorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.socialworkorder.list.vo.SocialWorkOrderVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-06-29 11:17
 */
@JSONMessage(messageCode = 2002101)
public final class ListResponse extends BaseResponse {
    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<SocialWorkOrderVO> socialWorkOrderList;

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

    public List<SocialWorkOrderVO> getSocialWorkOrderList() {
        return socialWorkOrderList;
    }

    public void setSocialWorkOrderList(List<SocialWorkOrderVO> socialWorkOrderList) {
        this.socialWorkOrderList = socialWorkOrderList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
