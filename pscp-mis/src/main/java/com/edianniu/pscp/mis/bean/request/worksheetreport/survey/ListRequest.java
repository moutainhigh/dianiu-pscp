package com.edianniu.pscp.mis.bean.request.worksheetreport.survey;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * @author tandingbo
 * @create 2017-11-08 10:00
 */
@JSONMessage(messageCode = 1002178)
public final class ListRequest extends TerminalRequest {
    /**
     * 分页页码
     */
    private int offset;
    /**
     * 工单编号
     */
    private String orderId;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
