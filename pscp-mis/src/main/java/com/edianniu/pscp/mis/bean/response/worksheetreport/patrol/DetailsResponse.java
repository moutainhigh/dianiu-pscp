package com.edianniu.pscp.mis.bean.response.worksheetreport.patrol;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportDetailsVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DetailsResponse
 * Author: tandingbo
 * CreateTime: 2017-09-13 11:03
 */
@JSONMessage(messageCode = 2002165)
public final class DetailsResponse extends BaseResponse {

    private ReportDetailsVO patrolRecordDetails;

    public ReportDetailsVO getPatrolRecordDetails() {
        return patrolRecordDetails;
    }

    public void setPatrolRecordDetails(ReportDetailsVO patrolRecordDetails) {
        this.patrolRecordDetails = patrolRecordDetails;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
