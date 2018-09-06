package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianEvaluateAttachment;

import java.util.List;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-26 11:03:26
 */
public interface ElectricianEvaluateAttachmentDao extends BaseDao<ElectricianEvaluateAttachment> {

    List<ElectricianEvaluateAttachment> queryListByEvaluateId(Long evaluateId);

    void saveBatchAttachment(List<ElectricianEvaluateAttachment> attachmentList);
}
