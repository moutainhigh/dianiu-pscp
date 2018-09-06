package com.edianniu.pscp.sps.service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SpsElectricianCertificateService
 * Author: tandingbo
 * CreateTime: 2017-05-17 14:01
 */
public interface SpsElectricianCertificateService {

    List<Map<String, Object>> queryListMap(Long electricianId);
}
