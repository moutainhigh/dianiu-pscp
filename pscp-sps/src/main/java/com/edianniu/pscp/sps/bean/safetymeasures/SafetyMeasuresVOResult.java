package com.edianniu.pscp.sps.bean.safetymeasures;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;

import java.util.List;

/**
 * ClassName: SafetyMeasuresVOResult
 * Author: tandingbo
 * CreateTime: 2017-05-17 17:15
 */
public class SafetyMeasuresVOResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<SafetyMeasuresVO> safetyMeasuresVOList;

    public List<SafetyMeasuresVO> getSafetyMeasuresVOList() {
        return safetyMeasuresVOList;
    }

    public void setSafetyMeasuresVOList(List<SafetyMeasuresVO> safetyMeasuresVOList) {
        this.safetyMeasuresVOList = safetyMeasuresVOList;
    }
}
