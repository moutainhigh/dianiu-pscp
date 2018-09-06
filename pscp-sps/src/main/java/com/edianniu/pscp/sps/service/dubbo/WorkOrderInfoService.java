package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.workorder.chieforder.*;
import com.edianniu.pscp.sps.domain.SysUserEntity;

/**
 * ClassName: WorkOrderInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:47
 */
public interface WorkOrderInfoService {
    /**
     * 列表
     *
     * @param listReqData
     * @return
     */
    ListResult list(ListReqData listReqData);

    /**
     * 工单取消
     *
     * @param id
     * @param userInfo
     */
    CancelResult cancelWorkOrder(Long id, SysUserEntity userInfo);

    /**
     * 获取工单查看/编辑信息
     *
     * @param orderId
     * @param uid
     * @return
     */
    ViewResult getWorkOrderViewInfo(String orderId, Long uid);

    /**
     * 保存、修改工单信息
     *
     * @param saveOrUpdateInfo
     * @return
     */
    SaveOrUpdateResult saveOrUpdate(SaveOrUpdateInfo saveOrUpdateInfo);

    /**
     * 派单数据初始化
     *
     * @return
     */
    InitDataResult initData(InitDataReqData initDataReqData);

    /**
     * 获取工单下拉数据列表
     *
     * @param selectDataReqData
     * @return
     */
    SelectDataResult listSelectData(SelectDataReqData selectDataReqData);

    /**
     * 获取工单查看/编辑信息（服务商APP）
     *
     * @param orderId
     * @param uid
     * @return
     */
    FacilitatorAppDetailsResult getFacilitatorAppDetails(String orderId, Long uid);
}
