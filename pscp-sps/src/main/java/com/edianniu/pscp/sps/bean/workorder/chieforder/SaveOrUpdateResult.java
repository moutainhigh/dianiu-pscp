package com.edianniu.pscp.sps.bean.workorder.chieforder;

import com.edianniu.pscp.sps.bean.Result;

import java.util.List;

/**
 * ClassName: SaveOrUpdateResult
 * Author: tandingbo
 * CreateTime: 2017-05-19 09:29
 */
public class SaveOrUpdateResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    private Long id;
    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 社会工单ID
     */
    private List<Long> socialWorkOrderIds;
    /**
     * 社会工单编号（多个以,隔开）
     */
    private String socialOrderIdStr;

    public List<Long> getSocialWorkOrderIds() {
        return socialWorkOrderIds;
    }

    public void setSocialWorkOrderIds(List<Long> socialWorkOrderIds) {
        this.socialWorkOrderIds = socialWorkOrderIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSocialOrderIdStr() {
        return socialOrderIdStr;
    }

    public void setSocialOrderIdStr(String socialOrderIdStr) {
        this.socialOrderIdStr = socialOrderIdStr;
    }
}
