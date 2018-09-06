package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.bean.workorder.chieforder.ListQuery;
import com.edianniu.pscp.sps.bean.workorder.chieforder.SelectDataQuery;
import com.edianniu.pscp.sps.bean.workorder.chieforder.WorkOrderInfo;
import com.edianniu.pscp.sps.bean.workorder.chieforder.WorkOrderVOQurey;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.SelectDataVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.WorkOrderVO;
import com.edianniu.pscp.sps.domain.WorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-05 16:20:25
 */
public interface WorkOrderDao extends BaseDao<WorkOrder> {
    List<WorkOrderInfo> queryListWorkOrder(ListQuery listQuery);

    WorkOrder queryObject(@Param("id") Long id, @Param("uid") Long uid, @Param("companyId") Long companyId);

    int queryListWorkOrderCount(ListQuery listQuery);

    Long getWorkOrderSequence();

    WorkOrder getById(Long id);

    WorkOrder queryEntity(Map<String, Object> queryMap);

    WorkOrder getWorkOrderByCondition(Map<String, Object> queryMap);

    Map<String, Object> calculateDistance(@Param("id") Long id, @Param("latitude") Double latitude, @Param("longitude") Double longitude);

    List<SelectDataVO> listSelectDataVO(@Param("companyId") Long companyId, @Param("status") Integer[] status);

    List<SelectDataVO> queryPageSelectData(SelectDataQuery selectDataQuery);

    int querySelectDataCount(SelectDataQuery selectDataQuery);

    int getCountByProjectId(Long projectId);

    List<WorkOrderVO> getWorkOrderVOs(WorkOrderVOQurey workOrderVOQurey);

    /**
     * 此方法仅用于操作修改人信息
     *
     * @param workOrder
     * @return
     */
    int updateModifiedUserInfo(WorkOrder workOrder);
}
