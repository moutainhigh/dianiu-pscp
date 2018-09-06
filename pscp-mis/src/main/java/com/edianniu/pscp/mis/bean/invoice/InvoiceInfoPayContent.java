package com.edianniu.pscp.mis.bean.invoice;

import java.io.Serializable;

public class InvoiceInfoPayContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private String period;
    private String fee;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
