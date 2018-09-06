package com.edianniu.pscp.mis.bean.history;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.history.vo.FacilitatorHistoryVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: FacilitatorHistoryResult
 * Author: tandingbo
 * CreateTime: 2017-10-30 10:58
 */
public class FacilitatorHistoryResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private long totalCount;
    private boolean hasNext = false;

    private List<FacilitatorHistoryVO> companyList;

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<FacilitatorHistoryVO> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<FacilitatorHistoryVO> companyList) {
        this.companyList = companyList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
