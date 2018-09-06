package com.edianniu.pscp.mis.bean.attachment.common;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * ClassName: QueryListResult
 * Author: tandingbo
 * CreateTime: 2017-09-19 11:36
 */
public class QueryListResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<Map<String, Object>> attachmentList;

    public List<Map<String, Object>> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Map<String, Object>> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
