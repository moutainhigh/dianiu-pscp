package com.edianniu.pscp.mis.bean.request.electricianworkorder;

import com.edianniu.pscp.mis.bean.electricianworkorder.FileInfo;
import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: AddWorkLogRequest
 * Author: tandingbo
 * CreateTime: 2017-04-13 17:24
 */
@JSONMessage(messageCode = 1002023)
public final class AddWorkLogRequest extends TerminalRequest {
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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
