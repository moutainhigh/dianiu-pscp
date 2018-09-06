package com.edianniu.pscp.cs.bean.needs;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class ResponsedCompany implements Serializable {
    private static final long serialVersionUID = 1L;
    //服务商响应ID
    private Long id;
    //需求ID
    private Long needsId;
    //服务商公司ID
    @JSONField(serialize = false)
    private Long companyId;
    //服务商公司名称
    private String companyName;
    //服务商响应订单编号
    private String orderId;
    //服务商响应状态
    private Integer status;
    //显示时间(yyyy-MM-dd HH:mm:ss)
    private String showTime;
    //服务商报价
    private String quotedPrice;
    
    private List<Attachment> quotedAttachment; 
    //支付状态 0:未支付：1:支付确认 (客户端)；  2:支付成功(服务端异步通知)；  3:支付失败；    4:取消支付
    private Integer payStatus;

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public List<Attachment> getQuotedAttachment() {
		return quotedAttachment;
	}

	public void setQuotedAttachment(List<Attachment> quotedAttachment) {
		this.quotedAttachment = quotedAttachment;
	}

	public String getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(String quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNeedsId() {
        return needsId;
    }

    public void setNeedsId(Long needsId) {
        this.needsId = needsId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
