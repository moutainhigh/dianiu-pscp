package com.edianniu.pscp.mis.bean.request.invoice;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 发票信息
 */
@JSONMessage(messageCode = 1002198)
public final class ApplyInvoiceRequest extends TerminalRequest {

    private String payOrderId;
    private Long invoiceTitleId;
    private Long uid;


    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Long getInvoiceTitleId() {
        return invoiceTitleId;
    }

    public void setInvoiceTitleId(Long invoiceTitleId) {
        this.invoiceTitleId = invoiceTitleId;
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
