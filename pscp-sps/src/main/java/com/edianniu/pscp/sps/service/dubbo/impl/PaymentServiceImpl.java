package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.cs.bean.needsorder.ListNeedsOrderReqData;
import com.edianniu.pscp.cs.bean.needsorder.ListNeedsOrderResult;
import com.edianniu.pscp.cs.bean.needsorder.UpdateReqData;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderVO;
import com.edianniu.pscp.cs.service.dubbo.CustomerNeedsOrderInfoService;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.EngineeringProjectStatus;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.pay.*;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.ProjectStatus;
import com.edianniu.pscp.sps.bean.payment.PaymentOrderType;
import com.edianniu.pscp.sps.bean.payment.PaymentReqData;
import com.edianniu.pscp.sps.bean.payment.PaymentResult;
import com.edianniu.pscp.sps.bean.payment.PaymentStatus;
import com.edianniu.pscp.sps.bean.payment.balance.FacilitatorBalanceReqData;
import com.edianniu.pscp.sps.bean.payment.balance.FacilitatorBalanceResult;
import com.edianniu.pscp.sps.bean.payment.paypage.AliPayCallbackResult;
import com.edianniu.pscp.sps.bean.payment.paypage.WxPayPollingResult;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.socialworkorder.SocialWorkOrderStatus;
import com.edianniu.pscp.sps.bean.workorder.electrician.ElectricianWorkOrderStatus;
import com.edianniu.pscp.sps.commons.Constants;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.*;
import com.edianniu.pscp.sps.service.*;
import com.edianniu.pscp.sps.service.dubbo.PaymentInfoService;
import com.edianniu.pscp.sps.util.MoneyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: PaymentServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-31 19:43
 */
