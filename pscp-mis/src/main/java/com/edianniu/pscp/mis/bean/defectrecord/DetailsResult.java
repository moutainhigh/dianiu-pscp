package com.edianniu.pscp.mis.bean.defectrecord;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordDetailsVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: DetailsResult
 * Author: tandingbo
 * CreateTime: 2017-09-12 16:51
 */
public class DetailsResult extends Result {
    private static final long serialVersionUID = 1L;

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
