package com.edianniu.pscp.mis.service.orderpay.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderStatus;
import com.edianniu.pscp.mis.bean.electricianworkorder.SettlementPayStatus;
import com.edianniu.pscp.mis.bean.pay.CheckPayOrderResult;
import com.edianniu.pscp.mis.bean.pay.PayPush;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.pay.PreparePayReq;
import com.edianniu.pscp.mis.bean.pay.PreparePayResult;
import com.edianniu.pscp.mis.bean.pay.StartPayReq;
import com.edianniu.pscp.mis.bean.pay.StartPayResult;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderStatus;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.ElectricianWorkOrder;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.SocialWorkOrder;
import com.edianniu.pscp.mis.service.ElectricianWorkOrderService;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.service.SocialWorkOrderService;
import com.edianniu.pscp.mis.service.orderpay.OrderPayService;
import com.edianniu.pscp.mis.util.MoneyUtils;

/**社会电工工单结算支付
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 下午4:46:35 
 * @version V1.0
 */
public class SocialElectricianWorkOrderPayService implements OrderPayService {
	private static final Logger logger = LoggerFactory
			.getLogger(SocialElectricianWorkOrderPayService.class);
	@Autowired
	private ElectricianWorkOrderService electricianWorkOrderService;
	@Autowired
	private SocialWorkOrderService socialWorkOrderService;
	@Autowired
	private PayOrderService payOrderService;
	@Override
	public StartPayResult start(StartPayReq startPayReq) {
		StartPayResult result=new StartPayResult();
		String orderIds=startPayReq.getOrderIds();
		Long uid=startPayReq.getUid();
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
        SocialWorkOrder order =null;
        for(String orderId:ewOrderIds){
        	ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getByOrderId(orderId);
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if(order==null){
            	order=socialWorkOrderService.getEntityById(electricianWorkOrder.getSocialWorkOrderId());
            	if(order==null){
            		result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("社会工单信息异常");
                    return result;
            	}
            	if(order.getStatus()!=SocialWorkOrderStatus.FINISHED.getValue()){
            		result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("社会工单未完成，无法结算");
                    return result;
            	}
            }
            else{
            	if(!order.getId().equals(electricianWorkOrder.getSocialWorkOrderId())){
            		result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("订单[" + orderId + "] 不属于同一个社会工单");
                    return result;
            	}
            }
            
            if (electricianWorkOrder.getCompanyId().compareTo(startPayReq.getCompanyId()) != 0) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.CANCELED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已经取消，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.FAIL.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已取消，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.UNCONFIRMED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]待确认，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.CONFIRMED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已确认，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.EXECUTING.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]进行中，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.FINISHED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已完成，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.LIQUIDATED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已结算，无法结算");
                return result;
            }
            if(!(electricianWorkOrder.getStatus()==ElectricianWorkOrderStatus.FEE_CONFIRM.getValue()
                &&electricianWorkOrder.getSettlementPayStatus()==0)){//费用确认并且结算未支付的情况下
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已费用确认结算已支付，无法结算");
                return result;
            }
        }
        
        //long rs=jedisUtil.setnx(orderId, orderId, 1);
        Double payAmount =socialWorkOrderService.getInsufficientSettlementAmount(uid,order);
        //判断金额，如果等于0，则不需要支付
        if(MoneyUtils.lessThanOrEqual(payAmount, 0.00)){
        	result.setResultCode(ResultCode.ERROR_209);
            result.setResultMessage("支付金额为0.00，无需支付");
            return result;
        }
        result.setPayAmount(MoneyUtils.format(payAmount));//设置支付金额
		return result;
	}
	@Override
	public PreparePayResult prepare(PreparePayReq preparePayReq) {
		PreparePayResult result=new PreparePayResult();
		String orderIds=preparePayReq.getOrderIds();
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
        SocialWorkOrder order =null;
        for(String orderId:ewOrderIds){
        	ElectricianWorkOrder electricianWorkOrder = electricianWorkOrderService.getByOrderId(orderId);
            if (electricianWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if(order==null){
            	order=socialWorkOrderService.getEntityById(electricianWorkOrder.getSocialWorkOrderId());
            	if(order==null){
            		result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("社会工单信息异常");
                    return result;
            	}
            	if(order.getStatus()!=SocialWorkOrderStatus.FINISHED.getValue()){
            		result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("社会工单未完成，无法结算");
                    return result;
            	}
            }
            else{
            	if(!order.getId().equals(electricianWorkOrder.getSocialWorkOrderId())){
            		result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("订单[" + orderId + "] 不属于同一个社会工单");
                    return result;
            	}
            }
            
            if (electricianWorkOrder.getCompanyId().compareTo(preparePayReq.getCompanyId()) != 0) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.CANCELED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已经取消，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.FAIL.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已取消，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.UNCONFIRMED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]待确认，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.CONFIRMED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已确认，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.EXECUTING.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]进行中，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.FINISHED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已完成，无法结算");
                return result;
            }
            if (electricianWorkOrder.getStatus() == ElectricianWorkOrderStatus.LIQUIDATED.getValue()) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已结算，无法结算");
                return result;
            }
            if(!(electricianWorkOrder.getStatus()==ElectricianWorkOrderStatus.FEE_CONFIRM.getValue()
                &&electricianWorkOrder.getSettlementPayStatus()==0)){//费用确认并且结算未支付的情况下
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("订单[" + orderId + "]已费用确认结算已支付，无法结算");
                return result;
            }
        }
        
        //long rs=jedisUtil.setnx(orderId, orderId, 1);
        Double payAmount =socialWorkOrderService.getInsufficientSettlementAmount(preparePayReq.getUid(),order);
        if (!MoneyUtils.equals(payAmount, Double.parseDouble(preparePayReq.getAmount()))) {
            result.setResultCode(ResultCode.ERROR_209);
            result.setResultMessage("支付金额不正确");
            return result;
        }
		return result;
	}
	@Override
	public boolean updateOrderStatus(PayOrder order) {
		int MODIFY_MAX_TRY=3;
		int tryIndex = 0;
		boolean isUpdated=false;
	    PayOrder payOrder=payOrderService.getPayOrderByOrderId(order.getOrderId());
		if(payOrder==null){
			return false;
		}
		while (!(isUpdated = electricianWorkOrderService.updateSettlementPayStatus(payOrder))
				&& tryIndex < MODIFY_MAX_TRY) {
			logger.error(String.format("更新社会电工工单状态![%s]已尝试%s次", order.getOrderId(), tryIndex));
			// 尝试次数
			tryIndex++;
			logger.error(String.format("更新社会电工工单状态![%s]第%s次尝试", order.getOrderId(), tryIndex));
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
		//忽略
		return 0;
	}
	@Override
	public boolean updateSyncOrderStatus(PayOrder payOrder) {
		
		return electricianWorkOrderService.updateSettlementPayStatus(payOrder);
	}
	@Override
	public boolean updateUserWallet(PayOrder payOrder) {
		// TODO Auto-generated method stub
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
		return result;
	}

}
