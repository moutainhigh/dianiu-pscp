package com.edianniu.pscp.mis.bean.electrician;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月14日 上午11:53:20 
 * @version V1.0
 */
public class AuditResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
