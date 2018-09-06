package com.edianniu.pscp.sps.bean.response.workOrder;

import com.alibaba.fastjson.annotation.JSONField;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: SaveResponse
 * Author: tandingbo
 * CreateTime: 2017-06-28 16:08
 */
@JSONMessage(messageCode = 2002097)
public final class SaveResponse extends BaseResponse {

    /**
     * 工单ID
     */
    private Long id;
    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 社会工单ID
     */
    @JSONField(serialize = false)
    private List<Long> socialWorkOrderIds;
    /**
     * 社会工单编号（多个以,隔开）
     */
    @JSONField(serialize = false)
    private String socialOrderIdStr;

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

    public List<Long> getSocialWorkOrderIds() {
        return socialWorkOrderIds;
    }

    public void setSocialWorkOrderIds(List<Long> socialWorkOrderIds) {
        this.socialWorkOrderIds = socialWorkOrderIds;
    }

    public String getSocialOrderIdStr() {
        return socialOrderIdStr;
    }

    public void setSocialOrderIdStr(String socialOrderIdStr) {
        this.socialOrderIdStr = socialOrderIdStr;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
