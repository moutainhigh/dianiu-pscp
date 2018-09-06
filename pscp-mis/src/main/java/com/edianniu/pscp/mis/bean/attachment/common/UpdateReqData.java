package com.edianniu.pscp.mis.bean.attachment.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UpdateReqData
 * Author: tandingbo
 * CreateTime: 2017-09-22 16:00
 */
public class UpdateReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    /**
     * 只支持(fid, orderNum, isOpen)这几个字段值的变更
     */
    private List<Map<String, Object>> attachmentList;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<Map<String, Object>> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Map<String, Object>> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
