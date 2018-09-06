package com.edianniu.pscp.mis.bean.worksheetreport;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:45
 */
public class ListResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private long totalCount;
    private boolean hasNext = false;

    /* 修试记录.*/
    private List<ReportListVO> repairTestRecordList;
    /* 巡视记录.*/
    private List<ReportListVO> patrolRecordList;
    /**
     * 勘察记录
     */
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

    public List<ReportListVO> getRepairTestRecordList() {
        return repairTestRecordList;
    }

    public void setRepairTestRecordList(List<ReportListVO> repairTestRecordList) {
        this.repairTestRecordList = repairTestRecordList;
    }

    public List<ReportListVO> getPatrolRecordList() {
        return patrolRecordList;
    }

    public void setPatrolRecordList(List<ReportListVO> patrolRecordList) {
        this.patrolRecordList = patrolRecordList;
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

