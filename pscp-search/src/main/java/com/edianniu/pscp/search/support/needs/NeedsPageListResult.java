package com.edianniu.pscp.search.support.needs;

import com.edianniu.pscp.search.support.Result;
import com.edianniu.pscp.search.support.needs.vo.NeedsVO;

import java.util.List;

/**
 * ClassName: NeedsPageListResult
 * Author: tandingbo
 * CreateTime: 2017-10-18 11:02
 */
public class NeedsPageListResult extends Result {
    private static final long serialVersionUID = 284670681106601109L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<NeedsVO> needsList;

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

    public List<NeedsVO> getNeedsList() {
        return needsList;
    }

    public void setNeedsList(List<NeedsVO> needsList) {
        this.needsList = needsList;
    }
}
