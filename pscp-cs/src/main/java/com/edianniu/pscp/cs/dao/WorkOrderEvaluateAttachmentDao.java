package com.edianniu.pscp.cs.dao;

import com.edianniu.pscp.cs.domain.WorkOrderEvaluateAttachment;
import org.apache.ibatis.annotations.Param;

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
    void saveBatch(@Param("attachmentList") List<WorkOrderEvaluateAttachment> attachmentList, @Param("evaluateId") Long evaluateId);

    List<Map<String, Object>> listMapByEvaluateId(Long evaluateId);
}
