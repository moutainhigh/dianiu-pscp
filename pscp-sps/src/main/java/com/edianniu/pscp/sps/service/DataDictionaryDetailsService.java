package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;

import java.util.List;

/**
 * ClassName: DataDictionaryDetailsService
 * Author: tandingbo
 * CreateTime: 2017-05-17 17:26
 */
public interface DataDictionaryDetailsService {
    List<SafetyMeasuresVO> selectAllSafetyMeasures();

    List<IdentificationsVO> selectAllIdentifications();
}
