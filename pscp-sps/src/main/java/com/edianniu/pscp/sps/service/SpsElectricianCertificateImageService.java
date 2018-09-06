package com.edianniu.pscp.sps.service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SpsElectricianCertificateImageService
 * Author: tandingbo
 * CreateTime: 2017-05-25 10:24
 */
public interface SpsElectricianCertificateImageService {
    List<Map<String, Object>> queryListMap(Long electricianId);
}
