package com.edianniu.pscp.mis.service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ElectricianCertificateService
 * Author: tandingbo
 * CreateTime: 2017-08-04 14:19
 */
public interface ElectricianCertificateService {
    List<Map<String, Object>> queryListMap(Long electricianId);
}
