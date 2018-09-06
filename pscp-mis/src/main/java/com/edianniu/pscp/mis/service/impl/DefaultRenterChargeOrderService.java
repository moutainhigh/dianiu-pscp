package com.edianniu.pscp.mis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.WalleFundType;
import com.edianniu.pscp.mis.bean.WalletDealType;
import com.edianniu.pscp.mis.bean.WalletType;
import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.pay.PayMethod;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.TrueStatus;
import com.edianniu.pscp.mis.dao.RenterChargeOrderDao;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.domain.CompanyRenter;
import com.edianniu.pscp.mis.domain.CompanyRenterConfig;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.RenterChargeOrder;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.service.CompanyRenterService;
import com.edianniu.pscp.mis.service.CompanyService;
import com.edianniu.pscp.mis.service.MemberInvoiceService;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.service.RenterChargeOrderService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.service.UserWalletService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.MoneyUtils;

@Service
@Repository("renterChargeOrderService")
public class DefaultRenterChargeOrderService implements
		RenterChargeOrderService {
	@Autowired
	private RenterChargeOrderDao renterChargeOrderDao;
	@Autowired
	private CompanyRenterService companyRenterService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MemberInvoiceService memberInvoiceService;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private UserService userService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private MessageInfoService messageInfoService;

	@Override
	public RenterChargeOrder getById(Long id) {

		return renterChargeOrderDao.getById(id);
	}

	@Override
	public RenterChargeOrder getByOrderId(String orderId) {

		return renterChargeOrderDao.getByOrderId(orderId);
	}

	@Override
	public RenterChargeOrder getByPayOrderId(String payOrderId) {
		return renterChargeOrderDao.getByPayOrderId(payOrderId);
	}

	@Override
	public int update(RenterChargeOrder renterChargeOrder) {

		return renterChargeOrderDao.update(renterChargeOrder);
	}

	@Override
	public boolean existOrderId(String orderId) {
		int c = renterChargeOrderDao.getCountByOrderId(orderId);
		return c > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean updatePayStatus(PayOrder payOrder) {
		if (payOrder.getOrderType() == OrderType.RENTER_CHARGE_SETTLEMENT
				.getValue()) {
			String[] orderIds = StringUtils.split(
					payOrder.getAssociatedOrderIds(), ",");
			for (String orderId : orderIds) {
				final RenterChargeOrder renterChargeOrder = getByOrderId(orderId);
				if (renterChargeOrder != null) {
					if ((renterChargeOrder.getPayStatus() == PayStatus.INPROCESSING
							.getValue() && payOrder.getStatus() != PayStatus.SUCCESS
							.getValue())
							|| (renterChargeOrder.getPayStatus() == PayStatus.SUCCESS
									.getValue())) {
						continue;
					}
					// renterChargeOrder.setPayAmount(needOrder.getAmount());
					renterChargeOrder.setPayMemo(payOrder.getMemo());
					renterChargeOrder.setPayStatus(payOrder.getStatus());
					renterChargeOrder.setPaySyncTime(payOrder.getPaySyncTime());
					renterChargeOrder.setPayAsyncTime(payOrder
							.getPayAsyncTime());
					renterChargeOrder.setPayTime(payOrder.getPayTime());
					renterChargeOrder.setPayType(payOrder.getPayType());
					renterChargeOrder.setPayOrderId(payOrder.getOrderId());
					int num = update(renterChargeOrder);
					if (num == 0) {
						return false;
					}

					// 发送推送消息
					if (payOrder.getStatus().equals(
							PayStatus.SUCCESS.getValue())) {
						sendMessagePushInfo(renterChargeOrder);
					}
				}
			}
		}
		return true;
	}

	@Override
	public Long getCompanyUidByOrderId(String orderId) {
		RenterChargeOrder renterChargeOrder = getByOrderId(orderId);
		if (renterChargeOrder != null)
			return companyRenterService.getCompanyUidById(renterChargeOrder
					.getRenterId());
		return null;
	}

	@Override
	@Transactional
	public boolean settlementPrepayRenterChargeOrder(
			String orderId) {
		// 更新租客钱包信息
		// 更新企业钱包信息
		// 新增租客资金明细 支出
		// 新增企业资金明细 收入
		// 更新租客账单信息
		
		RenterChargeOrder renterChargeOrder=getByOrderId(orderId);
		if (renterChargeOrder == null) {
			return false;
		}
		CompanyRenter companyRenter = companyRenterService
				.getById(renterChargeOrder.getRenterId());
		if (companyRenter == null) {
			return false;
		}
		Long renterUid = companyRenter.getMemberId();
		UserWallet renterWallet = userWalletService.getByUid(companyRenter
				.getMemberId());
		if (renterWallet == null) {
			return false;
		}
		Company company = companyService.getById(companyRenter.getCompanyId());
		if(company==null){
			return false;
		}
		Long companyUid=company.getMemberId();
		UserWallet companyWallet = userWalletService.getByUid(companyUid);
		if (companyWallet == null) {
			return false;
		}
		if(!MoneyUtils.greaterThan(renterWallet.getAmount(), 0.00D)){
			return false;
		}
		Double payAmount=0.00D;//支付金额
		Double amount = renterWallet.getAmount()
				- (renterChargeOrder.getCharge() - renterChargeOrder
						.getPrepaidCharge());
		if(MoneyUtils.greaterThanOrEqual(amount, 0.00D)){
			payAmount=renterChargeOrder.getCharge() - renterChargeOrder
					.getPrepaidCharge();
		}
		else{
			payAmount=renterWallet.getAmount();
		}
		PayOrder payOrder=null;
		if(StringUtils.isBlank(renterChargeOrder.getPayOrderId())){//支付订单未生成
			payOrder=payOrderService.buildPayOrder(renterUid, BizUtils.getOrderId("RD"), renterChargeOrder.getOrderId(), 0.00D, OrderType.RENTER_CHARGE_SETTLEMENT.getValue(), PayType.WALLET.getValue(), PayMethod.PC.getValue(), "", 0, "");
		}
		else{
			payOrder=payOrderService.getPayOrderByOrderId(renterChargeOrder.getPayOrderId());
		}
		if(MoneyUtils.greaterThan(payAmount, 0.00D)){
			payOrder.setAmount(payOrder.getAmount()+payAmount);
			renterWallet.setAmount(MoneyUtils.sub(renterWallet.getAmount(),
					payAmount));
			companyWallet.setAmount(MoneyUtils.add(companyWallet.getAmount(),
					payAmount));
			//TODO 费用明细是否到月账单结束后产生，还是每次扣都需要产生。
			// 增加电费明细支出
			UserWalletDetail renterUserWalletDetail = new UserWalletDetail();
			renterUserWalletDetail.setUid(renterUid);
			// 账单类型-电费
			renterUserWalletDetail.setType(WalletType.ELECTRIC_CHARGE.getValue());
			// 金额
			renterUserWalletDetail.setAmount(payAmount);
			// 交易类型 - 支出
			renterUserWalletDetail.setDealType(WalletDealType.COSTS.getValue());
			// 订单编号
			renterUserWalletDetail.setOrderId(renterChargeOrder.getOrderId());
			renterUserWalletDetail.setPayOrderId(payOrder.getOrderId());
			// 资金来源
			renterUserWalletDetail.setFundSource(new Long(
					WalleFundType.WALLE_BALANCE.getValue()));
			// 资金去向 -
			renterUserWalletDetail.setFundTarget(new Long(WalleFundType.PLATFORM
					.getValue()));
			// 交易后 钱包可用余额
			renterUserWalletDetail.setAvailableAmount(renterWallet.getAmount());
			// 交易后 钱包冻结金额
			renterUserWalletDetail.setAvailableFreezingAmount(renterWallet
					.getFreezingAmount());
			// 微信/支付宝订单号
			renterUserWalletDetail.setTransactionId(payOrder.getOrderId());
			// 支付者账号(目前只有支付宝有)
			renterUserWalletDetail.setDealAccount("");
			// 备注
			renterUserWalletDetail.setRemark("电费缴费");

			// 增加电费明细收入
			UserWalletDetail companyUserWalletDetail = new UserWalletDetail();
			companyUserWalletDetail.setUid(companyUid);
			// 账单类型-电费
			companyUserWalletDetail.setType(WalletType.ELECTRIC_CHARGE.getValue());
			// 金额
			companyUserWalletDetail.setAmount(payAmount);
			// 交易类型 - 收入
			companyUserWalletDetail.setDealType(WalletDealType.INCOME.getValue());
			// 订单编号
			companyUserWalletDetail.setOrderId(renterChargeOrder.getOrderId());
			companyUserWalletDetail.setPayOrderId(payOrder.getOrderId());
			// 资金来源
			companyUserWalletDetail.setFundSource(new Long(
					WalleFundType.PLATFORM.getValue()));
			// 资金去向 -
			companyUserWalletDetail.setFundTarget(new Long(WalleFundType.WALLE_BALANCE
					.getValue()));
			// 交易后 钱包可用余额
			companyUserWalletDetail.setAvailableAmount(companyWallet.getAmount());
			// 交易后 钱包冻结金额
			companyUserWalletDetail.setAvailableFreezingAmount(companyWallet
					.getFreezingAmount());
			// 微信/支付宝订单号
			companyUserWalletDetail.setTransactionId(payOrder.getOrderId());
			// 支付者账号(目前只有支付宝有)
			companyUserWalletDetail.setDealAccount("");
			// 备注
			companyUserWalletDetail.setRemark("电费缴费");
			userWalletService.addUserWalletDetail(renterUserWalletDetail);
			userWalletService.addUserWalletDetail(companyUserWalletDetail);
			userWalletService.update(renterWallet);
			userWalletService.update(companyWallet);
		}
		renterChargeOrder.setPrepaidCharge(payOrder.getAmount());
		//账单已截止并且总费用和已支付费用相等，表示支付成功
		if(renterChargeOrder.getStatus()==TrueStatus.YES.getValue()&&
				MoneyUtils.equals(renterChargeOrder.getCharge(), renterChargeOrder.getPrepaidCharge())){
			payOrder.setStatus(PayStatus.SUCCESS.getValue());
		}
		payOrder.setPaySyncTime(new Date());
		renterChargeOrder.setPayAmount(payOrder.getAmount());
		renterChargeOrder.setPayAsyncTime(payOrder.getPayAsyncTime());
		renterChargeOrder.setPayMemo(payOrder.getMemo());
		renterChargeOrder.setPayOrderId(payOrder.getOrderId());
		renterChargeOrder.setPayStatus(payOrder.getStatus());
		renterChargeOrder.setPaySyncTime(payOrder.getPaySyncTime());//异步
		renterChargeOrder.setPayTime(payOrder.getPayTime());
		renterChargeOrder.setPayType(payOrder.getPayType());
		renterChargeOrder.setPayOrderId(payOrder.getOrderId());
		payOrderService.updatePayStatus(payOrder);
		renterChargeOrderDao.update(renterChargeOrder);
		sendMessagePushInfo(renterChargeOrder);
		return true;
	}
	/**
     * 创建一个固定线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            // 工作队列
            new SynchronousQueue<Runnable>(),
            // 线程池饱和处理策略
            new ThreadPoolExecutor.CallerRunsPolicy());
    /**
     * 发送推送消息(异步多线程)
     * @param needOrder
     */
    private void sendMessagePushInfo(final RenterChargeOrder renterChargeOrder) {
        if (renterChargeOrder == null || renterChargeOrder.getRenterId() == null) {
            return;
        }
        EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
            	CompanyRenter renter=companyRenterService.getById(renterChargeOrder.getRenterId());
            	if(renter==null){
            		return ;
            	}
            	CompanyRenterConfig renterConfig=companyRenterService.getRenterConfig(renterChargeOrder.getRenterId());
            	if(renterConfig==null){
            		return ;
            	}
            	UserInfo userInfo=userService.getUserInfo(renter.getMemberId());
            	if(userInfo==null){
            		return ;
            	}
            	UserWallet renterWallet = userWalletService.getByUid(userInfo
        				.getUid());
        		if (renterWallet == null) {
        			return ;
        		}
            	Long uid=userInfo.getUid();
            	String mobile=userInfo.getMobile();
            	Map<String,String> params=new HashMap<>();
            	params.put("month",String.valueOf(renterChargeOrder.getThisCheckDate().getMonth()+1));
            	
            	MessageId messageId=null;
            	if(renterConfig.getChargeMode()==Constants.PRE_PAY){//预付费
            		if(renterChargeOrder.getPayStatus()==PayStatus.SUCCESS.getValue()){
            			messageId=MessageId.PRE_PAYMENT_RECHARGE_ORDER_PAY_SUCCESS;
            			params.put("pay_amount", MoneyUtils.format(renterChargeOrder.getCharge()));
            		}
            		else{
            			if(renterChargeOrder.getStatus()==TrueStatus.YES.getValue()&&
            					MoneyUtils.greaterThan(renterChargeOrder.getCharge(), renterChargeOrder.getPrepaidCharge())&&
            					MoneyUtils.equals(renterWallet.getAmount(), 0.00D)){
            				messageId=MessageId.PRE_PAYMENT_RECHARGE_ORDER_PAY_FAIL;
            				//欠费金额
            				params.put("pay_amount", MoneyUtils.format(renterChargeOrder.getPrepaidCharge()));
            				params.put("arrears_amount", MoneyUtils.format(renterChargeOrder.getCharge()- renterChargeOrder.getPrepaidCharge()));
            			}
            			else{
            				return ;
            			}
            			
            		}
            		
            		
            	}
            	else if(renterConfig.getChargeMode()==Constants.MONTH_SETTLE){//月结算
            		if(renterChargeOrder.getPayStatus()!=PayStatus.SUCCESS.getValue()){
                    	return;
                    }
            		messageId=MessageId.MONTHLY_PAYMENT_RECHARGE_ORDER_PAY_SUCCESS;
            		params.put("amount", MoneyUtils.format(renterChargeOrder.getCharge()));
            		
            	}
            	else{
            		return ;
            	}
            	//成功缴费(电费)xxx元 ---月结算
            	//您3月的电费扣款成功
            	//您3月的电费扣款失败，欠费金额XXXX元
            	messageInfoService.sendSmsAndPushMessage(uid, mobile, messageId, params);
            }
        });
    }
    public void walletNotify(final UserWallet wallet,final CompanyRenterConfig renterConfig){
    	if(wallet==null||renterConfig==null){
    		return ;
    	}
    	EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
            	UserInfo userInfo=userService.getUserInfo(wallet.getUid());
            	if(userInfo==null){
            		return ;
            	}
            	Long uid=userInfo.getUid();
            	String mobile=userInfo.getMobile();
            	MessageId messageId=MessageId.WALLET_BALANCE_NOT_ENOUGH;
            	Map<String,String> params=new HashMap<String,String>();
            	params.put("amount", MoneyUtils.format(wallet.getAmount()));
            	params.put("limit_amount", MoneyUtils.format(renterConfig.getAmountLimit()));
            	messageInfoService.sendSmsAndPushMessage(uid, mobile, messageId, params);
            }
		
	});
    }
}
