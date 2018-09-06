package com.edianniu.pscp.mis.bean.request.message;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ListMessageRequest
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:10
 */
@JSONMessage(messageCode = 1002040)
public final class ListMessageRequest extends TerminalRequest {

    /**
     * 类型(message:消息,active:活动)
     */
    private String type;
    /**
     * 分页(默认0)
     */
    private Integer offset;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