@Service
@Repository("paymentInfoService")
public class PaymentServiceImpl implements PaymentInfoService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    @Qualifier("memberWalletService")
    private MemberWalletService memberWalletService;
    @Autowired
    @Qualifier("spsCompanyService")
    private SpsCompanyService spsCompanyService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("payInfoService")
    private PayInfoService payInfoService;
    @Autowired
    @Qualifier("memberPayOrderService")
    private MemberPayOrderService memberPayOrderService;
    @Autowired
    @Qualifier("socialWorkOrderService")
    private SocialWorkOrderService socialWorkOrderService;
    @Autowired
    @Qualifier("electricianWorkOrderService")
    private ElectricianWorkOrderService electricianWorkOrderService;
    @Autowired
    @Qualifier("customerNeedsOrderInfoService")
    private CustomerNeedsOrderInfoService customerNeedsOrderInfoService;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;
    @Autowired
    @Qualifier("needsInfoService")
    private NeedsInfoService needsInfoService;
    @Autowired
    @Qualifier("engineeringProjectService")
    private EngineeringProjectService engineeringProjectService;


    @Override
    public FacilitatorBalanceResult getFacilitatorBalance(FacilitatorBalanceReqData facilitatorBalanceReqData) {
        FacilitatorBalanceResult result = new FacilitatorBalanceResult();
        try {
            MemberWallet memberWallet = memberWalletService.queryEntityByUid(facilitatorBalanceReqData.getUid());
            if (memberWallet == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("未匹配到用户信息");
                return result;
            }
            result.setAmount(MoneyUtils.format(memberWallet.getAmount()));
            result.setFreezingAmount(MoneyUtils.format(memberWallet.getFreezingAmount()));
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder confirm:{}", e);
        }
        return result;
    }

    /**
     * 支付宝支付回调
     *
     * @param params
     * @param uid
     * @return
     */
    @Override
    public AliPayCallbackResult aliPayCallback(Map<String, String> params, Long uid) {
        AliPayCallbackResult result = new AliPayCallbackResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            Company company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
            if (company == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("未匹配到公司信息");
                return result;
            }

            StringBuilder paramsLink = new StringBuilder(128);
            int i = 0;
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (i == params.size() - 1) {
                    paramsLink.append(key).append("=").append(value);
                } else {
                    paramsLink.append(key).append("=").append(value).append("&");
                }
                i++;
            }

            ConfirmPayResult confirmPayResult = getConfirmPayResult(company, params, paramsLink, 0);
            if (confirmPayResult == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("系统异常");
                return result;
            }

            if (!confirmPayResult.isSuccess()) {
                result.setResultCode(confirmPayResult.getResultCode());
                result.setResultMessage(confirmPayResult.getResultMessage());
                result.setPageUrl(structureFailurePageUrl());// 失败页面
            }

            if (PaymentStatus.SUCCESS.getName().equals(confirmPayResult.getSyncPayStatus())) {
                // 构建支付成功页面
                result.setPageUrl(structureSuccessPageUrl());// 成功页面
                if (StringUtils.isNoneBlank(confirmPayResult.getExtendParams())) {
                    result.setPageUrl(structurePageUrl(null, confirmPayResult.getExtendParams()));
                }
            } else if (!PaymentStatus.UNPAID.getName().equals(confirmPayResult.getSyncPayStatus()) &&
                    !PaymentStatus.CONFIRMING.getName().equals(confirmPayResult.getSyncPayStatus())) {
                result.setPageUrl(structureFailurePageUrl());// 失败页面
            } else if (PaymentStatus.CONFIRMING.getName().equals(confirmPayResult.getSyncPayStatus())) {
                //result.setPageUrl(structureSuccessPageUrl());
                result.setPageUrl(structurePageUrl(null, confirmPayResult.getExtendParams()));
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder confirm:{}", e);
        }
        return result;
    }

    private ConfirmPayResult getConfirmPayResult(Company company, Map<String, String> params, StringBuilder paramsLink, Integer index) {
        if (index > 3) {
            return null;
        }

        ConfirmPayReq confirmPayReq = new ConfirmPayReq();
        confirmPayReq.setUid(company.getMemberId());
        confirmPayReq.setOrderId(params.get("out_trade_no"));
        confirmPayReq.setPayMethod(PayMethod.PC.getValue());
        confirmPayReq.setOrderType(OrderType.RECHARGE.getValue());
        confirmPayReq.setPayType(PayType.ALIPAY.getValue());
        confirmPayReq.setResult(paramsLink.toString());
        confirmPayReq.setResultStatus("success");
        ConfirmPayResult confirmPayResult = payInfoService.confirmPay(confirmPayReq);
        if (!confirmPayResult.isSuccess()) {
            return confirmPayResult;
        }

        if (PaymentStatus.CONFIRMING.getName().equals(confirmPayResult.getSyncPayStatus())) {
            index++;
            try {
                Thread.sleep(1000 * index);
            } catch (InterruptedException e) {
                logger.error("thread sleep exception", e);
            }
            getConfirmPayResult(company, params, paramsLink, index);
        }

        return confirmPayResult;
    }

    /**
     * 微信支付轮询
     *
     * @param orderId
     * @param uid
     * @return
     */
    @Override
    public WxPayPollingResult wxPayPolling(String orderId, Long uid) {
        WxPayPollingResult result = new WxPayPollingResult();
        try {
            if (StringUtils.isBlank(orderId)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("订单ID不能为空");
                return result;
            }

            MemberPayOrder payOrder = memberPayOrderService.queryEntityByOrderId(orderId);
            if (payOrder == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("订单不存在");
                return result;
            }

            result.setPayState(payOrder.getStatus());

            // 根据支付订单状态构建返回页面跳转地址
            if (payOrder.getStatus().equals(PaymentStatus.SUCCESS.getValue())) {
                // 构建支付成功页面
                result.setPageUrl(structureSuccessPageUrl());
                if (StringUtils.isNoneBlank(payOrder.getExtendParams())) {
                    result.setPageUrl(structurePageUrl(null, payOrder.getExtendParams()));
                }
            } else if (payOrder.getStatus() > PaymentStatus.SUCCESS.getValue()) {
                //  支付失败
                result.setPageUrl(structureFailurePageUrl());
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder confirm:{}", e);
        }
        return result;
    }

    /**
     * 支付
     *
     * @param paymentReqData
     * @return
     */
    @Override
    public PaymentResult payment(PaymentReqData paymentReqData) {
        Date now = new Date();
        PaymentResult result = new PaymentResult();
        try {
            if (paymentReqData.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录用户不能为空");
                return result;
            }
            if (paymentReqData.getPayType() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("支付类型不能为空");
                return result;
            }
            if (paymentReqData.getOrderType() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("订单类型不能为空");
                return result;
            }
            if (StringUtils.isBlank(paymentReqData.getOrderIds())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("支付订单信息不能为空");
                return result;
            }
            if (StringUtils.isBlank(paymentReqData.getIp())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("IP信息不能为空");
                return result;
            }

            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(paymentReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            Company company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
            if (company == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("未匹配到公司信息");
                return result;
            }

            String[] orderIdArray = paymentReqData.getOrderIds().split(",");
            if (orderIdArray.length <= 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("订单信息错误");
                return result;
            }

            List<SocialWorkOrder> socialWorkOrderList = new ArrayList<>();
            List<ElectricianWorkOrder> electricianWorkOrderList = new ArrayList<>();
            List<NeedsOrderVO> needsOrderList = new ArrayList<>();
            List<ProjectInfo> projectList = new ArrayList<>();

            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("orderIds", orderIdArray);
            queryMap.put("companyId", company.getId());
            if (paymentReqData.getOrderType().equals(OrderType.SOCIAL_WORK_ORDER_PAY.getValue())) {
                // 社会工单支付
                socialWorkOrderList = socialWorkOrderService.selectListByCondition(queryMap);
                // 过期时间
                for (SocialWorkOrder socialWorkOrder : socialWorkOrderList) {
                    Integer status = socialWorkOrder.getStatus();
                    if (SocialWorkOrderStatus.CANCEL.getValue().equals(status)) {
                        result.setResultMessage("工单已取消，无需支付");
                        result.setResultCode(ResultCode.ERROR_401);
                        return result;
                    }
                    if (!SocialWorkOrderStatus.UN_PUBLISH.getValue().equals(status)
                            && !SocialWorkOrderStatus.CANCEL.getValue().equals(status)) {
                        result.setResultMessage("支付完成，请勿重复支付");
                        result.setResultCode(ResultCode.ERROR_401);
                        return result;
                    }

                    Date expiryTime = socialWorkOrder.getExpiryTime();
                    if (expiryTime.before(now)) {
                        result.setResultMessage("支付工单已过期");
                        result.setResultCode(ResultCode.ERROR_401);
                        return result;
                    }
                }
            } else if (paymentReqData.getOrderType().equals(OrderType.SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT.getValue())) {
                // 社会电工工单结算支付
                queryMap.put("settlementPayStatus", Constants.TAG_NO);
                queryMap.put("status", ElectricianWorkOrderStatus.FEE_CONFIRM.getValue());
                electricianWorkOrderList = electricianWorkOrderService.selectListByCondition(queryMap);
                if (CollectionUtils.isEmpty(electricianWorkOrderList)) {
                    result.setResultMessage("支付工单信息不存在");
                    result.setResultCode(ResultCode.ERROR_401);
                    return result;
                }
            } else if (paymentReqData.getOrderType().equals(OrderType.NEEDS_ORDER_PAY.getValue())) {
                // 参与需求保证金支付
                queryMap.put("payStatus", Constants.TAG_NO);
                queryMap.put("status", NeedsOrderStatus.RESPONED.getValue());
                ListNeedsOrderReqData reqData = new ListNeedsOrderReqData();
                reqData.setQueryMap(queryMap);
                ListNeedsOrderResult listNeedsOrderResult = customerNeedsOrderInfoService.listAllNeedsOrder(reqData);
                if (!listNeedsOrderResult.isSuccess()) {
                    result.setResultMessage("支付订单信息不存在");
                    result.setResultCode(ResultCode.ERROR_401);
                    return result;
                }
                needsOrderList = listNeedsOrderResult.getNeedsOrderList();
                if (CollectionUtils.isEmpty(needsOrderList)) {
                    result.setResultMessage("支付订单信息不存在");
                    result.setResultCode(ResultCode.ERROR_401);
                    return result;
                }
            } else if (paymentReqData.getOrderType().equals(OrderType.PROJECT_SETTLEMENT.getValue())) {
				// 项目结算     TODO
            	Integer[] payStatuss = new Integer[]{
            			PayStatus.CANCLE.getValue(),
            			PayStatus.FAIL.getValue(),
            			PayStatus.UNPAY.getValue()
            	};
            	queryMap.put("payStatuss", payStatuss);
            	queryMap.put("status", EngineeringProjectStatus.COST_TO_BE_CONFIRMED.getValue());
            	projectList = engineeringProjectService.queryList(queryMap);
            	if (CollectionUtils.isEmpty(projectList)) {
                    result.setResultMessage("支付项目信息不存在");
                    result.setResultCode(ResultCode.ERROR_401);
                    return result;
                }
			}

            // 检查验证结果
            if (result.getResultCode() != ResultCode.SUCCESS) {
                return result;
            }

            // 获取支付金额信息
            StartPayReq startPayReq = new StartPayReq();
            startPayReq.setOrderIds(paymentReqData.getOrderIds());
            startPayReq.setOrderType(paymentReqData.getOrderType());
            startPayReq.setUid(paymentReqData.getUid());
            StartPayResult startPayResult = payInfoService.startPay(startPayReq);
            if (startPayResult.getResultCode() != ResultCode.SUCCESS) {
                result.setResultCode(startPayResult.getResultCode());
                result.setResultMessage(startPayResult.getResultMessage());
                return result;
            }
            Double totalMoney = Double.valueOf(startPayResult.getPayAmount());

            // 获取预支付信息
            PreparePayResult preparePayResult = getPreparePayResult(paymentReqData, company, totalMoney);
            if (!preparePayResult.isSuccess()) {
                result.setResultCode(preparePayResult.getResultCode());
                result.setResultMessage(preparePayResult.getResultMessage());
                return result;
            }

            // 返回支付类型
            result.setPayType(paymentReqData.getPayType());
            // 支付成功后跳转地址
            result.setOriginUrl(paymentReqData.getReturnUrl());

            // 余额支付
            if (paymentReqData.getPayType().equals(PayType.WALLET.getValue())) {
                WalletPayInfo walletPayInfo = preparePayResult.getWalletpay();

                // 确认支付
                ConfirmPayReq confirmPayReq = new ConfirmPayReq();
                confirmPayReq.setPayMethod("PC");
                confirmPayReq.setUid(company.getMemberId());
                confirmPayReq.setResultStatus("success");
                confirmPayReq.setOrderId(walletPayInfo.getOrderId());
                confirmPayReq.setPayType(paymentReqData.getPayType());
                confirmPayReq.setOrderType(paymentReqData.getOrderType());
                ConfirmPayResult confirmPayResult = payInfoService.confirmPay(confirmPayReq);
                if (!confirmPayResult.isSuccess()) {
                    result.setResultCode(confirmPayResult.getResultCode());
                    result.setResultMessage(confirmPayResult.getResultMessage());
                    return result;
                }

                try {
                    // 更新工单支付信息
                    if (paymentReqData.getOrderType().equals(OrderType.SOCIAL_WORK_ORDER_PAY.getValue())) {
                        // 社会工单支付
                        batchUpdateSocialWorkOrderPayment(socialWorkOrderList, totalMoney, now);
                    } else if (paymentReqData.getOrderType().equals(OrderType.SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT.getValue())) {
                        // 社会电工工单结算支付
                        batchUpdateElectricianWorkOrderPayment(electricianWorkOrderList);
                    } else if (paymentReqData.getOrderType().equals(OrderType.NEEDS_ORDER_PAY.getValue())) {
                        // 参与需求保证金支付
                        batchUpdateNeedsOrderPayment(needsOrderList, company.getId(), totalMoney, paymentReqData.getPayType(), PayStatus.SUCCESS.getValue(), now);
                    } else if (paymentReqData.getOrderType().equals(OrderType.PROJECT_SETTLEMENT.getValue())) {
                    	// 项目结算
                    	batchUpdateProjectPayment(projectList, totalMoney, now);
					}
                } catch (Exception e) {
                    // FIXME 更新订单异常处理
                    e.printStackTrace();
                }
            } else {
                // 其他支付方式
                return buildPaymentInfo(result, preparePayResult, paymentReqData, totalMoney);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Social WorkOrder confirm:{}", e);
        }
        return result;
    }

    /**
     * 订单状态修改-项目结算 TODO
     */
    private void batchUpdateProjectPayment(List<ProjectInfo> projectList, Double totalMoney, Date now){
    	for (ProjectInfo projectInfo : projectList) {
    		projectInfo.setStatus(ProjectStatus.SETTLED.getValue());
			projectInfo.setPayStatus(PayStatus.SUCCESS.getValue());
			projectInfo.setPayType(PayType.WALLET.getValue());
			projectInfo.setPayTime(now);
			projectInfo.setPaySynctime(now);
			projectInfo.setPayAsynctime(now);
			projectInfo.setPayMemo("");
		}
    	engineeringProjectService.batchUpdatePayment(projectList);
    }
    
    /**
     * 订单状态修改-需求响应订单保证支付
     *
     * @param needsOrderList
     * @param companyId
     * @param totalMoney
     * @param payType
     * @param payStatus
     * @param now
     */
    private void batchUpdateNeedsOrderPayment(List<NeedsOrderVO> needsOrderList, Long companyId, Double totalMoney, Integer payType, int payStatus, Date now) {
        for (NeedsOrderVO needsOrderVO : needsOrderList) {
            needsOrderVO.setCompanyId(companyId);
            needsOrderVO.setPayAmount(totalMoney);
            needsOrderVO.setPayType(payType);
            needsOrderVO.setPayStatus(payStatus);
            needsOrderVO.setPayTime(now);
            needsOrderVO.setPaySyncTime(now);
            needsOrderVO.setPayAsyncTime(now);
        }
        UpdateReqData reqData = new UpdateReqData();
        reqData.setNeedsOrderList(needsOrderList);
        customerNeedsOrderInfoService.updateNeedsOrderVO(reqData);

        // 消息推送
        //sendMessagePushInfo(needsOrderList);
    }

    /**
     * 发送推送消息(异步多线程)
     *
     * @param needsOrderList
     *//*
    private void sendMessagePushInfo(final List<NeedsOrderVO> needsOrderList) {
        if (CollectionUtils.isEmpty(needsOrderList)) {
            return;
        }
        for (final NeedsOrderVO needsOrderVO : needsOrderList) {
            EXECUTOR_SERVICE.submit(new Runnable() {
                @Override
                public void run() {
                    Map<String, Object> map = customerNeedsOrderInfoService.getMapSendMessagePushInfo(needsOrderVO.getId());
                    if (MapUtils.isEmpty(map)) {
                        return;
                    }

                    Map<String, String> params = new HashMap<>();
                    params.put("needs_name", String.valueOf(map.get("needsName")));
                    messageInfoService.sendSmsAndPushMessage(Long.valueOf(String.valueOf(map.get("memberId"))), String.valueOf(map.get("mobile")), MessageId.NEEDS_ORDER_PAYMENT_FACILITATOR, params);
                }
            });
        }

    }*/

    /**
     * 创建一个固定线程池
     *//*
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            // 工作队列
            new SynchronousQueue<Runnable>(),
            // 线程池饱和处理策略
            new ThreadPoolExecutor.CallerRunsPolicy());
*/
    /**
     * 订单状态修改-社会工单支付
     *
     * @param socialWorkOrderList
     * @param totalMoney
     * @param now
     */
    private void batchUpdateSocialWorkOrderPayment(List<SocialWorkOrder> socialWorkOrderList, Double totalMoney, Date now) {
        List<SocialWorkOrder> updateList = new LinkedList<>();
        for (SocialWorkOrder socialWorkOrder : socialWorkOrderList) {
            SocialWorkOrder updateEntity = new SocialWorkOrder();
            updateEntity.setId(socialWorkOrder.getId());
            updateEntity.setPayTime(now);
            updateEntity.setPaySynctime(now);
            updateEntity.setPayAsynctime(now);
            updateEntity.setPayType(PayType.WALLET.getValue());
            updateEntity.setPayAmount(totalMoney);
            updateEntity.setPayStatus(PayStatus.SUCCESS.getValue());
            updateEntity.setStatus(SocialWorkOrderStatus.RECRUITING.getValue());
            updateList.add(socialWorkOrder);
        }
        socialWorkOrderService.batchUpdatePayment(updateList);
    }

    /**
     * 订单状态修改-社会电工工单结算支付
     *
     * @param electricianWorkOrderList
     */
    private void batchUpdateElectricianWorkOrderPayment(List<ElectricianWorkOrder> electricianWorkOrderList) {
        List<ElectricianWorkOrder> updateList = new ArrayList<>();
        for (ElectricianWorkOrder electricianWorkOrder : electricianWorkOrderList) {
            ElectricianWorkOrder updateEntity = new ElectricianWorkOrder();
            updateEntity.setId(electricianWorkOrder.getId());
            updateEntity.setSettlementPayStatus(Constants.TAG_YES);
            updateList.add(updateEntity);
        }
        electricianWorkOrderService.updateBatch(updateList, null, null, null);
    }


    /**
     * 获取预支付信息
     *
     * @param paymentReqData
     * @param company
     * @param totalMoney
     * @return
     */
    private PreparePayResult getPreparePayResult(PaymentReqData paymentReqData, Company company, Double totalMoney) {
        // 预支付请求参数
        PreparePayReq req = new PreparePayReq();
        req.setPayMethod("PC");
        req.setUid(company.getMemberId());
        req.setIp(paymentReqData.getIp());
        req.setPayType(paymentReqData.getPayType());
        req.setOrderType(paymentReqData.getOrderType());
        req.setAmount(MoneyUtils.format(totalMoney));
        req.setOrderIds(paymentReqData.getOrderIds());
        req.setExtendParams(paymentReqData.getReturnUrl());

        // 网站支付返回的url(余额和微信支付暂时没有用)
        String callback = "";
        if (paymentReqData.getPayType().equals(PayType.ALIPAY.getValue())) {
            callback = String.format("%s%s", platformDomain, "cp/payment/alipay/callback");
        } else if (paymentReqData.getPayType().equals(PayType.UNIONPAY.getValue())) {
            callback = String.format("%s%s", platformDomain, "cp/payment/unionpay/callback");
        }
        req.setReturnUrl(callback);
        return payInfoService.preparePay(req);
    }
    
    /**
     * 构建其他支付方式(除余额外)
     *
     * @param result
     * @param preparePayResult
     * @param paymentReqData
     * @param totalMoney
     * @return
     */
    private PaymentResult buildPaymentInfo(PaymentResult result, PreparePayResult preparePayResult,
                                           PaymentReqData paymentReqData, Double totalMoney) {

        result.setOrderId(preparePayResult.getOrderId());
        result.setPayType(paymentReqData.getPayType());
        result.setOrderType(PaymentOrderType.getNameByValue(paymentReqData.getOrderType()));

        if (paymentReqData.getPayType().equals(PayType.ALIPAY.getValue())) {
            AlipayPrepayInfo alipayPrepayInfo = preparePayResult.getAlipay();

            result.setRedirectUrl(structurePageUrl(null, "cp/payment/alipay"));
            // 用Base64加密支付宝请求数据，用到的地方在解密
            String thirdPartyPaymentInfo = new String(Base64.encodeBase64(alipayPrepayInfo.getParams().getBytes()));
            result.setThirdPartyPaymentInfo(thirdPartyPaymentInfo);
        } else if (paymentReqData.getPayType().equals(PayType.WEIXIN.getValue())) {
            WxpayPrepayInfo wxpayPrepayInfo = preparePayResult.getWeixinpay();

            result.setRedirectUrl(structurePageUrl(null, "cp/payment/wxpay"));
            result.setThirdPartyPaymentInfo(wxpayPrepayInfo.getCodeUrl());
            result.setAmount(MoneyUtils.format(totalMoney));
        } else if (paymentReqData.getPayType().equals(PayType.UNIONPAY.getValue())) {
            UnionPayInfo unionPayInfo = preparePayResult.getUnionpay();

            result.setRedirectUrl(structurePageUrl(null, "cp/payment/unionpay"));
            result.setThirdPartyPaymentInfo(unionPayInfo.getTn());
            result.setAmount(MoneyUtils.format(totalMoney));
        }

        return result;
    }


    /**
     * 支付失败页面跳转地址
     *
     * @return
     */
    private String structureFailurePageUrl() {
        return structurePageUrl(null, "cp/payment/failure");
    }

    /**
     * 支付成功页面跳转地址
     *
     * @return
     */
    private String structureSuccessPageUrl() {
        return structurePageUrl(null, "cp/payment/success");
    }

    private String structurePageUrl(String domain, String pageUrl) {
        if (StringUtils.isBlank(domain)) {
            domain = platformDomain;
        }
        return String.format("%s%s", domain, pageUrl);
    }

    private String platformDomain;

    @Value(value = "${portal.platform.domain}")
    public void setPlatformDomain(String platformDomain) {
        this.platformDomain = platformDomain;
    }
}
