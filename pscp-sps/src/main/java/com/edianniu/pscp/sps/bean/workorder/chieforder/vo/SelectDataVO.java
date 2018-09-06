package com.edianniu.pscp.sps.bean.workorder.chieforder.vo;

import java.io.Serializable;

/**
 * ClassName: SelectDataVO
 * Author: tandingbo
 * CreateTime: 2017-07-11 16:18
 */
public class SelectDataVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    private Long id;
    /**
     * 工单名称
     */
    private String name;
    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 工作地点
     */
    private String address;
    /**
     * 工单状态
     */
    private Integer status;
    /**
     * 工单发布时间(yyyy-MM-dd)
     */
    private String publishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
