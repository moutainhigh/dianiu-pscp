package com.edianniu.pscp.mis.service.orderpay.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.invoice.InvoiceTitle;
import com.edianniu.pscp.mis.bean.pay.CheckPayOrderResult;
import com.edianniu.pscp.mis.bean.pay.OrderContentInfo;
import com.edianniu.pscp.mis.bean.pay.PayPush;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.pay.PreparePayReq;
import com.edianniu.pscp.mis.bean.pay.PreparePayResult;
import com.edianniu.pscp.mis.bean.pay.StartPayReq;
import com.edianniu.pscp.mis.bean.pay.StartPayResult;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.CompanyRenter;
import com.edianniu.pscp.mis.domain.MemberInvoiceInfo;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.RenterChargeOrder;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.CompanyRenterService;
import com.edianniu.pscp.mis.service.MemberInvoiceService;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.service.RenterChargeOrderService;
import com.edianniu.pscp.mis.service.orderpay.OrderPayService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtils;
import com.edianniu.pscp.mis.util.MoneyUtils;
/**
 * 电费订单支付
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2018年03月28日 下午6:30:35
 */
public class RenterChargeOrderPayService implements OrderPayService {
	private static final Logger logger = LoggerFactory
            .getLogger(RenterChargeOrderPayService.class);
	@Autowired
	private RenterChargeOrderService renterChargeOrderService;
	@Autowired
	private CompanyRenterService companyRenterService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private MemberInvoiceService memberInvoiceService;
	@Override
	public StartPayResult start(StartPayReq startPayReq) {
		StartPayResult result = new StartPayResult();
        String orderIds = startPayReq.getOrderIds();
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
        Long companyId=0L;//租户企业ID
        List<OrderContentInfo> orderContents=new ArrayList<>();
        for (String orderId : ewOrderIds) {
        	RenterChargeOrder renterChargeOrder = renterChargeOrderService.getByOrderId(orderId);
            if (renterChargeOrder == null) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            CompanyRenter companyRenter=companyRenterService.getById(renterChargeOrder.getRenterId());//
            if(companyRenter==null){
            	result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            //检查批量订单是否属于同一个租户
            if(companyId==0){
            	companyId=companyRenter.getCompanyId();
            }
            else{
            	if(companyId.compareTo(companyRenter.getCompanyId())!=0){
            		result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("订单[" + orderId + "]无法支付");
                    return result;
            	}
            }
            if (!companyRenter.getMemberId().equals(startPayReq.getUid())) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if (renterChargeOrder.getPayStatus() == PayStatus.INPROCESSING.getValue() || renterChargeOrder.getPayStatus() == PayStatus.SUCCESS.getValue()) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单已支付，请勿重复操作");
                return result;
            }
            OrderContentInfo orderContentInfo=new OrderContentInfo();
            String title=DateUtils.format(renterChargeOrder.getLastCheckDate(), DateUtils.DATE_PATTERN)+"~"+
            DateUtils.format(renterChargeOrder.getThisCheckDate(), DateUtils.DATE_PATTERN);
            orderContentInfo.setTitle(title);
            orderContentInfo.setAmount(MoneyUtils.format(renterChargeOrder.getCharge()));
            orderContents.add(orderContentInfo);
            payAmount += renterChargeOrder.getCharge();
        }
        //判断金额，如果等于0，则不需要支付
        if (MoneyUtils.lessThanOrEqual(payAmount, 0.00)) {
            result.setResultCode(ResultCode.ERROR_209);
            result.setResultMessage("支付金额为0.00，无需支付");
            return result;
        }
        result.setPayAmount(MoneyUtils.format(payAmount));//设置支付金额
        result.setOrderId(BizUtils.getOrderId("RD"));//设置支付订单编号
        result.setOrderContent(JSON.toJSONString(orderContents));
        return result;
	}

