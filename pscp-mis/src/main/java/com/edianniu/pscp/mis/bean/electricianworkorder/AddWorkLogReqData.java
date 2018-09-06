package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: AddWorkLogReqData
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:56
 */
public class AddWorkLogReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 电工ID
     */
    private Long uid;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 文本
     */
    private String text;
    /**
     * 附件
     */
    private List<FileInfo> files;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }
}
