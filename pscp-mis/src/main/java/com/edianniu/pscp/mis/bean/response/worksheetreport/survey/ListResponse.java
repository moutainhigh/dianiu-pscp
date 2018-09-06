package com.edianniu.pscp.mis.bean.response.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * @author tandingbo
 * @create 2017-11-08 10:02
 */
@JSONMessage(messageCode = 2002178)
public final class ListResponse extends BaseResponse {

    private int nextOffset;
    private long totalCount;
    private boolean hasNext = false;

    private List<ReportListVO> surveyRecordList;

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

    public List<ReportListVO> getSurveyRecordList() {
        return surveyRecordList;
    }

    public void setSurveyRecordList(List<ReportListVO> surveyRecordList) {
        this.surveyRecordList = surveyRecordList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
