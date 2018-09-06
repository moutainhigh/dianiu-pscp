package com.edianniu.pscp.cs.bean.workorder.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * ClassName: WorkOrderVO
 * Author: tandingbo
 * CreateTime: 2017-08-07 17:02
 */
public class WorkOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 工单编号.*/
    private String orderId;
    /* 工单名称.*/
    private String name;
    /* 项目名称.*/
    private String projectName;
    /* 工单发布时间(yyyy-MM-dd).*/
    private String publishTime;
    /* 工单状态.*/
    /**
     * -1：已取消
     * 0：未确认
     * 1：已确认
     * 2：进行中
     * 3：未评价
     * 4：已评价
     */
    private Integer status;
    /* 工单类型.*/
    @JSONField(serialize = false)
    private Integer type;
    /* 工单类型名称.*/
    private String typeName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
