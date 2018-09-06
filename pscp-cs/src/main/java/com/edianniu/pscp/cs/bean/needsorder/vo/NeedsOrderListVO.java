package com.edianniu.pscp.cs.bean.needsorder.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * ClassName: NeedsOrderListVO
 * Author: tandingbo
 * CreateTime: 2017-09-22 17:01
 */
public class NeedsOrderListVO implements Serializable {
    private static final long serialVersionUID = 1601656894142924102L;

    /* 需求编号.*/
    private String orderId;
    /* 服务商响应订单编号.*/
    private String responsedOrderId;
    /* 需求名称.*/
    private String name;
    /* 需求描述.*/
    private String describe;
    /* 发布时间(yyyy-mm-dd).*/
    private String publishTime;
    /* 状态.*/
    /**
     * 取消: -3
     * 不合作: -2
     * 不符合: -1
     * 未响应: 0
     * 已响应: 1
     * 待报价: 2
     * 已报价: 3
     * 已合作: 4
     */
    private Integer status;
    //前台显示状态
    //@JSONField(serialize = false)
    private String showStatus;

    /* 支付状态(0:未支付
	,1:支付确认 (客户端)
	,2:支付成功(服务端异步通知)
	,3:支付失败
	,4:取消支付).*/
    private Integer payStatus;
    /* 关键词.*/
    @JSONField(serialize = false)
    private String keyword;

    /*---------------需求相关信息-------------------*/
    /* 需求发布截止时间.*/
    @JSONField(serialize = false)
    private String publishCutoffTime;
    /* 需求工作开始时间.*/
    @JSONField(serialize = false)
    private String workingStartTime;
    /* 需求工作结束时间.*/
    @JSONField(serialize = false)
    private String workingEndTime;
    /* 客户公司名称.*/
    @JSONField(serialize = false)
    private String compnayName;
    /* 客户联系人.*/
    @JSONField(serialize = false)
    private String contactPerson;
    /* 客户联系电话.*/
    @JSONField(serialize = false)
    private String contactNumber;
    /* 客户联系地址.*/
    @JSONField(serialize = false)
    private String contactAddr;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getResponsedOrderId() {
        return responsedOrderId;
    }

    public void setResponsedOrderId(String responsedOrderId) {
        this.responsedOrderId = responsedOrderId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getPublishCutoffTime() {
        return publishCutoffTime;
    }

    public void setPublishCutoffTime(String publishCutoffTime) {
        this.publishCutoffTime = publishCutoffTime;
    }

    public String getWorkingStartTime() {
        return workingStartTime;
    }

    public void setWorkingStartTime(String workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public String getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(String workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    public String getCompnayName() {
        return compnayName;
    }

    public void setCompnayName(String compnayName) {
        this.compnayName = compnayName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
    
}
