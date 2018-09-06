package com.edianniu.pscp.cs.dao;

import com.edianniu.pscp.cs.bean.workorder.vo.EvaluateVO;
import com.edianniu.pscp.cs.domain.WorkOrderEvaluate;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 10:11:05
 */
public interface WorkOrderEvaluateDao extends BaseDao<WorkOrderEvaluate> {
    void saveWorkOrderEvaluate(WorkOrderEvaluate workOrderEvaluate);

    WorkOrderEvaluate getEvaluateByWorkId(Long workOrderId);
}
