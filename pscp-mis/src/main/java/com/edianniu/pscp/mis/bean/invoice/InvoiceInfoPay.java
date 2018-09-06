package com.edianniu.pscp.mis.bean.invoice;

import java.io.Serializable;
import java.util.List;

public class InvoiceInfoPay implements Serializable {
    private static final long serialVersionUID = 1L;
    private String payOrderId;
    private List<InvoiceInfoPayContent> payContents;

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }


    public List<InvoiceInfoPayContent> getPayContents() {
        return payContents;
    }

    public void setPayContents(List<InvoiceInfoPayContent> payContents) {
        this.payContents = payContents;
    }
}
