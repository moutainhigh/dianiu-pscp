package com.edianniu.pscp.sps.bean.electrician;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.electrician.vo.ElectricianVO;

import java.util.List;

/**
 * ClassName: ElectricianVOResult
 * Author: tandingbo
 * CreateTime: 2017-05-17 10:40
 */
public class ElectricianVOResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<ElectricianVO> electricianList;

    public List<ElectricianVO> getElectricianList() {
        return electricianList;
    }

    public void setElectricianList(List<ElectricianVO> electricianList) {
        this.electricianList = electricianList;
    }
}
