package com.edianniu.pscp.cs.bean.response.company;

import com.edianniu.pscp.cs.bean.company.vo.BindingFacilitatorVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: BindingFacilitatorResponse
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:06
 */
@JSONMessage(messageCode = 2002177)
public final class BindingFacilitatorResponse extends BaseResponse {

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
