package com.edianniu.pscp.mis.bean.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: ListWorkLogResult
 * Author: tandingbo
 * CreateTime: 2017-04-17 11:33
 */
public class ListWorkLogResult extends Result {
    private static final long serialVersionUID = 1L;

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
