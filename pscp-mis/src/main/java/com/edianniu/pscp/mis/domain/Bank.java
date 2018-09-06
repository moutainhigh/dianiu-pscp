package com.edianniu.pscp.mis.domain;

/**
 * ClassName: Bank
 * Author: tandingbo
 * CreateTime: 2017-08-09 16:37
 */
public class Bank extends BaseDo {
    private static final long serialVersionUID = 1L;

    /* 主键ID.*/
    private Long id;
    /* 银行名称.*/
    private String name;
    /* 银行简称.*/
    private String abbreviation;
    /* 银行卡背景图.*/
    private String cardIcon;
    /* 银行状态(0:启用,1:禁用).*/
    private Integer status;
    /* 排序号.*/
    private Integer orderNum;

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCardIcon() {
        return cardIcon;
    }

    public void setCardIcon(String cardIcon) {
        this.cardIcon = cardIcon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
