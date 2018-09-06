package com.edianniu.pscp.mis.bean.response.electricianworkorder;

import com.edianniu.pscp.mis.bean.electricianworkorder.WorkLogInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListWorkLogResponse
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:28
 */
@JSONMessage(messageCode = 2002026)
public final class ListWorkLogResponse extends BaseResponse {

    private List<WorkLogInfo> worklogs;

    public List<WorkLogInfo> getWorklogs() {
        return worklogs;
    }

    public void setWorklogs(List<WorkLogInfo> worklogs) {
        this.worklogs = worklogs;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
