package com.edianniu.pscp.sps.bean.response.socialworkorder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ElectricianResponse
 * Author: tandingbo
 * CreateTime: 2017-06-29 15:41
 */
@JSONMessage(messageCode = 2002104)
public final class ElectricianResponse extends BaseResponse {
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
    List<Map<String, Object>> socialElectricianList;

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
