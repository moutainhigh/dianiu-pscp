package com.edianniu.pscp.sps.bean.workorder.worklog;

import com.edianniu.pscp.sps.commons.Constants;

import java.io.Serializable;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:43
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long workOrderId;
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
    private int offset;
    /**
     * 工单编号
     */
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
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
