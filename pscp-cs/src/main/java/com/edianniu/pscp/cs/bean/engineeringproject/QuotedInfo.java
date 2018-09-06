package com.edianniu.pscp.cs.bean.engineeringproject;

import com.edianniu.pscp.cs.bean.needs.Attachment;

import java.io.Serializable;
import java.util.List;

/**
 * 报价及附件信息
 *
 * @author zhoujianjian
 * 2017年9月20日下午2:07:49
 */
public class QuotedInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String quotedPrice;

    private List<Attachment> attachmentList;

    public String getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(String quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
