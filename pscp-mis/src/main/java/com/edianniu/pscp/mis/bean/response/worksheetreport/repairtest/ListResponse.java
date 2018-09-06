package com.edianniu.pscp.mis.bean.response.worksheetreport.repairtest;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-09-13 11:03
 */
@JSONMessage(messageCode = 2002160)
public final class ListResponse extends BaseResponse {

    private int nextOffset;
    private long totalCount;
    private boolean hasNext = false;

    private List<ReportListVO> repairTestRecordList;

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

    public List<ReportListVO> getRepairTestRecordList() {
        return repairTestRecordList;
    }

    public void setRepairTestRecordList(List<ReportListVO> repairTestRecordList) {
        this.repairTestRecordList = repairTestRecordList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
