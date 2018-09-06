package com.edianniu.pscp.mis.bean.request.invoice.info;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 发票信息
 */
@JSONMessage(messageCode = 1002195)
public final class ListInvoiceInfoRequest extends TerminalRequest {

    private int offset;
    /**
     * unfinished：未开票 , finished：已开票
     */
    private Integer status;

    /**
     * 开票公司
     */
    private String payerCompanyName;

    /**
     * 支付日期
     */
    private String payDate;

    public String getPayerCompanyName() {
        return payerCompanyName;
    }

    public void setPayerCompanyName(String payerCompanyName) {
        this.payerCompanyName = payerCompanyName;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
