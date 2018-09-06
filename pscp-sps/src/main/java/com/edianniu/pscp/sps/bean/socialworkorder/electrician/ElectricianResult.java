package com.edianniu.pscp.sps.bean.socialworkorder.electrician;

import com.edianniu.pscp.sps.bean.Result;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ElectricianResult
 * Author: tandingbo
 * CreateTime: 2017-06-29 15:52
 */
public class ElectricianResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;
    /**
     * 确认人数
     */
    private Integer recruitNumber;
    /**
     * 招募人数
     */
    private Integer totleNumber;
    /**
     * 社会电工工单信息
     */
    private List<Map<String, Object>> socialElectricianList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRecruitNumber() {
        return recruitNumber;
    }

    public void setRecruitNumber(Integer recruitNumber) {
        this.recruitNumber = recruitNumber;
    }

    public Integer getTotleNumber() {
        return totleNumber;
    }

    public void setTotleNumber(Integer totleNumber) {
        this.totleNumber = totleNumber;
    }

    public List<Map<String, Object>> getSocialElectricianList() {
        return socialElectricianList;
    }

    public void setSocialElectricianList(List<Map<String, Object>> socialElectricianList) {
        this.socialElectricianList = socialElectricianList;
    }
}
