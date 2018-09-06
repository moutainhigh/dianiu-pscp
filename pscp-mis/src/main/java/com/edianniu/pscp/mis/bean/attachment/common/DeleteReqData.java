package com.edianniu.pscp.mis.bean.attachment.common;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: DeleteReqData
 * Author: tandingbo
 * CreateTime: 2017-09-19 11:39
 */
public class DeleteReqData implements Serializable {
    private static final long serialVersionUID = 6469372183741277966L;

    private Long uid;
    private List<Long> attachmentIds;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }
}
