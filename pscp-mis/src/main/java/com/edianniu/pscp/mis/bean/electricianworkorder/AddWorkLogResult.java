package com.edianniu.pscp.mis.bean.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: AddWorkLogResult
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:57
 */
public class AddWorkLogResult extends Result {
    private static final long serialVersionUID = 1L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
