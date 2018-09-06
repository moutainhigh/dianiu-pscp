package com.edianniu.pscp.cs.bean.needsorder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: OfferReqData
 * Author: tandingbo
 * CreateTime: 2017-09-25 11:36
 */
public class OfferReqData implements Serializable {
    private static final long serialVersionUID = -828033764714645808L;

    /* 登录用户ID.*/
    private Long uid;
    /* 响应订单编号.*/
    private String orderId;
    /* 报价金额.*/
    private String quotedPrice;
    /* 报价附件.*/
    private List<Map<String, Object>> attachmentList;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(String quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public List<Map<String, Object>> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Map<String, Object>> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
