package com.edianniu.pscp.mis.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.NeedsOrderRefundStatus;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.WalletDealType;
import com.edianniu.pscp.mis.bean.WalleFundType;
import com.edianniu.pscp.mis.bean.WalletType;
import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.dao.MemberDao;
import com.edianniu.pscp.mis.dao.NeedsDao;
import com.edianniu.pscp.mis.dao.NeedsOrderDao;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.domain.Member;
import com.edianniu.pscp.mis.domain.Needs;
import com.edianniu.pscp.mis.domain.NeedsOrder;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.service.CompanyService;
import com.edianniu.pscp.mis.service.NeedsOrderService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.service.UserWalletService;
import com.edianniu.pscp.mis.util.MoneyUtils;

/**
 * DefaultNeedsOrderService
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午2:49:34
 */
@Service
@Repository("needsOrderService")
public class DefaultNeedsOrderService implements NeedsOrderService {
	private static final Logger logger = LoggerFactory
            .getLogger(DefaultNeedsOrderService.class);
    @Autowired
    private NeedsDao needsDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private NeedsOrderDao needsOrderDao;
    @Autowired
    private MessageInfoService messageInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private CompanyService companyService;

    @Override
    public NeedsOrder getById(Long id) {
        return needsOrderDao.getById(id);
    }

    @Override
    public NeedsOrder getNeedsOrder(Long companyId, String orderId) {
        return needsOrderDao.getCompanyIdAndOrderId(companyId, orderId);
    }

    @Override
    public NeedsOrder getNeedsOrderByOrderId(String orderId) {
        return needsOrderDao.getByOrderId(orderId);
    }

    @Override
    public int update(NeedsOrder needsOrder) {
        return needsOrderDao.update(needsOrder);
    }

    @Override
    public boolean existOrderId(String orderId) {
        int count = needsOrderDao.getCountByOrderId(orderId);
        return count > 0 ? true : false;
    }

