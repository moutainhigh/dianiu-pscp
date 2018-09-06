package com.edianniu.pscp.sps.bean.request.socialworkorder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ConfirmRequest
 * Author: tandingbo
 * CreateTime: 2017-06-29 16:38
 */
@JSONMessage(messageCode = 1002105)
public final class ConfirmRequest extends TerminalRequest {

    /**
     * 社会工单编号
     */
    private String orderId;
    /**
     * 当前登录用户信息
     */
    private Long uid;
    /**
     * 确认电工信息
     */
    private List<Map<String, Object>> socialElectricianList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public void setUid(Long uid) {
        this.uid = uid;
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
