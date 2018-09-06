package com.edianniu.pscp.mis.service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ElectricianCertificateImageService
 * Author: tandingbo
 * CreateTime: 2017-08-04 14:28
 */
public interface ElectricianCertificateImageService {

    List<Map<String, Object>> queryListMap(Long electricianId);
}
