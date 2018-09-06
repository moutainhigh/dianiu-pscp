package com.edianniu.pscp.sps.bean.workorder.chieforder;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: ElectricianWorkOrderInfo
 * Author: tandingbo
 * CreateTime: 2017-05-19 09:33
 */
public class ElectricianWorkOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 检修项目ID
     */
    private String checkOptionId;
    /**
     * 检修项目
     */
    private String checkOption;
    /**
     * 电工ID集合
     */
    private List<Long> electricianIdList;

    public String getCheckOptionId() {
        return checkOptionId;
    }

    public void setCheckOptionId(String checkOptionId) {
        this.checkOptionId = checkOptionId;
    }

    public String getCheckOption() {
        return checkOption;
    }

    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    public List<Long> getElectricianIdList() {
        return electricianIdList;
    }

    public void setElectricianIdList(List<Long> electricianIdList) {
        this.electricianIdList = electricianIdList;
    }
}
