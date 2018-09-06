package com.edianniu.pscp.mis.bean.request.message;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SetReadMessageRequest
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:23
 */
@JSONMessage(messageCode = 1002041)
public final class SetReadMessageRequest extends TerminalRequest {
    /**
     * 消息ID
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
