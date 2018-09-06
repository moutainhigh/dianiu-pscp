package com.edianniu.pscp.mis.bean.response.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportDetailsVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * @author tandingbo
 * @create 2017-11-08 15:05
 */
@JSONMessage(messageCode = 2002179)
public final class DetailsResponse extends BaseResponse {

    private ReportDetailsVO surveyDetails;

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
