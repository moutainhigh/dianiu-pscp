package com.edianniu.pscp.sps.bean.response.workOrder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.workorder.evaluate.WorkOrderEvaluateInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: EvaluateResponse
 * Author: tandingbo
 * CreateTime: 2017-06-29 10:07
 */
@JSONMessage(messageCode = 2002099)
public final class EvaluateResponse extends BaseResponse {

    private WorkOrderEvaluateInfo workOrderEvaluate;

    public WorkOrderEvaluateInfo getWorkOrderEvaluate() {
        return workOrderEvaluate;
    }

    public void setWorkOrderEvaluate(WorkOrderEvaluateInfo workOrderEvaluate) {
        this.workOrderEvaluate = workOrderEvaluate;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