	@Override
	public PreparePayResult prepare(PreparePayReq preparePayReq) {
		PreparePayResult result = new PreparePayResult();
        String orderIds = preparePayReq.getOrderIds();
        Integer needInvoice=preparePayReq.getNeedInvoice();
        if (StringUtils.isBlank(orderIds)) {
            result.setResultCode(ResultCode.ERROR_207);
            result.setResultMessage("orderIds 不能为空");
            return result;
        }
        if(needInvoice==1){//检查发票抬头是否填写了 TODO 
        	if(!memberInvoiceService.isExistInvoiceTitle(preparePayReq.getUid())){
        		result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("发票抬头不存在");
                return result;
        	}
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
        Long companyId=0L;//房东企业ID
        for (String orderId : ewOrderIds) {
        	RenterChargeOrder renterChargeOrder = renterChargeOrderService.getByOrderId(orderId);
            if (renterChargeOrder == null) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            CompanyRenter companyRenter=companyRenterService.getById(renterChargeOrder.getRenterId());//
            if(companyRenter==null){
            	result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            //检查批量订单是否属于同一个租户
            if(companyId==0){
            	companyId=companyRenter.getCompanyId();
            }
            else{
            	if(companyId.compareTo(companyRenter.getCompanyId())!=0){
            		result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("订单[" + orderId + "]无法支付");
                    return result;
            	}
            }
            if (!companyRenter.getMemberId().equals(preparePayReq.getUid())) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单[" + orderId + "]不存在");
                return result;
            }
            if (renterChargeOrder.getPayStatus() == PayStatus.INPROCESSING.getValue() || renterChargeOrder.getPayStatus() == PayStatus.SUCCESS.getValue()) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("订单已支付，请勿重复操作");
                return result;
            }
            
            payAmount += renterChargeOrder.getCharge();
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
        while (!(isUpdated = renterChargeOrderService.updatePayStatus(payOrder))
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
        if(isUpdated&&payOrder.getNeedInvoice()==Constants.TAG_YES){//发票信息
        	InvoiceTitle invoiceTitle=memberInvoiceService.getInvoiceTitleInfoByUid(payOrder.getUid());
         	if(invoiceTitle!=null){
         		MemberInvoiceInfo memberInvoiceInfo=new MemberInvoiceInfo();
             	memberInvoiceInfo.setApplyTime(new Date());
             	memberInvoiceInfo.setApplyUid(payOrder.getUid());
             	memberInvoiceInfo.setOrderId(BizUtils.getOrderId("I"));
             	memberInvoiceInfo.setPayOrderIds(payOrder.getOrderId());
             	memberInvoiceInfo.setStatus(Constants.INVOICE_APPLY_STATUS);
             	memberInvoiceInfo.setTitleBankCardAddress(invoiceTitle.getBankCardAddress());
             	memberInvoiceInfo.setTitleBankCardNo(invoiceTitle.getBankCardNo());
             	memberInvoiceInfo.setTitleContactNumber(invoiceTitle.getContactNumber());
             	memberInvoiceInfo.setTitleName(invoiceTitle.getCompanyName());
             	memberInvoiceInfo.setTitleTaxpayerNo(invoiceTitle.getTaxpayerId());
             	memberInvoiceService.applyInvoice(memberInvoiceInfo);
             	payOrder.setInvoiceOrderId(memberInvoiceInfo.getOrderId());
             	payOrderService.updateInvoiceOrderId(payOrder);
         	}
        }
        return isUpdated;
	}

	@Override
	public boolean updateSyncOrderStatus(PayOrder payOrder) {
		 return  renterChargeOrderService.updatePayStatus(payOrder);
	}

	@Override
	public double getOrderAmount(Integer orderType, String orderId) {
		RenterChargeOrder order = renterChargeOrderService.getByOrderId(orderId);
	     return order == null ? 0.00D : order.getCharge();
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
	public CheckPayOrderResult checkPayOrder(PayOrder payOrder, PayPush payPush)
			throws BusinessException {
		CheckPayOrderResult result=new CheckPayOrderResult();
		String[] orderIds = StringUtils.split(payOrder.getAssociatedOrderIds(), ",");
		double refundAmount=0.00D;
        for (String orderId : orderIds) {
        	RenterChargeOrder needOrder = renterChargeOrderService.getByOrderId(orderId);
            if(needOrder.getPayStatus()==PayStatus.SUCCESS.getValue()||
            		(needOrder.getPayStatus()==PayStatus.INPROCESSING.getValue()&&
            		needOrder.getPayType()!=payOrder.getPayType())){//表示重复支付||取消了还支付的情况
            	refundAmount=refundAmount+needOrder.getCharge();
            }
        }
        result.setRefundAmount(refundAmount);
		return result;
	}

}
