package com.edianniu.pscp.mis.bean.response.invoice;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 发票信息
 */
@JSONMessage(messageCode = 2002198)
public class ApplyInvoiceResponse extends BaseResponse {
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }


}
