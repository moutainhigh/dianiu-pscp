package com.edianniu.pscp.mis.bean.attachment.common;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * ClassName: QueryOneResult
 * Author: tandingbo
 * CreateTime: 2017-09-19 11:42
 */
public class QueryOneResult extends Result {
    private static final long serialVersionUID = 1L;

    private Map<String, Object> attachment;

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
