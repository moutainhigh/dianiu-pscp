package com.edianniu.pscp.mis.service.orderpay.impl;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.commons.ResultCode;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.*;
import com.edianniu.pscp.mis.bean.pay.*;
import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.*;
import com.edianniu.pscp.mis.service.orderpay.OrderPayService;
import com.edianniu.pscp.mis.util.MoneyUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务支付代理类
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 下午4:36:23
 */
public class DefaultOrderPayService implements OrderPayService {
	 private static final Logger logger = LoggerFactory
	            .getLogger(DefaultOrderPayService.class);
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private UserService userService;
    @Autowired
    private ElectricianWorkOrderService electricianWorkOrderService;
    @Autowired
    private EngineeringProjectService engineeringProjectService;
    @Autowired
    private NeedsOrderService needsOrderService;
    @Autowired
    private MessageInfoService messageInfoService;
    @Autowired
    private NeedsService needsService;
    @Autowired
    private CompanyService companyService;
    @Autowired
	private RenterChargeOrderService renterChargeOrderService;
    
    private Map<Integer, OrderPayService> serviceMap = new ConcurrentHashMap<Integer, OrderPayService>();

    @Override
    public StartPayResult start(StartPayReq startPayReq) {
        OrderPayService orderPayService = loadService(startPayReq.getOrderType());
        return orderPayService.start(startPayReq);
    }

    @Override
    public PreparePayResult prepare(PreparePayReq preparePayReq) {
        OrderPayService orderPayService = loadService(preparePayReq.getOrderType());
        return orderPayService.prepare(preparePayReq);
    }

    @Override
    public boolean updateOrderStatus(PayOrder order) {
        OrderPayService orderPayService = loadService(order.getOrderType());
        return orderPayService.updateOrderStatus(order);
    }

    @Override
    public double getOrderAmount(Integer orderType, String orderId) {
        OrderPayService orderPayService = loadService(orderType);
        return orderPayService.getOrderAmount(orderType, orderId);
    }

    /**
     * 获取具体业务服务类
     *
     * @param orderType
     * @return
     */
    private OrderPayService loadService(Integer orderType) {
        OrderPayService orderPayService = serviceMap.get(orderType);
        if (orderPayService == null) {
            throw new RuntimeException("orderPayService is not found!");
        }
        return orderPayService;
    }

