package com.edianniu.pscp.sps.bean.request.socialworkorder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: SaveRequest
 * Author: tandingbo
 * CreateTime: 2017-06-29 14:16
 */
@JSONMessage(messageCode = 1002103)
public final class SaveRequest extends TerminalRequest {

    private String orderId;
    private List<RecruitInfo> recruitInfoList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<RecruitInfo> getRecruitInfoList() {
        return recruitInfoList;
    }

    public void setRecruitInfoList(List<RecruitInfo> recruitInfoList) {
        this.recruitInfoList = recruitInfoList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
