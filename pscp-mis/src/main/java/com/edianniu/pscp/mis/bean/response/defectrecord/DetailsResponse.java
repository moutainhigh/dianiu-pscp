package com.edianniu.pscp.mis.bean.response.defectrecord;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordDetailsVO;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DetailsResponse
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:13
 */
@JSONMessage(messageCode = 2002156)
public final class DetailsResponse extends BaseResponse {

    private DefectRecordDetailsVO defectRecordDetails;

    public DefectRecordDetailsVO getDefectRecordDetails() {
        return defectRecordDetails;
    }

    public void setDefectRecordDetails(DefectRecordDetailsVO defectRecordDetails) {
        this.defectRecordDetails = defectRecordDetails;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
