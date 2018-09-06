package com.edianniu.pscp.cs.dao;

import com.edianniu.pscp.cs.bean.workorder.ListQuery;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderDetailsVO;
import com.edianniu.pscp.cs.bean.workorder.vo.WorkOrderVO;
import com.edianniu.pscp.cs.domain.WorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-05 16:20:25
 */
public interface WorkOrderDao extends BaseDao<WorkOrder> {

    int queryListWorkOrderVOCount(ListQuery listQuery);

    List<WorkOrderVO> queryListWorkOrderVO(ListQuery listQuery);

    WorkOrderDetailsVO getWorkOrderDetailsVOByOrderId(String orderId);

    WorkOrder getWorkOrderByOrderId(@Param("orderId") String orderId);

    Integer updateEvaluateInfo(WorkOrder workOrder);
}
