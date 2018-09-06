package com.edianniu.pscp.mis.bean.invoice;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;


public class ListInvoiceInfoResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 下一次请求的offset
     */
    private Integer nextOffset;
    /**
     * true:还有下一页
     * false:已经到了最后一页了。
     */
    private Boolean hasNext;
    /**
     * 总记录数
     */
    private Integer totalCount;
    /**
     * 发票信息
     */
    private List<InvoiceInfo> invoices;


    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
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
