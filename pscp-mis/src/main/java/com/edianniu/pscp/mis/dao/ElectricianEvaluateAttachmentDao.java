package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.ElectricianEvaluateAttachment;

import java.util.List;

/**
 * ClassName: ElectricianEvaluateAttachmentDao
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:23
 */
public interface ElectricianEvaluateAttachmentDao extends BaseDao<ElectricianEvaluateAttachment> {
    List<ElectricianEvaluateAttachment> selectAllByEvaluateId(Long evaluateId);
}
