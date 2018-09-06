package com.edianniu.pscp.mis.bean.electricianworkorder;

import com.edianniu.pscp.mis.bean.Result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月21日 下午4:24:57
 */
public class ElectricianListResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<Map<String, Object>> electricianList;

    public List<Map<String, Object>> getElectricianList() {
        return electricianList;
    }

    public void setElectricianList(List<Map<String, Object>> electricianList) {
        this.electricianList = electricianList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
