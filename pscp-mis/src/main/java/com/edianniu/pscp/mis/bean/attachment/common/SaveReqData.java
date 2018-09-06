package com.edianniu.pscp.mis.bean.attachment.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SaveReqData
 * Author: tandingbo
 * CreateTime: 2017-09-19 11:33
 */
public class SaveReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
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
