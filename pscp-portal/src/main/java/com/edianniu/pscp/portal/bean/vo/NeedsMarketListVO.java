package com.edianniu.pscp.portal.bean.vo;

import java.io.Serializable;

/**
 * ClassName: NeedsMarketListVO
 * Author: tandingbo
 * CreateTime: 2017-09-30 10:59
 */
public class NeedsMarketListVO implements Serializable {
    /* 需求ID.*/
    private Long id;
    /* 需求编号.*/
    private String orderId;
    /* 需求名称.*/
    private String name;
    /* 需求描述.*/
    private String describe;
    /* 发布时间(yyyy-mm-dd).*/
    private String publishTime;
    /* 需求响应状态(0:未响应,1:已响应).*/
    private Integer respondStatus;
    /* 支付状态(0:未支付,1:已支付).*/
    private Integer payStatus;
    /* 需求响应人数.*/
    private Integer responseNumber;
    /* 发布截止时间(yyyy-mm-dd).*/
    private String publishCutoffTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getRespondStatus() {
        return respondStatus;
    }

    public void setRespondStatus(Integer respondStatus) {
        this.respondStatus = respondStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getResponseNumber() {
        return responseNumber;
    }

    public void setResponseNumber(Integer responseNumber) {
        this.responseNumber = responseNumber;
    }

    public String getPublishCutoffTime() {
        return publishCutoffTime;
    }

    public void setPublishCutoffTime(String publishCutoffTime) {
        this.publishCutoffTime = publishCutoffTime;
    }
}
