package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.electricianworkorder.*;

/**
 * ClassName: WorkOrderInfoServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-04-13 10:03
 */
public interface ElectricianWorkOrderInfoService {
    /**
     * 电工工单列表
     *
     * @param listReqData
     * @return
     */
    ListResult listWorkOrders(ListReqData listReqData);

    /**
     * 电工工单详情
     *
     * @param detailReqData
     * @return
     */
    DetailResult workOrdersDetail(DetailReqData detailReqData);

    /**
     * 电工工单确认
     *
     * @param confirmReqData
     * @return
     */
    ConfirmResult workOrdersConfirm(ConfirmReqData confirmReqData);

    /**
     * 电工工单取消
     *
     * @param cancelReqData
     * @return
     */
    CancelResult cancelWorkOrder(CancelReqData cancelReqData);

    /**
     * 开始工作
     *
     * @param startWorkReqData
     * @return
     */
    StartWorkResult startWork(StartWorkReqData startWorkReqData);

    /**
     * 结束工作
     *
     * @param endWorkReqData
     * @return
     */
    EndWorkResult endWork(EndWorkReqData endWorkReqData);

    /**
     * 工单新增工作记录
     *
     * @param addWorkLogReqData
     * @return
     */
    AddWorkLogResult addWorkLog(AddWorkLogReqData addWorkLogReqData);

    /**
     * 工单删除工作记录
     *
     * @param delWorkLogReqData
     * @return
     */
    DelWorkLogResult delWorkLog(DelWorkLogReqData delWorkLogReqData);

    /**
     * 删除工作记录附件ID
     *
     * @param attachmentReqData
     * @return
     */
    DelWorkLogAttachmentResult delWorkLogAttachment(DelWorkLogAttachmentReqData attachmentReqData);

    /**
     * 工单工作记录列表
     *
     * @param listWorkLogReqData
     * @return
     */
    ListWorkLogResult listWorkLog(ListWorkLogReqData listWorkLogReqData);

    /**
     * 工单电工列表(企业电工和社会电工)
     *
     * @param electricianListReqData
     * @return
     */
    ElectricianListResult electricianList(ElectricianListReqData electricianListReqData);

    /**
     * 工单结算(社会电工工单)
     *
     * @param settlementReqData
     * @return
     */
    SettlementResult settlementWorkOrder(SettlementReqData settlementReqData);
}
