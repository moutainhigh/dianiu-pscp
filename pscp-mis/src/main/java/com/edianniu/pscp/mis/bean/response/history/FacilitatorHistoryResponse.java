package com.edianniu.pscp.mis.bean.response.history;

import com.edianniu.pscp.mis.bean.history.vo.FacilitatorHistoryVO;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: FacilitatorHistoryResponse
 * @author: tandingbo
 * CreateTime: 2017-10-30 11:05
 */
@JSONMessage(messageCode = 2002176)
public class FacilitatorHistoryResponse extends BaseResponse {

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
