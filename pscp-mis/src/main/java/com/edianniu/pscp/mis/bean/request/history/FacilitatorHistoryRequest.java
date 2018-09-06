package com.edianniu.pscp.mis.bean.request.history;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: FacilitatorHistoryRequest
 *
 * @author: tandingbo
 * CreateTime: 2017-10-30 11:04
 */
@JSONMessage(messageCode = 1002176)
public final class FacilitatorHistoryRequest extends TerminalRequest {

    private Integer offset;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
