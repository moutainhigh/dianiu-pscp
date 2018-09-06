package com.edianniu.pscp.mis.bean.attachment.common;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: DeleteResult
 * Author: tandingbo
 * CreateTime: 2017-09-19 11:38
 */
public class DeleteResult extends Result {
    private static final long serialVersionUID = 1L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
