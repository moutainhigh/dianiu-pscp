package com.edianniu.pscp.cs.bean.workorder;

import java.io.Serializable;

/**
 * ClassName: EvaluateInfo
 * Author: tandingbo
 * CreateTime: 2017-08-08 12:15
 */
public class EvaluateInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 评价内容.*/
    private String content;
    /* 服务质量.*/
    private Integer serviceQuality;
    /* 服务速度.*/
    private Integer serviceSpeed;
    /* 评价图片信息.*/
    private String[] evaluateImageArray;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(Integer serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public Integer getServiceSpeed() {
        return serviceSpeed;
    }

    public void setServiceSpeed(Integer serviceSpeed) {
        this.serviceSpeed = serviceSpeed;
    }

    public String[] getEvaluateImageArray() {
        return evaluateImageArray;
    }

    public void setEvaluateImageArray(String[] evaluateImageArray) {
        this.evaluateImageArray = evaluateImageArray;
    }
}
