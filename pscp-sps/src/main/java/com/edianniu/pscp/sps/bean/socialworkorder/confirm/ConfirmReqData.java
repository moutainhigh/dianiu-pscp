package com.edianniu.pscp.sps.bean.socialworkorder.confirm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ConfirmReqData
 * Author: tandingbo
 * CreateTime: 2017-05-24 09:52
 */
public class ConfirmReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社会工单ID
     */
    private Long socialWorkOrderId;
    /**
     * 社会工单编号
     */
    private String orderId;
    /**
     * 当前登录用户信息
     */
    private Long uid;
    /**
     * 确认电工信息
     */
    private List<Map<String, Object>> socialElectricianList;

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<Map<String, Object>> getSocialElectricianList() {
        return socialElectricianList;
    }

    public void setSocialElectricianList(List<Map<String, Object>> socialElectricianList) {
        this.socialElectricianList = socialElectricianList;
    }
}

