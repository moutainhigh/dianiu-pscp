package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelResult;
import com.edianniu.pscp.sps.bean.socialworkorder.confirm.ConfirmReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.confirm.ConfirmResult;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsResult;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianResult;
import com.edianniu.pscp.sps.bean.socialworkorder.liquidate.*;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListResult;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitResult;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SocialWorkOrderInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-18 09:42
 */
public interface SocialWorkOrderInfoService {

    public List<Long> getAfterExpiryTimeAndUnPayOrders();

    public List<Long> getBeforeExpiryTimeAndUnPayOrders(int hour);

    public List<Long> getAfterExpiryTimeAndPaiedOrders();

    public List<Long> getFinishOrders();

    ListResult list(ListReqData listReqData);

    RecruitResult recruit(RecruitReqData recruitReqData);

    CancelResult cancel(CancelReqData cancelReqData);

    CancelResult cancel(Long id);

    /**
     * 处理未支付并且未到截止时间订单提前提醒
     *
     * @param id
     * @param hour
     * @return
     */
    Result handleBeforeExpiryTimeAndUnPayOrder(Long id, int hour);

    /**
     * 处理已支付并且超过截止时间的订单
     * a)没有响应的工单自动取消,并且需要发起【解冻余额/获取提现的时候操作】
     * c)有响应没有一个审核的，自动取消【有响应的工单改为不符合，解冻余额/获取提现的时候操作】
     * d)有响应，大于等于1个确认符合的，工单状态变为结束，其他未确认的默认不符合。
     *
     * @param id
     * @return
     */
    Result handleAfterExpiryTimeAndPaiedOrder(Long id);

    /**
     * 处理已完成工单：所有社会工单是否都已结算，如果都已结算，修改工单的状态为已结算
     *
     * @param id
     * @return
     */
    Result handleFinishOrder(Long id);

    DetailsResult details(DetailsReqData detailsReqData);

    ConfirmResult confirm(ConfirmReqData confirmReqData);

    /**
     * 服务商APP结算
     *
     * @param liquidateReqData
     * @return
     */
    LiquidateResult liquidate(LiquidateReqData liquidateReqData);

    /**
     * 服务商后台结算
     *
     * @return
     */
//    LiquidateResult liquidateBackground(LiquidateReqData liquidateReqData);

    ElectricianResult electricianWorkOrder(ElectricianReqData electricianReqData);

    LiquidateDetailsResult liquidateDetailsForElectrician(LiquidateDetailsReqData liquidateDetailsReqData);

    /**
     * 服务商后台社会工单结算详情
     *
     * @param liquidateDetailsBackgroundReqData
     * @return
     */
    LiquidateDetailsBackgroundResult liquidateDetailsBackground(LiquidateDetailsBackgroundReqData liquidateDetailsBackgroundReqData);

    /**
     * 社会工单所有状态值与名称
     *
     * @return
     */
    Map<Integer, String> getAllSocialWorkOrderStatus();

    /**
     * 电工工单评价信息
     *
     * @param liquidateEvaluateReqData
     * @return
     */
    LiquidateEvaluateResult liquidateEvaluateForElectrician(LiquidateEvaluateReqData liquidateEvaluateReqData);

	List<String> scanNeedRefundOrders(int limit);
}
