package com.edianniu.pscp.sps.bean.workorder.chieforder.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: DetailsVO
 * Author: tandingbo
 * CreateTime: 2017-05-16 17:30
 */
public class SocialWorkOrderVO implements Serializable {
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
     * 电工费用
     */
    private Double fee;
    /**
     * 费用单位(0:元/天)
     */
    private Integer unit;
    /**
     * 工作标题
     */
    private String title;
    /**
     * 需求描述
     */
    private String content;
    /**
     * 工作开始时间
     */
    private String beginWorkTime;
    /**
     * 工作结束时间
     */
    private String endWorkTime;
    /**
     * 招募截止时间
     */
    private String expiryTime;
    /**
     * 工作时间
     */
    private String workTime;

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

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
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

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
