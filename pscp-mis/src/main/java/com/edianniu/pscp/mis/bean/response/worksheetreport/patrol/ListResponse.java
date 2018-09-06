package com.edianniu.pscp.mis.bean.response.worksheetreport.patrol;

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
@JSONMessage(messageCode = 2002164)
public final class ListResponse extends BaseResponse {

    private int nextOffset;
    private long totalCount;
    private boolean hasNext = false;

    private List<ReportListVO> patrolRecordList;

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

    public List<ReportListVO> getPatrolRecordList() {
        return patrolRecordList;
    }

    public void setPatrolRecordList(List<ReportListVO> patrolRecordList) {
        this.patrolRecordList = patrolRecordList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
