package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.WorkOrderEvaluateAttachment;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 10:11:05
 */
public interface WorkOrderEvaluateAttachmentDao extends BaseDao<WorkOrderEvaluateAttachment> {

    List<Map<String, Object>> selectMapByEvaluateId(Long workOrderEvaluateId);
}
