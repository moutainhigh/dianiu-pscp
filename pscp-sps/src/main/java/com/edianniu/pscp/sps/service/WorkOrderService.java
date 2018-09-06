package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.sps.bean.workorder.chieforder.*;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.SelectDataVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.WorkOrderVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.domain.SysUserEntity;
import com.edianniu.pscp.sps.domain.WorkOrder;

import java.util.List;
import java.util.Map;

/**
 * ClassName: WorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:48
 */
public interface WorkOrderService {
    PageResult<WorkOrderInfo> selectPageWorkOrderInfo(ListQuery listQuery);

    CancelResult cancelWorkOrder(Long id, SysUserEntity userInfo);

    ViewResult getWorkOrderViewInfo(String orderId, UserInfo userInfo);

    SaveOrUpdateResult saveWorkOrderInfo(SaveOrUpdateInfo saveOrUpdateInfo) throws Exception;

    SaveOrUpdateResult updateWorkOrderInfo(SaveOrUpdateInfo saveOrUpdateInfo) throws Exception;

    WorkOrder queryObject(Long workOrderId, Long uid, Long companyId);

    WorkOrder getById(Long id);

    public boolean existByProjectId(Long projectId);

    WorkOrder getWorkOrderByCondition(Map<String, Object> queryMap);

    PageResult<SelectDataVO> queryPageSelectData(SelectDataQuery selectDataQuery);

    FacilitatorAppDetailsResult getFacilitatorAppDetails(String orderId, UserInfo userInfo);

    List<WorkOrderVO> getWorkOrderVOs(WorkOrderVOQurey workOrderVOQurey);

    int querySelectDataCount(SelectDataQuery selectDataQuery);

    /**
     * 此方法仅用于操作修改人信息
     *
     * @param workOrder
     * @return
     */
    int updateModifiedUserInfo(WorkOrder workOrder);
}