    /**
     * 需求响应支付保证金并发送信息
     */
    @Override
    @Transactional
    public boolean updatePayStatus(PayOrder payOrder) {
        if (payOrder.getOrderType() == OrderType.NEEDS_ORDER_PAY.getValue()) {
            String[] orderIds = StringUtils.split(payOrder.getAssociatedOrderIds(), ",");
            for (String orderId : orderIds) {
                final NeedsOrder needOrder = this.getNeedsOrderByOrderId(orderId);
                if (needOrder != null && needOrder.getStatus() == NeedsOrderStatus.RESPONED.getValue()) {
                	if((needOrder.getPayStatus()==PayStatus.INPROCESSING.getValue()&&payOrder.getStatus()!=PayStatus.SUCCESS.getValue())
                			||(needOrder.getPayStatus()==PayStatus.SUCCESS.getValue())){
                		continue;
                	}
                    needOrder.setPayAmount(needOrder.getAmount());
                    needOrder.setPayMemo(payOrder.getMemo());
                    needOrder.setPayStatus(payOrder.getStatus());
                    needOrder.setPaySyncTime(payOrder.getPaySyncTime());
                    needOrder.setPayAsyncTime(payOrder.getPayAsyncTime());
                    needOrder.setPayTime(payOrder.getPayTime());
                    needOrder.setPayType(payOrder.getPayType());
                    int num = update(needOrder);
                    if (num == 0) {
                        return false;
                    }
                    // 发送推送消息
                    if (payOrder.getStatus().equals(PayStatus.SUCCESS.getValue())) {
                        sendMessagePushInfo(needOrder);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 发送推送消息(异步多线程)
     * @param needOrder
     */
    private void sendMessagePushInfo(final NeedsOrder needOrder) {
        if (needOrder == null || needOrder.getCompanyId() == null) {
            return;
        }
        EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                // 需求信息
                Needs needs = needsDao.getById(needOrder.getNeedsId());
                if (needs == null) {
                    return;
                }
                // 服务商信息
                Member facilitator = memberDao.getFacilitatorAdminByCompanyId(needOrder.getCompanyId());
                if (facilitator == null) {
                    return;
                }
                // 客户信息
                Member customer = memberDao.getCustomerAdminByCompanyId(needs.getCompanyId());
                if (customer == null) {
                    return;
                }
                Company facilitatorCompany=companyService.getById(needOrder.getCompanyId());
                Company customerCompany=companyService.getById(needs.getCompanyId());
                Map<String, String> params = new HashMap<>();
                params.put("needs_name", needs.getName());
                params.put("member_name", facilitatorCompany.getName());
                // 给服务商发消息
                messageInfoService.sendSmsAndPushMessage(facilitator.getUid(), facilitator.getMobile(), MessageId.NEEDS_ORDER_PAYMENT_FACILITATOR, params);
                params.put("member_name", customerCompany.getName());
                // 给客户发消息    
                messageInfoService.sendSmsAndPushMessage(customer.getUid(), customer.getMobile(), MessageId.NEEDS_RESPONED_CUSTOMER, params);
            }
        });
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

	@Override
	public NeedsOrder getByNeedsIdAndCompanyId(Long needsId,Long companyId) {
		return needsOrderDao.getByNeedsIdAndCompanyId(needsId,companyId);
	}

	@Override
	@Transactional
	public int updateRefundStatus(NeedsOrder needsOrder) {
		return needsOrderDao.updateRefundStatus(needsOrder);
	}

	@Override
	@Transactional
	public boolean refundNeedsOrderDeposit(NeedsOrder needsOrder,boolean isProjectSettle) {
		if(needsOrder==null){
			logger.warn("needsOrder can not be null");
			return false;
		}
		boolean statusRs=false;
		if(isProjectSettle){//需求订单-项目结算
			statusRs=(needsOrder.getStatus()==NeedsOrderStatus.COOPERATED.getValue());
		}
		else{//需求订单，取消，不符合，不合作
			statusRs=(needsOrder.getStatus()==NeedsOrderStatus.CANCEL.getValue()||
					needsOrder.getStatus()==NeedsOrderStatus.NOT_COOPERATE.getValue()||
					needsOrder.getStatus()==NeedsOrderStatus.NOT_QUALIFIED.getValue());
		}
		if(!statusRs){
			logger.warn("needsOrder({}) status({}) is not support refund depoist",needsOrder.getOrderId(),needsOrder.getStatus());
			return false;
		}
		Long fuid=userService.getFacilitatorAdminUid(needsOrder.getCompanyId());
		UserWallet fuserWallet = userWalletService.getByUid(fuid);
		if(fuserWallet==null){
			logger.warn("user({}) wallet is not exist",fuid);
			return false;
		}
	    if(needsOrder.getRefundStatus()==NeedsOrderRefundStatus.NORMAL.getValue()&&
	    		needsOrder.getPayStatus()==PayStatus.SUCCESS.getValue()){
	    	    Double amount=needsOrder.getAmount();
				//解冻冻结金额里的保证金
				fuserWallet.setFreezingAmount(MoneyUtils.sub(fuserWallet.getFreezingAmount(), amount));
				//将解冻的保证金直接返回到余额
				fuserWallet.setAmount(MoneyUtils.add(fuserWallet.getAmount(),amount));
				//增加解冻明细
			    UserWalletDetail userWalletDetail2=new UserWalletDetail();
			  
			    userWalletDetail2.setUid(fuid);
			    //账单类型 -解冻
			    userWalletDetail2.setType(WalletType.UNFROZEN.getValue());
    			// 金额
			    userWalletDetail2.setAmount(amount);
        		//交易类型 - 收入
			    userWalletDetail2.setDealType(WalletDealType.INCOME.getValue());
        		// 订单编号
			    userWalletDetail2.setOrderId(needsOrder.getOrderId());
			    userWalletDetail2.setPayOrderId(needsOrder.getOrderId());
        		// 资金来源
			    userWalletDetail2.setFundSource(new Long(WalleFundType.PLATFORM.getValue()));
        		// 资金去向 -
			    userWalletDetail2.setFundTarget(new Long(WalleFundType.WALLE_BALANCE.getValue()));
        		// 交易后 钱包可用余额
			    userWalletDetail2.setAvailableAmount(fuserWallet.getAmount());
        		// 交易后 钱包冻结金额
			    userWalletDetail2.setAvailableFreezingAmount(fuserWallet.getFreezingAmount());
        		// 微信/支付宝订单号
			    userWalletDetail2.setTransactionId(needsOrder.getOrderId());
        		// 支付者账号(目前只有支付宝有)
			    userWalletDetail2.setDealAccount("");
        		// 备注
			    userWalletDetail2.setRemark("参与需求保证金退回");
			    userWalletService.addUserWalletDetail(userWalletDetail2);
			    needsOrder.setRefundStatus(NeedsOrderRefundStatus.SUCCESS.getValue());
			    updateRefundStatus(needsOrder);
			    userWalletService.update(fuserWallet);
			    return true;
	    }
		return false;
		
	}
}
