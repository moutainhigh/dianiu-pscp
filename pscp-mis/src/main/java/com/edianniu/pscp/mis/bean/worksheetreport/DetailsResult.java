package com.edianniu.pscp.mis.bean.worksheetreport;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportDetailsVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: DetailsResult
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:45
 */
public class DetailsResult extends Result {
    private static final long serialVersionUID = 1L;

    /* 修试记录.*/
    private ReportDetailsVO repairTestDetails;
    /* 巡视记录.*/
    private ReportDetailsVO patrolRecordDetails;
    /**
     * 勘察记录
     */
    private ReportDetailsVO surveyDetails;

    public ReportDetailsVO getRepairTestDetails() {
        return repairTestDetails;
    }

    public void setRepairTestDetails(ReportDetailsVO repairTestDetails) {
        this.repairTestDetails = repairTestDetails;
    }

    public ReportDetailsVO getPatrolRecordDetails() {
        return patrolRecordDetails;
    }

    public void setPatrolRecordDetails(ReportDetailsVO patrolRecordDetails) {
        this.patrolRecordDetails = patrolRecordDetails;
    }

    public ReportDetailsVO getSurveyDetails() {
        return surveyDetails;
    }

    public void setSurveyDetails(ReportDetailsVO surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
