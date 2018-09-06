package com.edianniu.pscp.cs.bean.company;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.company.vo.BindingFacilitatorVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: BindingFacilitatorResult
 * Author: tandingbo
 * CreateTime: 2017-10-30 14:59
 */
public class BindingFacilitatorResult extends Result {
    private static final long serialVersionUID = -3610150474109579063L;

    private List<BindingFacilitatorVO> companyList;

    private int nextOffset;
    private int totalCount;
    private boolean hasNext;

    public List<BindingFacilitatorVO> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<BindingFacilitatorVO> companyList) {
        this.companyList = companyList;
    }

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
