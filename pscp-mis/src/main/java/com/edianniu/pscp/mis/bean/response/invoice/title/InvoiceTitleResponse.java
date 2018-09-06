package com.edianniu.pscp.mis.bean.response.invoice.title;

import com.edianniu.pscp.mis.bean.invoice.InvoiceTitle;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 发票信息
 */
@JSONMessage(messageCode = 2002192)
public class InvoiceTitleResponse extends BaseResponse {

    private InvoiceTitle invoiceTitle;

    public InvoiceTitle getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(InvoiceTitle invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }


}
