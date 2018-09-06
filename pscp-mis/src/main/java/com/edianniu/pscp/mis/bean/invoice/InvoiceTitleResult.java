package com.edianniu.pscp.mis.bean.invoice;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class InvoiceTitleResult extends Result {
    private static final long serialVersionUID = 1L;


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
