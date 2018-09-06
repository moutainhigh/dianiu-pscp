package com.edianniu.pscp.mis.bean.request.invoice.title;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 发票信息
 */
@JSONMessage(messageCode = 1002192)
public final class InvoiceTitleRequest extends TerminalRequest {
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
