package com.edianniu.pscp.cs.bean.needs;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 需求响应订单信息
 *
 * @author zhoujianjian
 * 2017年9月18日下午9:00:55
 */
public class NeedsOrderInfo implements Serializable {
    private static final long serialVersionUID = 2316729380305892633L;

    private Long id;

    private String orderId;
    
    private Long companyId;

    private String quotedPrice;

    private List<Attachment> quotedAttachmentList;

    private Integer status;
    /**支付状态 */
    private Integer payStatus;
    /**需求ID*/
    @JSONField(serialize = false)
    private Long needsId;
    /**需求保证金*/
    private String cautionMoney;

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

    public List<Attachment> getQuotedAttachmentList() {
        return quotedAttachmentList;
    }

    public void setQuotedAttachmentList(List<Attachment> quotedAttachmentList) {
        this.quotedAttachmentList = quotedAttachmentList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Long getNeedsId() {
        return needsId;
    }

    public void setNeedsId(Long needsId) {
        this.needsId = needsId;
    }

    public String getCautionMoney() {
        return cautionMoney;
    }

    public void setCautionMoney(String cautionMoney) {
        this.cautionMoney = cautionMoney;
    }

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
    
}
