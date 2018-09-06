package com.edianniu.pscp.cs.bean.needs.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * ClassName: NeedsMarketVO
 * Author: tandingbo
 * CreateTime: 2017-09-21 10:34
 */
public class NeedsMarketVO implements Serializable {
    private static final long serialVersionUID = 6619071769793487260L;

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
    @JSONField(serialize = false)
    private Integer payStatus;
    /* 关键词.*/
    @JSONField(serialize = false)
    private String keyword;
    /* 需求响应人数.*/
    @JSONField(serialize = false)
    private Integer responseNumber;
    /* 发布截止时间(yyyy-mm-dd).*/
    @JSONField(serialize = false)
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
