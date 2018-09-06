package com.edianniu.pscp.sps.bean.workorder.chieforder.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ClassName: CompanyWorkOrderVO
 * Author: tandingbo
 * CreateTime: 2017-05-16 16:59
 */
public class CompanyWorkOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 检修项目
     */
    private String checkOption;
    /**
     * 企业电工ID
     */
    private List<Long> electricianIds;
    /**
     * 企业电工姓名
     */
    private List<String> electricianNames;

    public String getCheckOption() {
        return checkOption;
    }

    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    public List<Long> getElectricianIds() {
        return electricianIds;
    }

    public void setElectricianIds(List<Long> electricianIds) {
        this.electricianIds = electricianIds;
    }

    public List<String> getElectricianNames() {
        return electricianNames;
    }

    public void setElectricianNames(List<String> electricianNames) {
        this.electricianNames = electricianNames;
    }
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