    public void setServiceMap(Map<Integer, OrderPayService> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    public boolean updateSyncOrderStatus(PayOrder payOrder) {
        OrderPayService orderPayService = loadService(payOrder.getOrderType());
        return orderPayService.updateSyncOrderStatus(payOrder);
    }

    @Override
    @Transactional
    public boolean updateUserWallet(PayOrder payOrder) {//更新支付方钱包信息
        boolean result = false;
        UserWallet wall = this.userWalletService.getByUid(payOrder.getUid());
        UserWallet fromModify = new UserWallet();
        fromModify.setId(wall.getId());
        fromModify.setModifiedUser("系统");
        fromModify.setModifiedTime(wall.getModifiedTime());
        /* 计算充值后金额 */
        // 将原冻结金额转换单位为分
        if (payOrder.getOrderType() == OrderType.RECHARGE.getValue()) {
            // 转换单位为元.
            double newAmount = MoneyUtils.add(wall.getAmount(), payOrder.getAmount());
            fromModify.setAmount(newAmount);
            // 原冻结金额不动
            fromModify.setFreezingAmount(wall.getFreezingAmount());
        } else {//支付

            if (payOrder.getPayType() == PayType.WALLET.getValue()) {//余额支付
                //余额金额-order.getAmount()*100;
                double newAmount = MoneyUtils.sub(wall.getAmount(), payOrder.getAmount());
                fromModify.setAmount(newAmount);
            } else {//其他支付
                //原账户金额不变
                fromModify.setAmount(wall.getAmount());
            }
            if (payOrder.getOrderType() == OrderType.PROJECT_SETTLEMENT.getValue()) {//项目结算支付
            	fromModify.setFreezingAmount(wall.getFreezingAmount());//客户冻结余额不变
            }
            else if(payOrder.getOrderType() == OrderType.RENTER_CHARGE_SETTLEMENT.getValue()){//电费缴费
            	fromModify.setFreezingAmount(wall.getFreezingAmount());//租客冻结余额不变
            }
            else {
                double newFreezingAmount = MoneyUtils.add(wall.getFreezingAmount(), payOrder.getAmount());
                fromModify.setFreezingAmount(newFreezingAmount);
            }
        }
        // 更新钱包金额
        int rowNum = userWalletService.update(fromModify);
        if (rowNum == 1) {
            result = true;
        }
        return result;
    }

    @Override
    @Transactional
    public boolean addUserWalletDetail(PayOrder payOrder) {//更新支付方交易明细，更新收钱方交易明细，以及收钱方钱包信息
        Long uid = payOrder.getUid();
        UserWallet userWallet = this.userWalletService.getByUid(uid);
        OrderType orderType = OrderType.parse(payOrder.getOrderType());
        String[] orderIds = StringUtils.split(payOrder.getAssociatedOrderIds(), ",");
        if (orderType.equals(OrderType.SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT)) {//社会电工工单结算
            //计算社会电工工单对应的社会工单编号
            //社会电工工单交易明细记录在社会工单下面
            String id = electricianWorkOrderService.getSocialWorkOrderOrderIdByOrderId(orderIds[0]);
            orderIds = new String[1];
            orderIds[0] = id;
        }
        for (String orderId : orderIds) {
            UserWalletDetail userWalletDetail = new UserWalletDetail();
            userWalletDetail.setUid(uid);
            if (orderType.equals(OrderType.RECHARGE)) {
                // 账单类型 - 充值
                userWalletDetail.setType(WalletType.RECHARGE.getValue());
                // 金额
                userWalletDetail.setAmount(payOrder.getAmount());
            } else {
                // 账单类型
                if (orderType.equals(OrderType.PROJECT_SETTLEMENT)) {
                    userWalletDetail.setType(WalletType.WITHHOLD.getValue());
                }
                else if(orderType.equals(OrderType.RENTER_CHARGE_SETTLEMENT)){
                	userWalletDetail.setType(WalletType.ELECTRIC_CHARGE.getValue());
                }
                else {
                    userWalletDetail.setType(WalletType.FROZEN.getValue());
                }

                if (orderIds.length > 1) {//表示多个社会工单批量支付
                    userWalletDetail.setAmount(getOrderAmount(payOrder.getOrderType(), orderId));
                } else {//单个社会工单支付或者单个社会工单结算
                    userWalletDetail.setAmount(payOrder.getAmount());
                }

            }
            if (orderType.equals(OrderType.PROJECT_SETTLEMENT)||
            	orderType.equals(OrderType.RENTER_CHARGE_SETTLEMENT)||
            	new Integer(userWalletDetail.getType()).equals(WalletType.FROZEN.getValue())) {
                //交易类型 - 支出
                userWalletDetail.setDealType(WalletDealType.COSTS.getValue());
            } else {
                //交易类型 - 收入
                userWalletDetail.setDealType(WalletDealType.INCOME.getValue());
            }

            // 订单编号
            userWalletDetail.setOrderId(orderId);
            userWalletDetail.setPayOrderId(payOrder.getOrderId());
            // 资金来源
            userWalletDetail.setFundSource(new Long(payOrder.getPayType()));
            // 资金去向 -
            if (orderType.equals(OrderType.PROJECT_SETTLEMENT)) {
                userWalletDetail.setFundTarget(new Long(WalleFundType.PLATFORM.getValue()));
            } else {//可用余额
                userWalletDetail.setFundTarget(new Long(WalleFundType.WALLE_BALANCE.getValue()));
            }
            // 交易后 钱包可用余额
            userWalletDetail.setAvailableAmount(userWallet.getAmount());
            // 交易后 钱包冻结金额
            userWalletDetail.setAvailableFreezingAmount(userWallet.getFreezingAmount());
            // 微信/支付宝订单号
            userWalletDetail.setTransactionId(payOrder.getTppTransactionId());
            // 支付者账号(目前只有支付宝有)
            userWalletDetail.setDealAccount(payOrder.getTppBuyerId());
            // 备注
            userWalletDetail.setRemark(orderType.getOrderTitle());
            userWalletDetail.setCreateUser("系统");
            userWalletService.addUserWalletDetail(userWalletDetail);
            if (orderType.equals(OrderType.PROJECT_SETTLEMENT)) {
                //服务商增加收入明细
                EngineeringProject project = engineeringProjectService.getByProjectNo(orderId);
                if (project.getNeedsId() != null && project.getStatus() == EngineeringProjectStatus.SETTLED.getValue()) {
                    NeedsOrder needsOrder = needsOrderService.getByNeedsIdAndCompanyId(project.getNeedsId(), project.getCompanyId());
                    if (needsOrder != null &&
                            needsOrder.getStatus() == NeedsOrderStatus.COOPERATED.getValue()) {
                        Long fuid = userService.getFacilitatorAdminUid(project.getCompanyId());
                        UserWallet fuserWallet = this.userWalletService.getByUid(fuid);
                        //客户项目结算支付的金额直接到服务商的余额
                        fuserWallet.setAmount(MoneyUtils.add(fuserWallet.getAmount(), userWalletDetail.getAmount()));

                        //增加收入明细
                        UserWalletDetail userWalletDetail1 = new UserWalletDetail();
                        userWalletDetail1.setUid(fuid);
                        //账单类型 -收入
                        userWalletDetail1.setType(WalletType.INCOME.getValue());
                        // 金额
                        userWalletDetail1.setAmount(userWalletDetail.getAmount());
                        //交易类型 - 收入
                        userWalletDetail1.setDealType(WalletDealType.INCOME.getValue());
                        // 订单编号
                        userWalletDetail1.setOrderId(orderId);
                        userWalletDetail1.setPayOrderId(payOrder.getOrderId());
                        // 资金来源
                        userWalletDetail1.setFundSource(new Long(WalleFundType.PLATFORM.getValue()));
                        // 资金去向 -
                        userWalletDetail1.setFundTarget(new Long(WalleFundType.WALLE_BALANCE.getValue()));
                        // 交易后 钱包可用余额
                        userWalletDetail1.setAvailableAmount(fuserWallet.getAmount());
                        // 交易后 钱包冻结金额
                        userWalletDetail1.setAvailableFreezingAmount(fuserWallet.getFreezingAmount());
                        // 微信/支付宝订单号
                        userWalletDetail1.setTransactionId(payOrder.getTppTransactionId());
                        // 支付者账号(目前只有支付宝有)
                        userWalletDetail1.setDealAccount(payOrder.getTppBuyerId());
                        // 备注
                        userWalletDetail1.setRemark(orderType.getOrderTitle());
                        userWalletService.addUserWalletDetail(userWalletDetail1);
                        userWalletService.update(fuserWallet);
                        if (needsOrderService.refundNeedsOrderDeposit(needsOrder, true)) {
                            // 项目结算消息推送
                            sendProjectSettlementMessage(uid, fuid, userWalletDetail.getAmount(), project.getId());
                        }
                    }
                }
            }
            else if(orderType.equals(OrderType.RENTER_CHARGE_SETTLEMENT)){
            	//企业房东增加电费收入明细
                	RenterChargeOrder renterChargeOrder = renterChargeOrderService.getByOrderId(orderId);
                    if (renterChargeOrder != null &&
                    		renterChargeOrder.getPayStatus() == PayStatus.SUCCESS.getValue()) {
                        Long companyUid = renterChargeOrderService.getCompanyUidByOrderId(orderId);
                        UserWallet fuserWallet = this.userWalletService.getByUid(companyUid);
                        //租客电费支付的金额直接到房东的余额
                        fuserWallet.setAmount(MoneyUtils.add(fuserWallet.getAmount(), userWalletDetail.getAmount()));
                        //增加收入明细
                        UserWalletDetail userWalletDetail1 = new UserWalletDetail();
                        userWalletDetail1.setUid(companyUid);
                        //账单类型 -收入
                        userWalletDetail1.setType(WalletType.ELECTRIC_CHARGE.getValue());
                        // 金额
                        userWalletDetail1.setAmount(userWalletDetail.getAmount());
                        //交易类型 - 收入
                        userWalletDetail1.setDealType(WalletDealType.INCOME.getValue());
                        // 订单编号
                        userWalletDetail1.setOrderId(orderId);
                        userWalletDetail1.setPayOrderId(payOrder.getOrderId());
                        // 资金来源
                        userWalletDetail1.setFundSource(new Long(WalleFundType.PLATFORM.getValue()));
                        // 资金去向 -
                        userWalletDetail1.setFundTarget(new Long(WalleFundType.WALLE_BALANCE.getValue()));
                        // 交易后 钱包可用余额
                        userWalletDetail1.setAvailableAmount(fuserWallet.getAmount());
                        // 交易后 钱包冻结金额
                        userWalletDetail1.setAvailableFreezingAmount(fuserWallet.getFreezingAmount());
                        // 微信/支付宝订单号
                        userWalletDetail1.setTransactionId(payOrder.getTppTransactionId());
                        // 支付者账号(目前只有支付宝有)
                        userWalletDetail1.setDealAccount(payOrder.getTppBuyerId());
                        // 备注
                        userWalletDetail1.setRemark(orderType.getOrderTitle());
                        userWalletService.addUserWalletDetail(userWalletDetail1);
                        userWalletService.update(fuserWallet);
                    }
                
            }
        }
        return true;
    }

    /**
     * 项目结算消息推送
     *
     * @param customerId
     * @param facilitatorId
     * @param amount
     * @param projectId
     */
    private void sendProjectSettlementMessage(Long customerId, Long facilitatorId, Double amount, Long projectId) {
        UserInfo facilitator = userService.getUserInfo(facilitatorId);
        if (facilitator == null) {
            logger.error(String.format("根据服务商用户ID[%s]未找到服务商信息", facilitatorId));
            return;
        }

        UserInfo customer = userService.getUserInfo(customerId);
        if (customer == null) {
            logger.error(String.format("根据客户用户ID[%s]未找到客户信息", customerId));
            return;
        }
        Company customerCompany = companyService.getById(customer.getCompanyId());
        if (customerCompany == null) {
            logger.error(String.format("根据客户公司ID[%s]未找到客户公司信息", customer.getCompanyId()));
            return;
        }

        Needs needs = needsService.getByProjectId(projectId);
        if (needs == null) {
            logger.error(String.format("根据项目ID[%s]未找到需求信息", projectId));
            return;
        }

        try {
            // 发送消息参数
            Map<String, String> params = new HashMap<>();
            params.put("needs_name", needs.getName());
            params.put("amount", MoneyUtils.format(amount));
            // 项目结算-客户消息
            messageInfoService.sendSmsAndPushMessage(customer.getUid(), customer.getMobile(), MessageId.NEEDS_SETTLEMENT_SUCCESS_CUSTOMER, params);
            // 项目结算-服务商消息
            params.put("customer_name", customerCompany.getName());
            messageInfoService.sendSmsAndPushMessage(facilitator.getUid(), facilitator.getMobile(), MessageId.NEEDS_SETTLEMENT_SUCCESS_FACILITATOR, params);
        } catch (Exception e) {
            logger.error("项目结算消息发送异常", e);
        }
    }

    @Override
    public CheckPayOrderResult checkPayOrder(PayOrder payOrder, PayPush payPush) throws BusinessException {
        CheckPayOrderResult result = new CheckPayOrderResult();
        if (payOrder == null) {
            throw new BusinessException("订单不存在!");
        }
        int totalAmount = Integer.parseInt(payPush.getTotalFee());
        if (totalAmount != payOrder.getAmount() * 100) {
            result.set(ResultCode.ERROR_201, "支付金额不正确，走退款流程");
            return result;//支付金额有问题
        }
        /*
         * 判断订单状态
		 */
        if (payOrder.getStatus() == PayStatus.SUCCESS.getValue() && payOrder.getPayType() == payPush.getPayType()) {
            //重复通知
            result.set(ResultCode.ERROR_203, "重复通知");
            return result;
        }
        /*
         * 支付状态是成功的情况下：那么返回重复支付
		 * 支付状态是确认中并且订单的支付类型和push的支付类型不一样：那么返回重复支付
		 * 判断订单状态
		 */
        if (payOrder.getStatus() == PayStatus.SUCCESS.getValue() ||
                (payOrder.getStatus() == PayStatus.INPROCESSING.getValue() && payOrder.getPayType() != payPush.getPayType())) {
            result.set(ResultCode.ERROR_201, "重复支付，走退款流程");
            return result;
        }
        if (payOrder.getStatus() == PayStatus.UNPAY.getValue() ||
                payOrder.getStatus() == PayStatus.INPROCESSING.getValue() ||
                payOrder.getStatus() == PayStatus.CANCLE.getValue() ||
                payOrder.getStatus() == PayStatus.FAIL.getValue()) {
            OrderPayService orderPayService = loadService(payOrder.getOrderType());
            result = orderPayService.checkPayOrder(payOrder, payPush);
            if (MoneyUtils.equals(result.getRefundAmount(), payOrder.getAmount())) {
                result.set(ResultCode.ERROR_201, "重复支付，走退款流程");
                return result;
            }
            if (MoneyUtils.lessThan(result.getRefundAmount(), payOrder.getAmount()) && MoneyUtils.greaterThan(result.getRefundAmount(), 0.00D)) {
                result.set(ResultCode.ERROR_202, "重复支付，走退款流程");
                return result;
            }
            return result;
        }
        return result;
    }
}
