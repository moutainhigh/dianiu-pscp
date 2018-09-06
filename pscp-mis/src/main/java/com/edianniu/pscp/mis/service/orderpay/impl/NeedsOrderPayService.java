package com.edianniu.pscp.mis.service.orderpay.impl;

import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.NeedsStatus;
import com.edianniu.pscp.mis.bean.pay.*;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.Needs;
import com.edianniu.pscp.mis.domain.NeedsOrder;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.service.NeedsOrderService;
import com.edianniu.pscp.mis.service.NeedsService;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.service.orderpay.OrderPayService;
import com.edianniu.pscp.mis.util.MoneyUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 响应需求订单订金支付
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 下午4:46:35
 */
public class NeedsOrderPayService implements OrderPayService {
    private static final Logger logger = LoggerFactory
            .getLogger(NeedsOrderPayService.class);
    @Autowired
    private NeedsOrderService needsOrderService;
    @Autowired
    private NeedsService needsService;
    @Autowired
    private PayOrderService payOrderService;

    @Override
    public StartPayResult start(StartPayReq startPayReq) {
        StartPayResult result = new StartPayResult();
        String orderIds = startPayReq.getOrderIds();
        Long companyId = startPayReq.getCompanyId();
        if (StringUtils.isBlank(orderIds)) {
            result.setResultCode(ResultCode.ERROR_207);
            result.setResultMessage("orderIds 不能为空");
            return result;
        }
        String[] ewOrderIds = StringUtils.split(orderIds, ",");
        Set<String> orderIdSet = new HashSet<String>();
        for (String orderId : ewOrderIds) {
            orderIdSet.add(orderId);
        }
        if (orderIdSet.size() != ewOrderIds.length) {
            result.setResultCode(ResultCode.ERROR_207);
            result.setResultMessage("orderIds 有重复记录");
            return result;
        }
        Double payAmount = 0.00D;
        for (String orderId : ewOrderIds) {
            NeedsOrder needsOrder = needsOrderService.getNeedsOrderByOrderId(orderId);
            if (needsOrder == null) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if (!needsOrder.getCompanyId().equals(companyId)) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if (needsOrder.getStatus() != NeedsOrderStatus.RESPONED.getValue()) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单状态不正确");
                return result;
            }
            if (needsOrder.getPayStatus() == PayStatus.INPROCESSING.getValue() || needsOrder.getPayStatus() == PayStatus.SUCCESS.getValue()) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单已支付，请勿重复操作");
                return result;
            }
            Needs needs = needsService.getById(needsOrder.getNeedsId());
            if (needs == null) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("需求不存在");
                return result;
            }
            if (needs.getStatus() != NeedsStatus.AUDIT_SUCCESS.getValue()) {//响应中
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("需求状态不正确");
                return result;
            }
            payAmount += needsOrder.getAmount();
        }
        //判断金额，如果等于0，则不需要支付
        if (MoneyUtils.lessThanOrEqual(payAmount, 0.00)) {
            result.setResultCode(ResultCode.ERROR_209);
            result.setResultMessage("支付金额为0.00，无需支付");
            return result;
        }
        result.setPayAmount(MoneyUtils.format(payAmount));//设置支付金额
        return result;
    }

    @Override
    public PreparePayResult prepare(PreparePayReq preparePayReq) {
        PreparePayResult result = new PreparePayResult();
        String orderIds = preparePayReq.getOrderIds();
        Long companyId = preparePayReq.getCompanyId();
        if (StringUtils.isBlank(orderIds)) {
            result.setResultCode(ResultCode.ERROR_207);
            result.setResultMessage("orderIds 不能为空");
            return result;
        }
        String[] ewOrderIds = StringUtils.split(orderIds, ",");
        Set<String> orderIdSet = new HashSet<String>();
        for (String orderId : ewOrderIds) {
            orderIdSet.add(orderId);
        }
        if (orderIdSet.size() != ewOrderIds.length) {
            result.setResultCode(ResultCode.ERROR_207);
            result.setResultMessage("orderIds 有重复记录");
            return result;
        }
        Double payAmount = 0.00D;
        for (String orderId : ewOrderIds) {
            NeedsOrder needsOrder = needsOrderService.getNeedsOrderByOrderId(orderId);
            if (needsOrder == null) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if (!needsOrder.getCompanyId().equals(companyId)) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if (needsOrder.getStatus() != NeedsOrderStatus.RESPONED.getValue()) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单状态不正确");
                return result;
            }
            if (needsOrder.getPayStatus() == PayStatus.INPROCESSING.getValue() || needsOrder.getPayStatus() == PayStatus.SUCCESS.getValue()) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单已支付，请勿重复操作");
                return result;
            }
            Needs needs = needsService.getById(needsOrder.getNeedsId());
            if (needs == null) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("需求不存在");
                return result;
            }
            if (needs.getStatus() != NeedsStatus.AUDIT_SUCCESS.getValue()) {//响应中
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("需求状态不正确");
                return result;
            }
            payAmount += needsOrder.getAmount();
        }
        if (!MoneyUtils.equals(payAmount, Double.parseDouble(preparePayReq.getAmount()))) {
            result.setResultCode(ResultCode.ERROR_209);
            result.setResultMessage("支付金额不正确");
            return result;
        }
        return result;
    }

    @Override
    public boolean updateOrderStatus(PayOrder order) {
        int MODIFY_MAX_TRY = 3;
        int tryIndex = 0;
        boolean isUpdated = false;
        PayOrder payOrder = payOrderService.getPayOrderByOrderId(order.getOrderId());
        if (payOrder == null) {
            return false;
        }
        while (!(isUpdated = needsOrderService.updatePayStatus(payOrder))
                && tryIndex < MODIFY_MAX_TRY) {
            logger.error(String.format("更新社会工单状态![%s]已尝试%s次", order.getOrderId(), tryIndex));
            // 尝试次数
            tryIndex++;
            logger.error(String.format("更新社会工单状态![%s]第%s次尝试", order.getOrderId(), tryIndex));
            try {
                // 两个进程同时操作时,并且产生脏数据时，
                // 线程停止100ms，继续执行一次
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
        return isUpdated;
    }

    @Override
    public double getOrderAmount(Integer orderType, String orderId) {
        NeedsOrder order = needsOrderService.getNeedsOrderByOrderId(orderId);
        return order == null ? 0.00D : order.getAmount();
    }

	@Override
	public boolean updateSyncOrderStatus(PayOrder payOrder) {
        return  needsOrderService.updatePayStatus(payOrder);
	}

	@Override
	public boolean updateUserWallet(PayOrder payOrder) {
		return false;
	}

	@Override
	public boolean addUserWalletDetail(PayOrder payOrder) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CheckPayOrderResult checkPayOrder(PayOrder payOrder,PayPush payPush) {
		CheckPayOrderResult result=new CheckPayOrderResult();
		String[] orderIds = StringUtils.split(payOrder.getAssociatedOrderIds(), ",");
		double refundAmount=0.00D;
        for (String orderId : orderIds) {
            NeedsOrder needOrder = needsOrderService.getNeedsOrderByOrderId(orderId);
            if((needOrder.getStatus()==NeedsOrderStatus.CANCEL.getValue()&&
            		(needOrder.getPayStatus()==PayStatus.CANCLE.getValue()||
            		needOrder.getPayStatus()==PayStatus.FAIL.getValue()||
            		needOrder.getPayStatus()==PayStatus.UNPAY.getValue()))||
            		needOrder.getPayStatus()==PayStatus.SUCCESS.getValue()||
            		(needOrder.getPayStatus()==PayStatus.INPROCESSING.getValue()&&
            		needOrder.getPayType()!=payOrder.getPayType())){//表示重复支付||取消了还支付的情况
            	refundAmount=refundAmount+needOrder.getAmount();
            }
        }
        result.setRefundAmount(refundAmount);
		return result;
	}

}
