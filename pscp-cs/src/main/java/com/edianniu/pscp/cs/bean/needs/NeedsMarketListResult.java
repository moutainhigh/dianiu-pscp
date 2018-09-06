package com.edianniu.pscp.cs.bean.needs;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsMarketVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: NeedsMarketListResult
 * Author: tandingbo
 * CreateTime: 2017-09-21 10:37
 */
public class NeedsMarketListResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<NeedsMarketVO> needsList;

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

    public List<NeedsMarketVO> getNeedsList() {
        return needsList;
    }

    public void setNeedsList(List<NeedsMarketVO> needsList) {
        this.needsList = needsList;
    }

    public String ToString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
