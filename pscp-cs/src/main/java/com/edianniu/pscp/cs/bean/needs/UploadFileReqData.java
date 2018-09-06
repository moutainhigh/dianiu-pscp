package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;
import java.util.List;

/**
 * 合作附件上传编辑
 *
 * @author zhoujianjian
 * 2017年9月18日下午2:53:57
 */
public class UploadFileReqData implements Serializable{

    private static final long serialVersionUID = 1L;

    //用户ID
    private Long uid;
    //需求编号
    private String orderId;
    //要删除的附件id,多个用逗号分隔
    private String removedImgs;
    //合作附件
    private List<Attachment> attachmentList;

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

    public String getRemovedImgs() {
        return removedImgs;
    }

    public void setRemovedImgs(String removedImgs) {
        this.removedImgs = removedImgs;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
