package com.edianniu.pscp.sps.bean.workorder.worklog;

import com.edianniu.pscp.sps.commons.BaseQuery;

/**
 * ClassName: ListQuery
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:44
 */
public class ListQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long workOrderId;
    private String orderId;

    /**
     * 来源-解决图片地址域名（区分:APP,PC）
     */
    private String source;

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
