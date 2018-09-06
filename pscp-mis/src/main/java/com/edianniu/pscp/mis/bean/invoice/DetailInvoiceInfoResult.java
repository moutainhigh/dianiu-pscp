package com.edianniu.pscp.mis.bean.invoice;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class DetailInvoiceInfoResult extends Result {
    private static final long serialVersionUID = 1L;

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
