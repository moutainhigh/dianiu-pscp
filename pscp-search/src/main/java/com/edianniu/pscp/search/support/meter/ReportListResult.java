package com.edianniu.pscp.search.support.meter;

import java.util.List;

import com.edianniu.pscp.search.support.Result;

/**
 * ReportListResult
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月6日 下午12:18:47 
 * @version V1.0
 */
public class ReportListResult<T> extends Result {
    private static final long serialVersionUID = 284670681106601109L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<T> list;

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

	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
    
}
