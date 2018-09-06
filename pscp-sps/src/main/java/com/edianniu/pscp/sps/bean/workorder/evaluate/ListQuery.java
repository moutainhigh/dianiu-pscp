package com.edianniu.pscp.sps.bean.workorder.evaluate;

import com.edianniu.pscp.sps.commons.BaseQuery;

/**
 * ClassName: ListQuery
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:26
 */
public class ListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long workOrderId;

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }
}
