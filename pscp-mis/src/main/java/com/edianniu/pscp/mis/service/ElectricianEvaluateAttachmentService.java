package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.ElectricianEvaluateAttachment;

import java.util.List;

/**
 * ClassName: ElectricianEvaluateAttachmentService
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:31
 */
public interface ElectricianEvaluateAttachmentService {
    List<ElectricianEvaluateAttachment> selectAllByEvaluateId(Long evaluateId);
}
