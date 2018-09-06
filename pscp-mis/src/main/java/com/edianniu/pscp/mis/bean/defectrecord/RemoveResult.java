package com.edianniu.pscp.mis.bean.defectrecord;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: RemoveResult
 * Author: tandingbo
 * CreateTime: 2017-09-13 09:54
 */
public class RemoveResult extends Result {
    private static final long serialVersionUID = 1L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
