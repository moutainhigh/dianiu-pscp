package com.edianniu.pscp.sps.bean.workorder.evaluate;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:26
 */
public class ListResult extends Result {
    private static final long serialVersionUID = 1L;

    private WorkOrderEvaluateInfo workOrderEvaluate;

    public WorkOrderEvaluateInfo getWorkOrderEvaluate() {
        return workOrderEvaluate;
    }

    public void setWorkOrderEvaluate(WorkOrderEvaluateInfo workOrderEvaluate) {
        this.workOrderEvaluate = workOrderEvaluate;
    }
}
