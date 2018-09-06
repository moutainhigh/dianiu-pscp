package com.edianniu.pscp.cs.bean.request.workorder;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ListRequest
 * Author: tandingbo
 * CreateTime: 2017-08-07 17:00
 */
@JSONMessage(messageCode = 1002115)
public final class ListRequest extends TerminalRequest {

    private int offset;
    /**
     * 工单状态
     * 进行中：underway
     * 已结束：finished
     */
    private String status;
    private String name;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
