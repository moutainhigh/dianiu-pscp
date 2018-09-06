package com.edianniu.pscp.mis.bean.response.invoice.info;

import com.edianniu.pscp.mis.bean.invoice.InvoiceInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;
import java.util.List;

/**
 * 发票信息
 */
@JSONMessage(messageCode = 2002195)
public class ListInvoiceInfoResponse extends BaseResponse {
    /**
     * 下一次请求的offset
     */
    private Integer nextOffset;
    /**
     * True:还有下一页。False:最后一页了。
     */
    private boolean hasNext;
    /**
     * 总记录数
     */
    private Integer totalCount;

    private List<InvoiceInfo> invoices;

    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<InvoiceInfo> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceInfo> invoices) {
        this.invoices = invoices;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }


}
