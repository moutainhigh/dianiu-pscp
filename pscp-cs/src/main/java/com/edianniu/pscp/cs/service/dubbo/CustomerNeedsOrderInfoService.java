package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needsorder.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CustomerNeedsOrderInfoService
 * Author: tandingbo
 * CreateTime: 2017-09-25 09:55
 */
public interface CustomerNeedsOrderInfoService {

    /**
     * 获取响应订单信息
     *
     * @param reqData
     * @return
     */
    NeedsOrderResult getNeedsOrderInfo(NeedsOrderReqData reqData);

    /**
     * 跟据条件获取响应订单信息
     *
     * @param reqData
     * @return
     */
    ListNeedsOrderResult listAllNeedsOrder(ListNeedsOrderReqData reqData);

    /**
     * 已响应订单列表
     *
     * @param reqData
     * @return
     */
    ListResult list(ListReqData reqData);

    /**
     * 保存响应订单
     *
     * @param reqData
     * @return
     */
    SaveResult save(SaveReqData reqData);

    /**
     * 需求报价
     *
     * @param reqData
     * @return
     */
    OfferResult offer(OfferReqData reqData);

    /**
     * 取消响应订单
     *
     * @param reqData
     * @return
     */
    CancelResult cancel(CancelReqData reqData);

    /**
     * 更新订单信息(订单支付)
     *
     * @param reqData
     * @return
     */
    UpdateResult updateNeedsOrderVO(UpdateReqData reqData);

    /**
     * 扫描超时支付保证金的响应订单
     */
    List<Long> getOvertimeAndUnPayNeedsorders(int minutes);

    /**
     * 处理超时支付保证金的响应订单
     * @param id
     * @return
     */
    Result handleOvertimeAndUnPayNeedsorders(Long id);

    /**
     * 根据需求订单ID获取响应订单服务商推送信息
     * @param id 需求订单ID
     * @return
     */
    Map<String, Object> getMapSendMessagePushInfo(Long id);

    /**
     * 扫描所有取消、不符合、不合作的需求订单（已支付且未退款）
     * @return
     */
	List<String> getNotGoWellNeedsorders(int limit);

}
