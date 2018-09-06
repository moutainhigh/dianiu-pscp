package com.edianniu.pscp.cs.bean.needs;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;

import java.util.List;

/**
 * ClassName: NeedsMarketDetailsResult
 * Author: tandingbo
 * CreateTime: 2017-09-21 15:12
 */
public class NeedsMarketDetailsResult extends Result {
    private static final long serialVersionUID = 1L;

    /* 需求信息.*/
    private NeedsVO needsInfo;
    /* 响应订单信息.*/
    private NeedsOrderInfo needsOrderInfo;
    /* 合作附件.*/
    private List<Attachment> attachmentList;

    public NeedsVO getNeedsInfo() {
        return needsInfo;
    }

    public void setNeedsInfo(NeedsVO needsInfo) {
        this.needsInfo = needsInfo;
    }

    public NeedsOrderInfo getNeedsOrderInfo() {
        return needsOrderInfo;
    }

    public void setNeedsOrderInfo(NeedsOrderInfo needsOrderInfo) {
        this.needsOrderInfo = needsOrderInfo;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
