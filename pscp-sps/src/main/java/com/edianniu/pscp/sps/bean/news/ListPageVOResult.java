package com.edianniu.pscp.sps.bean.news;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.news.VO.NewsListInfoVO;

import java.util.List;

/**
 * ClassName: ListPageVOResult
 * Author: tandingbo
 * CreateTime: 2017-08-10 17:27
 */
public class ListPageVOResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    private List<NewsListInfoVO> newsList;

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

    public List<NewsListInfoVO> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsListInfoVO> newsList) {
        this.newsList = newsList;
    }
}
