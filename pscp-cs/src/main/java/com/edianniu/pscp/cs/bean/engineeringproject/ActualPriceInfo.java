package com.edianniu.pscp.cs.bean.engineeringproject;

import com.edianniu.pscp.cs.bean.needs.Attachment;

import java.io.Serializable;
import java.util.List;

/**
 * 实际结算金额及附件
 *
 * @author zhoujianjian
 * 2017年9月19日下午11:09:22
 */
public class ActualPriceInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String actualPrice;

    private List<Attachment> attachmentList;

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
