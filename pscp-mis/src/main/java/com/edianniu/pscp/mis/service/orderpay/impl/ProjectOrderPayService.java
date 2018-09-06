package com.edianniu.pscp.mis.service.orderpay.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.edianniu.pscp.mis.bean.EngineeringProjectStatus;
import com.edianniu.pscp.mis.bean.pay.CheckPayOrderResult;
import com.edianniu.pscp.mis.bean.pay.PayPush;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.pay.PreparePayReq;
import com.edianniu.pscp.mis.bean.pay.PreparePayResult;
import com.edianniu.pscp.mis.bean.pay.StartPayReq;
import com.edianniu.pscp.mis.bean.pay.StartPayResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.EngineeringProject;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.service.CompanyInvitationService;
import com.edianniu.pscp.mis.service.EngineeringProjectService;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.service.orderpay.OrderPayService;
import com.edianniu.pscp.mis.util.MoneyUtils;

/**
 * 项目结算支付
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 下午4:46:35 
 * @version V1.0
 */
public class ProjectOrderPayService implements OrderPayService {
	private static final Logger logger = LoggerFactory
			.getLogger(NeedsOrderPayService.class);
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private EngineeringProjectService engineeringProjectService;
	@Autowired
	private CompanyInvitationService companyInvitationService;
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
        Double payAmount=0.00D;
        for(String orderId:ewOrderIds){
        	EngineeringProject engineeringProject = engineeringProjectService.getByProjectNo(orderId);
            if (engineeringProject == null) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("项目[" + orderId + "]不存在");
                return result;
            }
            Long customerId=companyInvitationService.getCustomerId(uid, engineeringProject.getCompanyId());
            if(customerId==null){
            	result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("项目[" + orderId + "]不存在");
                return result;
            }
            if (engineeringProject.getCustomerId().compareTo(customerId) != 0) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("项目[" + orderId + "]不存在");
                return result;
            }
            if(engineeringProject.getStatus()==EngineeringProjectStatus.COFIRMING.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目确认中，无法支付");
                return result;
            }
            if(engineeringProject.getStatus()==EngineeringProjectStatus.EXECUTING.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目进行中，无法支付");
                return result;
            }
            if(engineeringProject.getStatus()==EngineeringProjectStatus.SETTLED.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("费用已结算，无需支付");
                return result;
            }
            if(engineeringProject.getStatus()==EngineeringProjectStatus.CANCLE.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目已取消，无需支付");
                return result;
            }
            if(engineeringProject.getStatus()!=EngineeringProjectStatus.COST_TO_BE_CONFIRMED.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目费用未确认，无法支付");
                return result;
            }
            if(engineeringProject.getPayStatus()==PayStatus.INPROCESSING.getValue()||engineeringProject.getPayStatus()==PayStatus.SUCCESS.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目已结算，无需支付");
                return result;
            }
            payAmount+=engineeringProject.getActualSettlementAmount();
        }
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
		Long uid=preparePayReq.getUid();
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
        Double payAmount=0.00D;
        for(String orderId:ewOrderIds){
        	EngineeringProject engineeringProject = engineeringProjectService.getByProjectNo(orderId);
            if (engineeringProject == null) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("项目[" + orderId + "]不存在");
                return result;
            }
            Long customerId=companyInvitationService.getCustomerId(uid, engineeringProject.getCompanyId());
            if(customerId==null){
            	result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("项目[" + orderId + "]不存在");
                return result;
            }
            if (engineeringProject.getCustomerId().compareTo(customerId) != 0) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("项目[" + orderId + "]不存在");
                return result;
            }
            if(engineeringProject.getStatus()==EngineeringProjectStatus.COFIRMING.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目确认中，无法支付");
                return result;
            }
            if(engineeringProject.getStatus()==EngineeringProjectStatus.EXECUTING.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目进行中，无法支付");
                return result;
            }
            if(engineeringProject.getStatus()==EngineeringProjectStatus.SETTLED.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("费用已结算，无需支付");
                return result;
            }
            if(engineeringProject.getStatus()==EngineeringProjectStatus.CANCLE.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目已取消，无需支付");
                return result;
            }
            if(engineeringProject.getStatus()!=EngineeringProjectStatus.COST_TO_BE_CONFIRMED.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目费用未确认，无法支付");
                return result;
            }
            if(engineeringProject.getPayStatus()==PayStatus.INPROCESSING.getValue()||engineeringProject.getPayStatus()==PayStatus.SUCCESS.getValue()){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("项目已结算，无需支付");
                return result;
            }
            payAmount+=engineeringProject.getActualSettlementAmount();
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
		 int MODIFY_MAX_TRY=3;
		    int tryIndex = 0;
		    boolean isUpdated=false;
		    PayOrder payOrder=payOrderService.getPayOrderByOrderId(order.getOrderId());
			if(payOrder==null){
				return false;
			}
			while (!(isUpdated = engineeringProjectService.updatePayStatus(payOrder))
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
		EngineeringProject project=engineeringProjectService.getByProjectNo(orderId);
		return project==null?0.00D:project.getActualSettlementAmount();
	}
	@Override
	public boolean updateSyncOrderStatus(PayOrder payOrder) {
		return engineeringProjectService.updatePayStatus(payOrder);
	}
	@Override
	public boolean updateUserWallet(PayOrder payOrder) {
		
		return false;
	}
	@Override
	public boolean addUserWalletDetail(PayOrder payOrder) {
		
		return false;
	}
	@Override
	public CheckPayOrderResult checkPayOrder(PayOrder payOrder,PayPush payPush) {
		CheckPayOrderResult result=new CheckPayOrderResult();
		String[] orderIds = StringUtils.split(payOrder.getAssociatedOrderIds(), ",");
		double refundAmount=0.00D;
        for (String orderId : orderIds) {
        	EngineeringProject engineeringProject = engineeringProjectService.getByProjectNo(orderId);
            if(engineeringProject.getPayStatus()==PayStatus.SUCCESS.getValue()||
            		(engineeringProject.getPayStatus()==PayStatus.INPROCESSING.getValue()&&engineeringProject.getPayType()!=payOrder.getPayType())){
            	refundAmount=refundAmount+engineeringProject.getActualSettlementAmount();
            }
        }
        result.setRefundAmount(refundAmount);
		return result;
	}

}
