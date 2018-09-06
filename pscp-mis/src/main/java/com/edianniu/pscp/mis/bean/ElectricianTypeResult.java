package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

/**
 * ClassName: ElectricianTypeResult
 * Author: tandingbo
 * CreateTime: 2017-04-13 14:57
 */
public class ElectricianTypeResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否是企业电工(默认True:企业电工，False:社会电工)
     */
    private boolean isCompanyElectrician = true;

    public boolean isCompanyElectrician() {
        return isCompanyElectrician;
    }

    public void setCompanyElectrician(boolean companyElectrician) {
        isCompanyElectrician = companyElectrician;
    }


}
