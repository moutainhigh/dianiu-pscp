package com.edianniu.pscp.sps.bean.socialworkorder.list;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.socialworkorder.list.vo.SocialWorkOrderVO;

import java.util.List;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-05-18 09:48
 */
public class ListResult extends Result {
    private static final long serialVersionUID = 1L;

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
}
