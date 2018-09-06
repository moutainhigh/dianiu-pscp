package com.edianniu.pscp.sps.bean.socialworkorder.recruit;

import java.io.Serializable;

/**
 * ClassName: RecruitInfo
 * Author: tandingbo
 * CreateTime: 2017-05-18 14:37
 */
public class RecruitInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 电工资质
     */
    private String qualifications;
    /**
     * 电工人数
     */
    private Integer quantity;
    /**
     * 费用
     */
    private Double fee;
    /**
     * 工作标题
     */
    private String title;
    /**
     * 需求描述
     */
    private String content;
    /**
     * 开始工作时间
     */
    private String beginWorkTime;
    /**
     * 结束工作时间
     */
    private String endWorkTime;
    /**
     * 派单截止时间
     */
    private String expiryTime;

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBeginWorkTime() {
        return beginWorkTime;
    }

    public void setBeginWorkTime(String beginWorkTime) {
        this.beginWorkTime = beginWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
}
