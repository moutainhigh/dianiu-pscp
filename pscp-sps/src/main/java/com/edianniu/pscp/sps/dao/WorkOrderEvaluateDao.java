package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData;
import com.edianniu.pscp.sps.domain.WorkOrderEvaluate;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 10:11:05
 */
public interface WorkOrderEvaluateDao extends BaseDao<WorkOrderEvaluate> {

    List<WorkOrderEvaluate> selectAllByWorkOrderId(Long workOrderId);

    List<WorkOrderEvaluate> queryListEvaluate(ListReqData listReqData);

    int queryListEvaluateCount(Map<String, Object> map);
}
