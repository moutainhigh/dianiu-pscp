package com.edianniu.pscp.mis.bean.response.invoice.info;

import com.edianniu.pscp.mis.bean.invoice.InvoiceInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 发票信息
 */
@JSONMessage(messageCode = 2002196)
public class DetailInvoiceInfoResponse extends BaseResponse {

    private InvoiceInfo invoice;

    public InvoiceInfo getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceInfo invoice) {
        this.invoice = invoice;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }


}
