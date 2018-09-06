package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.identifications.IdentificationsVOResult;
import com.edianniu.pscp.sps.bean.safetymeasures.SafetyMeasuresVOResult;

/**
 * ClassName: DataDictionaryDetailsInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-17 17:29
 */
public interface DataDictionaryDetailsInfoService {
    SafetyMeasuresVOResult selectAllSafetyMeasures();

    IdentificationsVOResult selectAllIdentifications();
}
