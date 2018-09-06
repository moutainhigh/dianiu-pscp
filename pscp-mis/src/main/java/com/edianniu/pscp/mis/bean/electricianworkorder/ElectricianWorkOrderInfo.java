package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

public class ElectricianWorkOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long uid;//电工ID
    private String name;//电工名字
    private String mobile;//电工手机号码
    private Integer type;//1企业电工，2社会电工
    private Integer status;//
    private String orderId;// 工单编号
    private String checkOption;// 检修项目
    private Long socialWorkOrderId;// 社会工单ID

    public Long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getType() {
        return type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCheckOption() {
        return checkOption;
    }

    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }
}
