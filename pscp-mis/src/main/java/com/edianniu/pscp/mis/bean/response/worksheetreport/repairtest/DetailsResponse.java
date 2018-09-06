package com.edianniu.pscp.mis.bean.response.worksheetreport.repairtest;

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
@JSONMessage(messageCode = 2002161)
public final class DetailsResponse extends BaseResponse {

    private ReportDetailsVO repairTestDetails;

    public ReportDetailsVO getRepairTestDetails() {
        return repairTestDetails;
    }

    public void setRepairTestDetails(ReportDetailsVO repairTestDetails) {
        this.repairTestDetails = repairTestDetails;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
