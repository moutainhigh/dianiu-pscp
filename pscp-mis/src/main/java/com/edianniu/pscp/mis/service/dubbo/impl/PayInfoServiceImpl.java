/**
 *
 */
package com.edianniu.pscp.mis.service.dubbo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stc.skymobi.cache.redis.JedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.result.SendMessageResult;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.DefaultResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.TradeStatus;
import com.edianniu.pscp.mis.bean.pay.*;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.MemberRefund;
import com.edianniu.pscp.mis.domain.PayConfirm;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.UnionpayPushLog;
import com.edianniu.pscp.mis.domain.UserBankCard;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.CachedService;
import com.edianniu.pscp.mis.service.ElectricianWorkOrderService;
import com.edianniu.pscp.mis.service.MemberRefundService;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.service.PayService;
import com.edianniu.pscp.mis.service.SocialWorkOrderService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.service.UserWalletService;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.orderpay.OrderPayService;
import com.edianniu.pscp.mis.service.pay.AliPayService;
import com.edianniu.pscp.mis.service.pay.UnionpayService;
import com.edianniu.pscp.mis.service.pay.WxPayService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.MoneyUtils;
import com.edianniu.pscp.mis.util.alipay.AlipayCore;
import com.edianniu.pscp.mis.util.alipay.AlipayResult;
import com.edianniu.pscp.mis.util.unionpay.AcpService;
import com.edianniu.pscp.mis.util.unionpay.SDKConfig;
import com.edianniu.pscp.mis.util.wxpay.H5Info;
import com.edianniu.pscp.mis.util.wxpay.SceneInfo;
import com.edianniu.pscp.mis.util.wxpay.WxPayUtil;

/**
 * 支付相关业务
 * 
 * @author cyl
 */
@Service
@Repository("payInfoService")
public class PayInfoServiceImpl implements PayInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(PayInfoServiceImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private MessageInfoService messageInfoService;
    @Autowired
    private PayService payService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private MemberRefundService memberRefundService;
    @Autowired
    private SocialWorkOrderService socialWorkOrderService;
    @Autowired
    private ElectricianWorkOrderService electricianWorkOrderService;

    @Autowired
    private UnionpayService unionpayService;
    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    @Qualifier("cachedService")
    private CachedService cachedService;
   

    

    private String unionpayNotifyUrl;
    private String unionpaydfNotifyUrl;


    private String merId;
    @Value(value = "${return.car.distance:50}")
    private int returnCarDistance = 50;//米

    @Value(value = "${unionpay.merId:}")
    public void setMerId(String merId) {
        this.merId = merId;
    }

    @Value(value = "${pay.unionpay.notify.url:}")
    public void setUnionpayNotifyUrl(String unionpayNotifyUrl) {
        this.unionpayNotifyUrl = unionpayNotifyUrl;
    }

    @Value(value = "${pay.unionpaydf.notify.url:}")
    public void setUnionpaydfNotifyUrl(String unionpaydfNotifyUrl) {
        this.unionpaydfNotifyUrl = unionpaydfNotifyUrl;
    }

    @Override
    public AlipayNotifyResult alipayNotify(AlipayNotifyReq req) {
        AlipayNotifyResult result = new AlipayNotifyResult();

        try {
            logger.info(String.format("alipayNotify request : %s", req));

            // 验证 是否传入了支付宝推送参数
            if (req.getAliPush() == null || req.getAliPush().isEmpty()) {
                throw new BusinessException("未找到推送信息!");
            }
            logger.info(String.format(
                    "alipay push notify [aliPush - map] : %s", req.getAliPush()));
            logger.info(String.format(
                    "alipay push notify [aliPush - json] : %s",
                    JSON.toJSONString(req.getAliPush())));

            // 转换为bean
            AliPayPush pushInfo = new AliPayPush();
            BeanUtils.populate(pushInfo, req.getAliPush());
            pushInfo.setAppid(req.getAliPush().get("app_id"));
            // 记录接口推送日志
            this.payService.aliPushLog(pushInfo);
            // 签名验证, 是否为支付宝回调.
            Map<String, String> params = new HashMap<>();
            for (String key : req.getAliPush().keySet()) {
                params.put(key, req.getAliPush().get(key));
            }
            logger.info(String.format(
                    "alipay push notify [aliPush - params] : %s",
                    JSON.toJSONString(params)));
            if (!aliPayService.rsaCheck(params)) {
                logger.error("支付宝推送签名验证不合法!");
                // 签名不合法
                throw new BusinessException("签名验证失败!签名不合法!");
            }
            pushInfo.setOrderId(pushInfo.getOutTradeNo());
            PayNotifyResult payNotifyResult = handlePayOrder(pushInfo);
            // 支付宝需要在页面上直接打印success
            if (payNotifyResult.isSuccess()) {
                result.setResultMessage("success");
            } else {
                logger.warn("alipayNotify handlePayOrder:{}", JSON.toJSONString(payNotifyResult));
                // 记录处理的异常信息
                String pushJson = JSON.toJSONString(req.getAliPush());
                String errMsg = String.format("%s [%s]",
                        payNotifyResult.getResultCode(), payNotifyResult.getResultMessage());
                // 记录推送异常信息
                this.payService.aliushError(pushJson, errMsg);
                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage(errMsg);
            }

        } catch (Exception e) {
            logger.error("alipayNotify:{}", e);
            // 记录处理的异常信息
            String pushJson = JSON.toJSONString(req.getAliPush());
            String errMsg = String.format("%s [%s]",
                    ResultCode.ERROR_500_MESSAGE, e.getMessage());
            // 记录推送异常信息
            this.payService.aliushError(pushJson, errMsg);

            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage(errMsg);
        }

        return result;
    }
    /**
	 * 获取交易状态
	 *
	 * @param pushInfo 推送的信息
	 * @return
	 */
	private TradeStatus getTradeStatus(PayPush pushInfo) throws BusinessException {
		if (pushInfo instanceof WxPayPush) {
			if ("SUCCESS".equals(pushInfo.getResultCode())) {
				return TradeStatus.TRADE_FINISHED;
			} else if ("FAIL".equals(pushInfo.getResultCode())) {
				return TradeStatus.TRADE_CLOSED;
			} else {
				throw new BusinessException("result_code 错误[微信]");
			}
		} else if (pushInfo instanceof AliPayPush) {
			if ("WAIT_BUYER_PAY".equals(pushInfo.getTradeStatus())) {
				return TradeStatus.WAIT_BUYER_PAY;
			} else if ("TRADE_CLOSED".equals(pushInfo.getTradeStatus())) {
				return TradeStatus.TRADE_CLOSED;
			} else if ("TRADE_SUCCESS".equals(pushInfo.getTradeStatus())) {
				return TradeStatus.TRADE_SUCCESS;
			} else if ("TRADE_FINISHED".equals(pushInfo.getTradeStatus())) {
				return TradeStatus.TRADE_FINISHED;
			} else {
				throw new BusinessException("trade_status 错误[支付宝]");
			}
		}else if(pushInfo instanceof UnionpayPush){
			if("00".equals(pushInfo.getRespCode())){
				return TradeStatus.TRADE_SUCCESS;
			}else if("03".equals(pushInfo.getRespCode())||"05".equals(pushInfo.getRespCode())){
				return TradeStatus.TRADE_PAYING;
			}else{
				return TradeStatus.TRADE_FASLE;
			}
			
		} else {
			throw new BusinessException("推送信息类型错误!");
		}
	}
    private PayNotifyResult handlePayOrder(PayPush pushInfo)
            throws BusinessException, Exception {
        PayNotifyResult payNotifyResult = new PayNotifyResult();
        TradeStatus tradeStatus = this.getTradeStatus(pushInfo);
        PayOrder order = payOrderService.getPayOrderByOrderId(pushInfo.getOrderId());
        CheckPayOrderResult result=orderPayService.checkPayOrder(order, pushInfo);
        logger.debug("CheckPayOrderResult:{},tradeStatus:{}",JSON.toJSONString(result),tradeStatus);
        switch (result.getResultCode()) {
            case 200://正常情况
                // 通知订单, 执行修改事物
                payNotifyResult = this.payService.asyncNotify(order, pushInfo);
                break;
            case 201://全部退款
                if (tradeStatus.equals(TradeStatus.TRADE_SUCCESS)||tradeStatus.equals(TradeStatus.TRADE_FINISHED)) {
                    payService.refund(order, pushInfo,result.getRefundAmount());
                }
                break;
            case 202://部分退款
            	payNotifyResult = this.payService.asyncNotify(order, pushInfo);
            	if (tradeStatus.equals(TradeStatus.TRADE_SUCCESS)||tradeStatus.equals(TradeStatus.TRADE_FINISHED)) {
            		payService.refund(order, pushInfo,result.getRefundAmount());
            	}
                break;
            case 203://重复通知
                payNotifyResult.set(ResultCode.PAY_ERROR,result.getResultMessage());
                break;
            default:
                break;
        }
        return payNotifyResult;
    }

    @Override
    public WxpayNotifyResult wxpayNotify(WxpayNotifyReq req) {
        WxpayNotifyResult result = new WxpayNotifyResult();
        try {
            logger.info(String.format("wxpayPushNotify request : %s", req));

            // 验证, 是否传入了完整的pushxml
            if (req.getPushXml() == null || "".equals(req.getPushXml())) {
                throw new BusinessException("未找到推送的xml信息! pushXml 是必须的!");
            }
            logger.info(String.format("WxPay push notify [wxpush - bean] : %s",
                    req.getPushXml()));

			/*
             * 将xml转换为bean
			 */
            Map<String, String> pushMap = WxPayUtil.decodeXml(req.getPushXml());
            WxPayPush pushInfo = new WxPayPush();
            BeanUtils.populate(pushInfo, pushMap);
            String orderId = pushInfo.getOutTradeNo();
            if (StringUtils.isNoneBlank(orderId)) {
                int idx = orderId.indexOf("_");
                if (idx > 0) {
                    orderId = orderId.substring(0, idx);
                }
            }
            pushInfo.setOrderId(orderId);
            // 记录接口推送日志
            this.payService.wxPushLog(pushInfo);
            logger.info(String.format("WxPay push notify [wxpush - json] : %s",
                    JSON.toJSONString(pushInfo)));

            // 签名验证, 是否为微信回调.
            
            if (!wxPayService.checkIsSignValidFromResponseString(req.getPushXml(), pushInfo.getTradeType(),pushInfo.getAppid())) {
                logger.error("微信推送签名验证不合法!");
                // 签名不合法
                throw new BusinessException("签名验证失败!签名不合法!");
            }
            PayNotifyResult payNotifyResult = handlePayOrder(pushInfo);
            // 支付宝需要在页面上直接打印success
            if (payNotifyResult.isSuccess()) {
                result.setResultMessage("success");
            } else {
                logger.warn("wxpayNotify handlePayOrder:{}", JSON.toJSONString(payNotifyResult));
                // 记录处理的异常信息
                String reqXml = req.getPushXml();
                String errMsg = String.format("%s [%s]",
                        payNotifyResult.getResultCode(), payNotifyResult.getResultMessage());
                // 记录推送异常信息
                this.payService.wxPushError(reqXml, errMsg);

                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage(errMsg);
            }


        } catch (Exception e) {
            logger.error("wxpayNotify:{}", e);
            // 记录处理的异常信息
            String reqXml = req.getPushXml();
            String errMsg = String.format("%s [%s]",
                    ResultCode.ERROR_500_MESSAGE, e.getMessage());
            // 记录推送异常信息
            this.payService.wxPushError(reqXml, errMsg);

            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage(errMsg);
        }

        return result;
    }
    /**
     * 开始支付
     */
    @Override
	public StartPayResult startPay(StartPayReq startPayReq) {
		logger.debug("StartPayReq:{}", startPayReq);
		StartPayResult result = new StartPayResult();
		Integer orderType = startPayReq.getOrderType();
		Long uid=startPayReq.getUid();
		try {
			if (startPayReq.getUid() == null) {
				result.setResultCode(ResultCode.ERROR_201);
				result.setResultMessage("uid 不能为空");
				return result;
			}
			UserInfo userInfo = userService.getSimpleUserInfo(startPayReq
					.getUid());
			if (userInfo == null) {
				result.setResultCode(ResultCode.ERROR_202);
				result.setResultMessage("用户信息不存在");
				return result;
			}
			if(userInfo.isNotFacilitatorAdmin()){
			   uid=userService.getFacilitatorAdminUid(userInfo.getCompanyId());
			}
			if (orderType == null) {
				result.setResultCode(ResultCode.ERROR_204);
				result.setResultMessage("orderType 不能为空");
				return result;
			}
			if (!OrderType.include(orderType)) {
				result.setResultCode(ResultCode.ERROR_204);
				result.setResultMessage(OrderType.tip());
				return result;
			}
			startPayReq.setCompanyId(userInfo.getCompanyId());
			startPayReq.setUid(uid);//如果是子帐号，设置主帐号的uid
			result=orderPayService.start(startPayReq);
			if(!result.isSuccess()){
				return result;
			}
			UserWallet userWallet=userWalletService.getByUid(uid);
			if(userWallet==null){
				result.setResultCode(ResultCode.ERROR_210);
				result.setResultMessage("用户数据异常");
				return result;
			}
			List<com.edianniu.pscp.mis.domain.PayType> payTypes=payService.getPayTypes();
			List<PayTypeInfo> payTypeInfos=new ArrayList<PayTypeInfo>();
			for(com.edianniu.pscp.mis.domain.PayType payType:payTypes){
				PayTypeInfo payTypeInfo=new PayTypeInfo();
				BeanUtils.copyProperties(payTypeInfo, payType);
				if(payType.getType().equals("walletpay")){
					if (orderType == OrderType.RECHARGE.getValue()) {
						payTypeInfo.setDisabled(1);
					}
					else{
						if(MoneyUtils.greaterThanOrEqual(userWallet.getAmount(), Double.parseDouble(result.getPayAmount()))){
							payTypeInfo.setDisabled(0);
						}
						else{
							payTypeInfo.setDisabled(1);
						}
					}
				}
				payTypeInfos.add(payTypeInfo);
				
			}
			result.setPayTypeInfos(payTypeInfos);
			result.setWalletAmount(MoneyUtils.format(userWallet.getAmount()));

		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
			result.setResultMessage("系统异常");
			logger.error("startPay:{}", e);
		}
		return result;
	}
    /**
     * 予支付
     */
    @Override
    public PreparePayResult preparePay(PreparePayReq req) {
        logger.debug("preparePayReq:{}", req);
        PreparePayResult result = new PreparePayResult();
        String orderId=req.getOrderId();
        String orderIds = req.getOrderIds();
        Integer orderType = req.getOrderType();
        String amount = req.getAmount();//
        Integer payType = req.getPayType();//支付类型
        String payMethod = req.getPayMethod();//支付方式(APP,PC,WAP)
        String extendParams = req.getExtendParams();//业务扩展参数
        Integer needInvoice=req.getNeedInvoice();//是否需要发票
        Long uid=req.getUid();
        try {
            
            if (req.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid 不能为空");
                return result;
            }
            UserInfo userInfo = userService.getSimpleUserInfo(req.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            if(userInfo.isNotFacilitatorAdmin()){
 			   uid=userService.getFacilitatorAdminUid(userInfo.getCompanyId());
 			}
            if (orderType == null) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("orderType 不能为空");
                return result;
            }
            if (!OrderType.include(orderType)) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("orderType 只能是1[充值]或者2[支付订单]或者3[结算订单]");
                return result;
            }
            if (payType == null) {
                result.setResultCode(ResultCode.ERROR_206);
                result.setResultMessage("payType 不能为空");
                return result;
            }
            if (StringUtils.isBlank(amount)) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("amount 不能为空");
                return result;
            }
            if (!BizUtils.isPositiveNumber(amount)) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("amount必须为数字");
                return result;
            }
            if(!MoneyUtils.greaterThan(Double.parseDouble(amount), 0.00D)){//支付金额大于0.00
            	 result.setResultCode(ResultCode.ERROR_207);
                 result.setResultMessage("amount必须大于0.00");
                 return result;
            }
            if (StringUtils.isBlank(payMethod)) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("payMethod 不能为空");
                return result;
            }
            if (!PayMethod.include(payMethod)) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("payMethod 只支持APP|PC|WAP");
                return result;
            }
            if (StringUtils.isNoneBlank(extendParams)) {
                if (!BizUtils.checkLength(extendParams, 256)) {
                    result.setResultCode(ResultCode.ERROR_207);
                    result.setResultMessage("extendParams最大支持256个字符");
                    return result;
                }
            }
            if(!(needInvoice==null||needInvoice==0||needInvoice==1)){
            	result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("needInvoice只支持0|1");
                return result;
            } 
            req.setCompanyId(userInfo.getCompanyId());
            req.setUid(uid);//如果是子帐号，设置为父帐号的uid
            result=orderPayService.prepare(req);
            if(!result.isSuccess()){
            	return result;
            }
            PayOrder order = payOrderService.buildPayOrder(uid, orderId,orderIds, 
            		Double.parseDouble(amount), orderType, payType, payMethod,req.getAppType(),needInvoice,extendParams);
            if (payType == PayType.WALLET.getValue()) {// 余额支付
                UserWallet userWalle = userWalletService.getByUid(order.getUid());
                if (MoneyUtils.greaterThan(order.getAmount(), userWalle.getAmount())) {
                    result.setResultCode(ResultCode.ERROR_404);
                    result.setResultMessage("余额不足");
                    result.setOrderId(order.getOrderId());
                    return result;
                }
                WalletPayInfo walletpay = new WalletPayInfo();
                walletpay.setAmount(MoneyUtils.format(amount));
                walletpay.setOrderId(order.getOrderId());
                result.setWalletpay(walletpay);
                result.setOrderId(order.getOrderId());
                result.setExtendParams(order.getExtendParams());
            } else if (payType == PayType.ALIPAY.getValue()) {// 支付宝支付
                result = alipayPrepay(order, req);
            } else if (payType == PayType.WEIXIN.getValue()) {// 微信支付
                result = weixinPrepay(order, req);
            } else if (payType == PayType.UNIONPAY.getValue()) {

                result = unionpayPrepay(order, req);//银联单位为分，只能传入整数
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("preparepay:{}", e);
        }
        return result;
    }

    /**
     * 支付宝预支付
     *
     * @param order
     * @param preparePayReq
     * @return
     */
    private PreparePayResult alipayPrepay(PayOrder order, PreparePayReq preparePayReq) {
        PreparePayResult result = new PreparePayResult();
        String orderId = order.getOrderId();
        String subject = order.getTitle();
        String body = order.getBody();
        String totalAmount = MoneyUtils.format(order.getAmount());
        AliPayPrepayReq aliPayPrepayReq = new AliPayPrepayReq();
        aliPayPrepayReq.setBody(body);
        aliPayPrepayReq.setOrderId(orderId);
        aliPayPrepayReq.setPayMethod(order.getPayMethod());
        aliPayPrepayReq.setSubject(subject);
        aliPayPrepayReq.setTotalAmount(totalAmount);
        aliPayPrepayReq.setReturnUrl(preparePayReq.getReturnUrl());
        AliPayPrepayResult aliPayPrepayResult = this.aliPayService.prepay(aliPayPrepayReq);
        if (!aliPayPrepayResult.isSuccess()) {
            result.set(aliPayPrepayResult.getResultCode(), aliPayPrepayResult.getResultMessage());
            return result;
        }
        result.setAlipay(aliPayPrepayResult.getAlipayPrepayInfo());
        result.setOrderId(orderId);
        result.setExtendParams(order.getExtendParams());
        return result;
    }

    /**
     * 微信预支付
     *
     * @param order
     * @param preparePayReq
     * @return
     */
    private PreparePayResult weixinPrepay(PayOrder order, PreparePayReq preparePayReq) {
        PreparePayResult result = new PreparePayResult();

        String orderId = order.getOrderId();
        String body = order.getTitle();
        String detail = order.getBody();
        String attach = "" + order.getOrderType();
        String outTradeNo = orderId + "_" + WxPayUtil.getTimeStamp();
        String tradeType = order.getPayMethod();
        if (tradeType.equals(PayMethod.PC.getValue())) {
            tradeType = "NATIVE";
        }
        else if(tradeType.endsWith(PayMethod.WAP.getValue())){
        	tradeType = "MWEB";
        }
        WxpayPrepayReqData reqData = new WxpayPrepayReqData();
        reqData.setAttach(attach);
        reqData.setBody(body);
        reqData.setDetail(detail);
        reqData.setNonce_str(WxPayUtil.genNonceStr());
        reqData.setOut_trade_no(outTradeNo);
        reqData.setSpbill_create_ip(preparePayReq.getIp());
        reqData.setTotal_fee(MoneyUtils.yuanToStrFen(String.valueOf(order.getAmount())));
        reqData.setTrade_type(tradeType);
        if(tradeType.equals("MWEB")){
        	SceneInfo sceneInfo=new SceneInfo();
        	H5Info h5Info=new H5Info();
        	h5Info.setType("Wap");
        	h5Info.setWap_name("电牛运维");
        	h5Info.setWap_url("http://renter.edianniu.cn");
        	sceneInfo.setH5_info(h5Info);
        	reqData.setSceneInfo(JSON.toJSONString(sceneInfo));
        	reqData.setReturnUrl(preparePayReq.getReturnUrl());
        }
        reqData.setAppType(preparePayReq.getAppType());
        WxpayPrepayRespData respData = wxPayService.prepay(reqData);
        if (!respData.isSuccess()) {
            result.setResultCode(ResultCode.ERROR_405);
            result.setResultMessage(respData.getErrorMessage());
            return result;
        }
        result.setWeixinpay(respData.getWeixinpay());
        result.setOrderId(orderId);
        result.setExtendParams(order.getExtendParams());
        return result;
    }

    /**
     * 银联支付预付
     *
     * @param order
     * @param preparePayReq
     * @return
     */
    private PreparePayResult unionpayPrepay(PayOrder order, PreparePayReq preparePayReq) {
        PreparePayResult result = new PreparePayResult();
        UnionpayPrepayReqData data = new UnionpayPrepayReqData();
        data.setAccessType("0");
        data.setBizType("000201");
        data.setChannelType("08");
        data.setCurrencyCode("156");
        data.setEncoding("UTF-8");
        data.setOrderId(order.getOrderId());
        data.setMerId(merId);//商户编码，后期配置文件中获取
        if (order.getOrderType() == OrderType.RECHARGE.getValue()) {
            data.setReqReserved("{cardNumberLock=1&type=3}");
        } else {
            data.setReqReserved("{cardNumberLock=1&type=1}");
        }
        data.setRequestUrl(SDKConfig.getConfig().getAppRequestUrl());
        data.setReserved("{cardNumberLock=1}");
        data.setSignMethod("01");
        data.setVersion("5.0.0");
        data.setTxnAmt(MoneyUtils.format(order.getAmount() * 100));
        data.setTxnSubType("01");
        data.setTxnType("01");
        data.setBackUrl(unionpayNotifyUrl);
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String txnTime = format.format(now);
        data.setTxnTime(txnTime);
        UnionpayPrepayRespData resp = unionpayService.prepay(data);
        if (!resp.isSuccess()) {
            result.setResultCode(resp.getResultCode());
            result.setResultMessage(resp.getResultMessage());
        }
        UnionPayInfo tnInfo = new UnionPayInfo();
        tnInfo.setMode("01");
        tnInfo.setTn(resp.getUnionpayPrepayInfo().getTn());
        result.setUnionpay(tnInfo);
        result.setOrderId(order.getOrderId());
        result.setExtendParams(order.getExtendParams());
        return result;

    }

    @Override
    public ConfirmPayResult confirmPay(ConfirmPayReq req) {
        ConfirmPayResult result = new ConfirmPayResult();
        String orderId = req.getOrderId();
        Integer orderType = req.getOrderType();//充值和社会工单予支付
        Integer payType = req.getPayType();//
        
        Long uid=req.getUid();
        PayResult payResult = new PayResult(req.getResultStatus(), req.getResult());
        UserInfo userInfo = userService.getSimpleUserInfo(req.getUid());
        if (userInfo == null) {
            result.setResultCode(ResultCode.ERROR_203);
            result.setResultMessage("用户信息不存在");
            return result;
        }
        if (StringUtils.isBlank(orderId)) {
            result.setResultCode(ResultCode.ERROR_204);
            result.setResultMessage("orderId 不能为空");
            return result;
        }
        if (orderType == null) {
            result.setResultCode(ResultCode.ERROR_205);
            result.setResultMessage("orderType 不能为空");
            return result;
        }
        if (!OrderType.include(orderType)) {
            result.setResultCode(ResultCode.ERROR_205);
            result.setResultMessage("orderType 只能是1[充值]或者2[订单]");
            return result;
        }
        if (payType == null) {
            result.setResultCode(ResultCode.ERROR_207);
            result.setResultMessage("payType 不能为空");
            return result;
        }
        if (orderType == OrderType.RECHARGE.getValue()) {
            if (!PayType.include(payType, PayType.WALLET.getValue())) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("payType 必须为1,2,3");
                return result;
            }
        } else {
            if (!PayType.include(payType)) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("payType 必须为0,1,2,3");
                return result;
            }
        }
        if (StringUtils.isBlank(payResult.getResultStatus())) {
            result.setResultCode(ResultCode.ERROR_208);
            result.setResultMessage("resultStatus 不能为空");
            return result;
        }
        if (payType == PayType.ALIPAY.getValue()) {
            if (!payResult.getResultStatus().equals("6001")) {// 用户取消时,payReuslt未返回
                if (StringUtils.isBlank(payResult.getResult())) {
                    result.setResultCode(ResultCode.ERROR_209);
                    result.setResultMessage("result 不能为空");
                    return result;
                }
            }
        }
        PayOrder payOrder = payOrderService.getPayOrderByOrderId(orderId);
        if (payOrder == null) {
            result.setResultCode(ResultCode.ERROR_210);
            result.setResultMessage("支付订单不存在");
            return result;
        }
        if(userInfo.isNotFacilitatorAdmin()){
			   uid=userService.getFacilitatorAdminUid(userInfo.getCompanyId());
		}
        if (payOrder.getUid().compareTo(uid) != 0) {
            result.setResultCode(ResultCode.ERROR_210);
            result.setResultMessage("支付订单不正确");
            return result;
        }

        if (payType == PayType.WALLET.getValue()) {
        	 UserWallet userWalle = userWalletService.getByUid(payOrder.getUid());
             if (MoneyUtils.greaterThan(payOrder.getAmount(), userWalle.getAmount())) {
                 result.setResultCode(ResultCode.ERROR_404);
                 result.setResultMessage("余额不足");
                 return result;
             }
            return this.handleWalletResponse(userInfo, payOrder, payResult);
        } else if (payType == PayType.ALIPAY.getValue()) {
            return this.handleAlipayResponse(userInfo, payOrder,
                    payResult);
        } else if (payType == PayType.WEIXIN.getValue()) {
            return this.handleWxpayResponse(userInfo, payOrder, payResult);
        } else if (payType == PayType.UNIONPAY.getValue()) {
            return this.handleUnionpayResponse(userInfo, payOrder,
                    payResult);
        }
        result.setExtendParams(payOrder.getExtendParams());
        return result;
    }

    private ConfirmPayResult handleWalletResponse(UserInfo userInfo,
                                                  PayOrder payOrder,
                                                  PayResult payResult) {
        ConfirmPayResult checkPayResult = new ConfirmPayResult();
        checkPayResult.setSyncPayStatus("CONFIRM");
        if (!payResult.getResultStatus().equals("success")) {
            checkPayResult.setResultCode(ResultCode.ERROR_210);
            checkPayResult.setResultMessage("resultStatus 参数错误");
            return checkPayResult;
        }
        // 更新状态
        PayStatus payStatus = PayStatus.SUCCESS;
        PayConfirm payConfirm = new PayConfirm();
        payConfirm.setOrderType(OrderType.parse(payOrder.getOrderType()));
        payConfirm.setOrderId(payOrder.getOrderId());
        payConfirm.setPayMemo("");
        payConfirm.setPayStatus(payStatus);
        payConfirm.setPayType(PayType.WALLET);
        payConfirm.setUid(payOrder.getUid());
        PayConfirmResult payConfirmResult = payService.paymentConfirm(payConfirm);
        if (!payConfirmResult.isSuccess()) {
            checkPayResult.setResultCode(payConfirmResult.getResultCode());
            checkPayResult
                    .setResultMessage(payConfirmResult.getResultMessage());
            return checkPayResult;
        }
        if (payConfirmResult.isAsync()) {
            if (payConfirmResult.isRepeat()) {
                if (checkPayResult.getSyncPayStatus().equals("CONFIRM")) {
                    checkPayResult.setSyncPayStatus("REPEAT");
                }
            } else {
                checkPayResult.setSyncPayStatus("SUCCESS");
            }
        }
        if (payStatus.equals(PayStatus.SUCCESS) && checkPayResult.getSyncPayStatus().equals("SUCCESS")) {
            rechargeSmsNotify(userInfo, payOrder, payStatus);
        }
        checkPayResult.setExtendParams(payOrder.getExtendParams());
        return checkPayResult;
    }

    /**
     * 支付结果校验 支付状态 9000 订单支付成功 8000 正在处理中 4000 订单支付失败 6001 用户中途取消 6002 网络连接出错
     *
     * @param userInfo
     * @param payOrder
     * @param payResult
     * @return
     */
    private ConfirmPayResult handleAlipayResponse(UserInfo userInfo,
                                                  PayOrder payOrder,
                                                  PayResult payResult) {
        ConfirmPayResult checkPayResult = new ConfirmPayResult();
        // 1、原始数据是否跟商户请求支付的原始数据一致（必须验证这个）
        // 2、验证这个签名是否能通过。
        // 在sign字段中success=true才是可信的
        logger.debug("payResult:" + payResult);
        String result = payResult.getResult();
        String resultStatus = payResult.getResultStatus();
        PayStatus payStatus = PayStatus.SUCCESS;
        if ((resultStatus.equals("9000") || resultStatus.equals("8000"))) {
        	AlipayResult alipayResult=JSON.parseObject(result, AlipayResult.class);
            logger.debug("alipayResult:{}",alipayResult);
            if (alipayResult==null||
            	StringUtils.isBlank(alipayResult.getSign()) || 
            	StringUtils.isBlank(alipayResult.getSign_type())||
            	alipayResult.getAlipay_trade_app_pay_response()==null) {
                checkPayResult.setResultCode(ResultCode.ERROR_402);
                checkPayResult.setResultMessage("参数校验失败");
                return checkPayResult;
            }
            //校验其他问题
            if (!aliPayService.rsaCheck(result,alipayResult.getSign())) {
                checkPayResult.setResultCode(ResultCode.ERROR_403);
                checkPayResult.setResultMessage("签名校验失败");
                return checkPayResult;
            }
            if (alipayResult.getAlipay_trade_app_pay_response().getCode().equals("10000")) {
                checkPayResult.setSyncPayStatus("CONFIRM");
                payStatus = PayStatus.INPROCESSING;
            } else {
                checkPayResult.setSyncPayStatus("FAIL");
                payStatus = PayStatus.FAIL;
            }
        } else if (resultStatus.equals("success")) {//PC网站支付成功
            Map<String, String> params = AlipayCore.parseParams(result);
            logger.info("PC网站支付成功返回参数:{}", JSON.toJSONString(params));
            if (!aliPayService.rsaCheck(params)) {
                checkPayResult.setResultCode(ResultCode.ERROR_403);
                checkPayResult.setResultMessage("PC网站支付成功-签名校验失败");
                return checkPayResult;
            }
            checkPayResult.setSyncPayStatus("CONFIRM");
            payStatus = PayStatus.INPROCESSING;
        } else if (resultStatus.equals("6001")) {
            checkPayResult.setSyncPayStatus("CANCLE");
            payStatus = PayStatus.CANCLE;
        } else {
            checkPayResult.setSyncPayStatus("FAIL");
            payStatus = PayStatus.FAIL;
        }
        // 更新状态
        PayConfirm payConfirm = new PayConfirm();
        payConfirm.setOrderType(OrderType.parse(payOrder.getOrderType()));
        payConfirm.setOrderId(payOrder.getOrderId());
        payConfirm.setPayMemo(resultStatus);
        payConfirm.setPayStatus(payStatus);
        payConfirm.setPayType(PayType.ALIPAY);
        payConfirm.setUid(payOrder.getUid());
        PayConfirmResult payConfirmResult = payService.paymentConfirm(payConfirm);
        if (payConfirmResult.getResultCode() != ResultCode.SUCCESS) {
            checkPayResult.setResultCode(payConfirmResult.getResultCode());
            checkPayResult
                    .setResultMessage(payConfirmResult.getResultMessage());
            return checkPayResult;
        }
        if (payConfirmResult.isAsync()) {
            if (payConfirmResult.isRepeat()) {
                if (checkPayResult.getSyncPayStatus().equals("CONFIRM")) {
                    checkPayResult.setSyncPayStatus("REPEAT");
                }
            } else {
                checkPayResult.setSyncPayStatus("SUCCESS");
            }
        }
        if (payStatus.equals(PayStatus.SUCCESS) || payStatus.equals(PayStatus.INPROCESSING)) {

            rechargeSmsNotify(userInfo, payOrder, payStatus);
        }

        checkPayResult.setExtendParams(payOrder.getExtendParams());
        return checkPayResult;
    }

    /**
     * 充值成功短信通知
     *
     * @param userInfo
     * @param payOrder
     * @param orderType
     * @param payStatus
     */
    private void rechargeSmsNotify(UserInfo userInfo, PayOrder payOrder, PayStatus payStatus) {
        if (payOrder.getOrderType() == OrderType.RECHARGE.getValue()) {
            Map<String, String> smsParam = new HashMap<String, String>();
            smsParam.put("mobile", BizUtils.getTailMobile(userInfo.getMobile()));
            smsParam.put("amount", MoneyUtils.format(payOrder.getAmount()));
            SendMessageResult smsResult = messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getCompanyId(),
                    userInfo.getMobile(), MessageId.RECHARGE_SUCCESS, smsParam);
            if (smsResult.getResultCode() != ResultCode.SUCCESS) {
                logger.error("发送短信失败:code:{},resultCode:{}",
                        smsResult.getCode(), smsResult.getResultCode());
            }
        }
    }

    /**
     * 处理微信支付相关业务
     * <p>
     * 支付状态: 0 成功, -1 错误 -2 用户取消
     * </p>
     *
     * @param userInfo
     * @param payOrder
     * @param payResult
     * @return
     */
    private ConfirmPayResult handleWxpayResponse(UserInfo userInfo, PayOrder payOrder,
                                                 PayResult payResult) {
        ConfirmPayResult checkPayResult = new ConfirmPayResult();

        Map<String, String> resultMap = WxPayUtil.parseParams(payResult.getResult());
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("appid", resultMap.get("appid")));
        params.add(new BasicNameValuePair("noncestr", resultMap.get("noncestr")));
        params.add(new BasicNameValuePair("package", resultMap.get("package")));
        params.add(new BasicNameValuePair("partnerid", resultMap
                .get("partnerid")));
        params.add(new BasicNameValuePair("prepayid", resultMap.get("prepayid")));
        params.add(new BasicNameValuePair("timestamp", resultMap
                .get("timestamp")));
        String sign = resultMap.get("sign");
       
        if (!wxPayService.getSignVeryfy(params, sign, payOrder.getPayMethod(),payOrder.getPayChannel())) {
            checkPayResult.setResultCode(ResultCode.ERROR_403);
            checkPayResult.setResultMessage("签名校验失败");
            return checkPayResult;
        }
        PayStatus payStatus = PayStatus.SUCCESS;
        if (payResult.getResultStatus().equals("0")) {
            checkPayResult.setSyncPayStatus("CONFIRM");
            payStatus = PayStatus.INPROCESSING;
        } else if (payResult.getResultStatus().equals("-2")) {
            checkPayResult.setSyncPayStatus("CANCLE");
            payStatus = PayStatus.CANCLE;
        } else {
            checkPayResult.setSyncPayStatus("FAIL");
            payStatus = PayStatus.FAIL;
        }
        PayConfirm payConfirm = new PayConfirm();
        payConfirm.setOrderType(OrderType.parse(payOrder.getOrderType()));
        payConfirm.setOrderId(payOrder.getOrderId());
        payConfirm.setPayMemo(payResult.getResultStatus());
        payConfirm.setPayStatus(payStatus);
        payConfirm.setPayType(PayType.WEIXIN);
        payConfirm.setUid(payOrder.getUid());
        PayConfirmResult payConfirmResult = payService.paymentConfirm(payConfirm);
        if (payConfirmResult.getResultCode() != ResultCode.SUCCESS) {
            checkPayResult.setResultCode(payConfirmResult.getResultCode());
            checkPayResult
                    .setResultMessage(payConfirmResult.getResultMessage());
            return checkPayResult;
        }
        if (payConfirmResult.isAsync()) {
            if (payConfirmResult.isRepeat()) {
                if (checkPayResult.getSyncPayStatus().equals("CONFIRM")) {
                    checkPayResult.setSyncPayStatus("REPEAT");
                }
            } else {
                checkPayResult.setSyncPayStatus("SUCCESS");
            }
            checkPayResult.setSyncPayStatus("SUCCESS");
        }
        if (payStatus.equals(PayStatus.SUCCESS) || payStatus.equals(PayStatus.INPROCESSING)) {
            rechargeSmsNotify(userInfo, payOrder, payStatus);
        }

        checkPayResult.setExtendParams(payOrder.getExtendParams());
        return checkPayResult;
    }

    /**
     * 处理微信支付相关业务
     * <p>
     * 支付状态: 0 成功, -1 错误 -2 用户取消
     * </p>
     *
     * @param userInfo
     * @param payOrder
     * @param payResult
     * @return
     */
    private ConfirmPayResult handleUnionpayResponse(UserInfo userInfo,
                                                    PayOrder payOrder, PayResult payResult) {
        ConfirmPayResult result = new ConfirmPayResult();
        //取消支付返回信息为空，不需要校验
        if (!payResult.getResultStatus().equals("cancel")) {
            boolean flag = AcpService.validateAppResponse(payResult.getResult(), "UTF-8");
            if (!flag) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("签名校验失败");
                return result;
            }
        }
        PayStatus payStatus = PayStatus.SUCCESS;
        if (payResult.getResultStatus().equals("success")) {
            result.setSyncPayStatus("CONFIRM");
            payStatus = PayStatus.INPROCESSING;
        } else if (payResult.getResultStatus().equals("cancel")) {
            result.setSyncPayStatus("CANCLE");
            payStatus = PayStatus.CANCLE;
        } else {
            result.setSyncPayStatus("FAIL");
            payStatus = PayStatus.FAIL;
        }
        PayConfirm payConfirm = new PayConfirm();
        payConfirm.setOrderType(OrderType.parse(payOrder.getOrderType()));
        payConfirm.setOrderId(payOrder.getOrderId());
        payConfirm.setPayMemo(payOrder.getOrderId());
        payConfirm.setPayStatus(payStatus);
        payConfirm.setPayType(PayType.UNIONPAY);
        PayConfirmResult payConfirmResult = payService.paymentConfirm(payConfirm);
        if (payConfirmResult.getResultCode() != ResultCode.SUCCESS) {
            result.setResultCode(payConfirmResult.getResultCode());
            result
                    .setResultMessage(payConfirmResult.getResultMessage());
            return result;
        }
        if (payConfirmResult.isAsync()) {
            if (payConfirmResult.isRepeat()) {
                if (result.getSyncPayStatus().equals("CONFIRM")) {
                    result.setSyncPayStatus("REPEAT");
                }
            } else {
                result.setSyncPayStatus("SUCCESS");
            }
            result.setSyncPayStatus("SUCCESS");
        }
        if (payStatus.equals(PayStatus.SUCCESS) || payStatus.equals(PayStatus.INPROCESSING)) {

            rechargeSmsNotify(userInfo, payOrder, payStatus);
        }
        result.setExtendParams(payOrder.getExtendParams());
        return result;

    }


    /**
     * 验证订单
     *
     * @param order
     * @param payPush
     * @return 200 正常 ，201重复通知 ，202重复支付(全部退款),203重复支付(部分退款)，204（已取消或者支付失败）
     * @throws BusinessException
     */
    private int checkPayOrder(PayOrder order, PayPush payPush)
            throws BusinessException {
        if (order == null) {
            throw new BusinessException("订单不存在!");
        }
        int totalAmount = Integer.parseInt(payPush.getTotalFee());
        if (totalAmount != order.getAmount() * 100) {
            return -3;//支付金额有问题
        }
        if (order.getStatus() == PayStatus.UNPAY.getValue() ||
                order.getStatus() == PayStatus.INPROCESSING.getValue()) {
            // 未支付 || 正在支付, 需要执行支付操作
            return 0;
        }
        /*
         * 判断订单状态
		 */
        if (order.getStatus() == PayStatus.SUCCESS.getValue() && order.getPayType() == payPush.getPayType()) {
            //重复通知
            return 1;
        }
        /*
		 * 支付状态是成功的情况下：那么返回重复支付
		 * 支付状态是确认中并且订单的支付类型和push的支付类型不一样：那么返回重复支付
		 * 判断订单状态
		 */
        if (order.getStatus() == PayStatus.SUCCESS.getValue() ||
                (order.getStatus() == PayStatus.INPROCESSING.getValue() && order.getPayType() != payPush.getPayType())) {
            return -1;
        }

        if (order.getStatus() == PayStatus.CANCLE.getValue() ||
                order.getStatus() == PayStatus.FAIL.getValue()) {
            // 已取消或者支付失败
            return -2;
        }
        return 0;
    }
    @Override
    public UnionpaydfPayRespData unionpayDfPay(UnionpaydfPayReqData req) {
        UnionpaydfPayRespData result = new UnionpaydfPayRespData();
        if (req.getUserWalletDetailId() == null) {
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("id不能为空");
            return result;
        }
        UserWalletDetail userWalleDetail = userWalletService.getUserWalleDetail(req.getUserWalletDetailId());
        if (userWalleDetail == null) {
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("打款信息记录不存在");
            return result;
        }
        long amount = (long) (userWalleDetail.getAvailableAmount() * 100);//银联单位为分，所以需要转化
        String amountStr = Long.toString(amount);
        UserBankCard userBankCard = userWalletService.getUserBankCardById(userWalleDetail.getFundTarget());
        String accNo = userBankCard.getAccount();
        //UserAuthInfo userAuthInfo=userService.getUserAuthInfo(walletDetail.getUid());
        //String idCardNo=userAuthInfo.getIdcardno();
        UnionpaydfData data = new UnionpaydfData();
        data.setAccessType("0");
        data.setAccType("01");

        data.setBizType("000401");
        data.setChannelType("07");
        data.setCurrencyCode("156");

        data.setAccNo("6216261000000000018");//生产环境改为accNo
        data.setCertifId("01");
        data.setCertifTp("341126197709218366");//生产环境改为idCardNo
        data.setTxnAmt("10");//生产环境改为amountStr;

        data.setEncryptCertId(AcpService.getEncryptCertId());
        data.setEncoding("UTF-8");
        data.setOrderId(userWalleDetail.getOrderId());
        data.setMerId(merId);//商户编码，后期配置文件中获取
        data.setReqReserved("{cardNumberLock=1&type=1}");
        data.setReserved("{cardNumberLock=1}");
        data.setSignMethod("01");
        data.setVersion("5.0.0");

        data.setTxnSubType("00");
        data.setTxnType("12");
        data.setBackUrl(unionpaydfNotifyUrl);
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String txnTime = format.format(now);
        data.setTxnTime(txnTime);
        UnionpaydfPayRespData resp = unionpayService.unionpaydf(data);
		/*
		if(resp.getResultCode()==ResultCode.ERROR_201){
			walletDetail.setPayStatus(PayStatus.FAIL.getValue());
		}
		if(resp.isSuccess()){
			walletDetail.setPayStatus(PayStatus.INPROCESSING.getValue());
		}
		walletDetail.setPayMemo(resp.getUnionpayPrepayInfo().getRespMsg());		
		walletDetail.setPayUser("系统");
		walletDetail.setModifiedUser("系统");
		*/
        userWalletService.withdrawalscashPayConfirm(userWalleDetail);

        result.setResultCode(resp.getResultCode());
        result.setResultMessage(resp.getResultMessage());
        result.setUnionpayPrepayInfo(resp.getUnionpayPrepayInfo());

        return result;
    }

    @Override
    @Transactional
    public UnionpayNotifyResult unionpayNotify(UnionpayNotifyReq data) throws Exception {

        UnionpayNotifyResult result = new UnionpayNotifyResult();
        UnionpayPrepayInfo info = new UnionpayPrepayInfo();
        try {
            BeanUtils.populate(info, data.getMap());
            logger.debug("mis------unionpayNotify接收参数为：" + JSONObject.toJSONString(info));
            UnionpayPushLog log = new UnionpayPushLog();
            //先查看是否已经存有改订单记录的日志
            //UnionpayPushLog pushLog=this.payService.getUnionPushLogByOrderId(info.getOrderId());
            org.springframework.beans.BeanUtils.copyProperties(info, log);

            /**
             * 00 交易成功
             */
            if (info.getRespCode().equals("00")) {
                log.setStatus(UnionpayStatus.SUCCESS.getValue());
            } else if (info.getRespCode().equals("03") || info.getRespCode().equals("05")) {
                /**
                 * 03 交易通讯超时，请发起查询交易结果
                 * 05交易已受理，请稍后查询交易结果
                 */
                log.setStatus(UnionpayStatus.PAYING.getValue());
            } else {

                log.setStatus(UnionpayStatus.FALSE.getValue());
            }
            //消费类型
            log.setType(TradeType.XIAOFEI.getValue());
            //充值类型
            //log.setType(TradeType.RECHARGE.getValue());
            payService.unionpayPushLog(log);
            if (!AcpService.validate(data.getMap(), "UTF-8")) {
                logger.error("银联推送签名验证不合法!" + data.getMap());
                // 签名不合法
                throw new BusinessException("签名验证失败!签名不合法!");
            }
            //没有此订单的日志记录才需要记录
            if (info.getRespCode().equals("00")) {
                // 转换为bean
                UnionpayPush pushInfo = new UnionpayPush();
                BeanUtils.copyProperties(info, pushInfo);
                //计算充值金额
                pushInfo.setTotalFee(info.getTxnAmt().toString());
                pushInfo.setPayType(PayType.UNIONPAY.getValue());
                pushInfo.setOrderId(pushInfo.getOutTradeNo());


                PayNotifyResult payNotifyResult = handlePayOrder(pushInfo);
                // 支付宝需要在页面上直接打印success
                if (payNotifyResult.isSuccess()) {
                    result.setResultCode(ResultCode.SUCCESS);
                } else {
                    logger.warn("unionpayNotify handlePayOrder:{}", JSON.toJSONString(payNotifyResult));
                    // 记录处理的异常信息
                    String pushJson = JSON.toJSONString(info);
                    String errMsg = String.format("%s [%s]",
                            payNotifyResult.getResultCode(), payNotifyResult.getResultMessage());
                    // 记录推送异常信息
                    this.payService.aliushError(pushJson, errMsg);
                    result.setResultCode(ResultCode.ERROR_500);
                    result.setResultMessage(errMsg);
                }
                return result;
            }
        } catch (Exception e) {

            // 记录处理的异常信息
            String pushJson = JSON.toJSONString(info);
            String errMsg = String.format("%s [%s]",
                    ResultCode.ERROR_500_MESSAGE, e.getMessage());
            // 记录推送异常信息
            this.payService.aliushError(pushJson, errMsg);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage(errMsg);
            logger.error("unionpayNotify {} ", e);
        }
        return result;
    }

    @Override
    @Transactional
    public UnionpayNotifyResult unionpaydfNotify(UnionpayNotifyReq data) throws Exception {

        UnionpayNotifyResult result = new UnionpayNotifyResult();
        UnionpayPrepayInfo info = new UnionpayPrepayInfo();
        try {
            info = data.getUnionpayPrepayInfo();
            BeanUtils.populate(info, data.getMap());
            UnionpayPushLog log = new UnionpayPushLog();

            //先查看是否已经存有改订单记录的日志
            //UnionpayPushLog pushLog=this.payService.getUnionPushLogByOrderId(info.getOrderId());
            org.springframework.beans.BeanUtils.copyProperties(info, log);

            /**
             * 00 交易成功
             */
            if (info.getRespCode().equals("00")) {
                log.setStatus(UnionpayStatus.SUCCESS.getValue());
            } else if (info.getRespCode().equals("03") || info.getRespCode().equals("05")) {
                /**
                 * 03 交易通讯超时，请发起查询交易结果
                 * 05交易已受理，请稍后查询交易结果
                 */
                log.setStatus(UnionpayStatus.PAYING.getValue());
            } else {

                log.setStatus(UnionpayStatus.FALSE.getValue());

            }
            //代付类型
            log.setType(TradeType.DAIFU.getValue());
            payService.unionpaydfPushLog(log);
            if (!AcpService.validate(data.getMap(), data.getMap().get("encoding"))) {
                logger.error("银联支付推送签名验证不合法!");
                // 签名不合法
                throw new BusinessException("签名验证失败!签名不合法!");
            }
            logger.debug("mis------unionpaydfNotify接收参数为：" + JSONObject.toJSONString(info));
            if (info.getRespCode().equals("00") || info.getOrigRespCode().equals("00")) {
                // 转换为bean
                UnionpayPush pushInfo = new UnionpayPush();
                org.springframework.beans.BeanUtils.copyProperties(info, pushInfo);
                pushInfo.setPayType(PayType.UNIONPAY.getValue());
                //计算消费金额
                String amount = info.getTxnAmt().toString();
                pushInfo.setTotalFee(amount);
                String orderId = pushInfo.getOrderId();
                UserWalletDetail order = null;
                //如果是提现订单
                if (orderId.startsWith("T")) {
                    // 提现订单
                    // 获取订单信息
                    order = userWalletService.getUserwalleDetailByOrderId(orderId);
                    // 验证订单及支付信息
                    // 通知订单, 执行修改事物
                    this.payService.unionPaydfNotify(order, pushInfo);

                }
                // 发送审核通过短信.
                Map<String, String> param = new HashMap<String, String>();
                UserInfo userInfo = userService.getUserInfo(order.getUid());
                logger.debug("mis------userInfo信息为：" + JSONObject.toJSONString(userInfo));
                SendMessageResult smsResult = messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getCompanyId(), userInfo.getMobile(), MessageId.WITH_DRAWALS_SUCCESS, param);
                if (smsResult.isSuccess()) {
                    logger.error("发送短信失败:code:{},message:{}", new Object[]{result.getResultCode(), result.getResultMessage()});
                }

                result.setResultCode(ResultCode.SUCCESS);
                return result;
            }

        } catch (Exception e) {

            // 记录处理的异常信息
            String pushJson = JSON.toJSONString(info);
            String errMsg = String.format("%s [%s]",
                    ResultCode.ERROR_500_MESSAGE, e.getMessage());
            // 记录推送异常信息
            this.payService.aliushError(pushJson, errMsg);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage(errMsg);
            logger.error("unionpayNotify {} ", e);
        }
        return result;

    }

    @Override
    public QueryPayOrderResult queryPayOrder(Long uid, String orderId) {
        QueryPayOrderResult result = new QueryPayOrderResult();
        try {
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid 不能为空");
                return result;
            }
            if (StringUtils.isBlank(orderId)) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("orderId 不能为空");
                return result;
            }
            PayOrder payOrder = this.payOrderService.getPayOrder(uid, orderId);
            if (payOrder != null) {
                BeanUtils.copyProperties(result, payOrder);
            } else {
                result.set(ResultCode.ERROR_401, "订单不存在");
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("queryPayOrder:{}", e);
        }
        return result;
    }
    
    @Override
	public PayOrderListResult queryPayOrderList(Long uid, Integer type, Integer status, Integer offset) {
    	PayOrderListResult result = new PayOrderListResult();
    	try {
    		if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid 不能为空");
                return result;
            }
    		if (!OrderType.include(type)) {
				result.set(ResultCode.ERROR_201, OrderType.tip());
				return result;
			}
    		if (!PayStatus.inclide(status)) {
    			result.set(ResultCode.ERROR_201, PayStatus.tip());
				return result;
			}
    		offset = offset == null ? 0 : offset;
    		PageResult<PayOrderInfo> pageResult = payOrderService.queryPayOrderList(uid, type, status, offset, Constants.DEFAULT_PAGE_SIZE);
    		
    		result.setOrderInfos(pageResult.getData());
    		result.setTotalCount(pageResult.getTotal());
    		result.setHasNext(pageResult.isHasNext());
    		result.setNextOffset(pageResult.getNextOffset());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("queryPayOrderList:{}", e);
		}
		return result;
	}
    
    private UnionpayQueryReqData setQueryParams(UnionpayPushLog log) {
        UnionpayQueryReqData data = new UnionpayQueryReqData();
        data.setAccessType("0");
        data.setBizType("000401");
        data.setEncoding("UTF-8");
        data.setMerId(merId);
        data.setOederId(log.getOrderId());
        data.setQueryId(log.getQueryId());
        data.setSignMethod("01");
        data.setTxmTime(log.getTxnTime());
        data.setTxnSubType("00");
        data.setTxnType("00");
        return data;
    }

	@Override
	public QueryRefundsResult queryRefunds(QueryRefundsReq queryRefundsReq) {
		QueryRefundsResult result = new QueryRefundsResult();
        try {
        	if(queryRefundsReq.getLimit()<=0){
        		result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("limit 必须大于0");
                return result;
        	}
            if (!(queryRefundsReq.getStatus()==MemberRefundStatus.FAIL.getValue()||
            	queryRefundsReq.getStatus()==MemberRefundStatus.PROCESSING.getValue())) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("status 只支持("+MemberRefundStatus.FAIL.getValue()+","+MemberRefundStatus.PROCESSING.getValue()+")");
                return result;
            }
            List<MemberRefund> list=null;
            if(queryRefundsReq.getStatus()==MemberRefundStatus.FAIL.getValue()){
            	list=memberRefundService.getFailList(queryRefundsReq.getLimit());
            }
            else{
            	list=memberRefundService.getProcessingList(queryRefundsReq.getLimit());
            }
            List<MemberRefundInfo> refunds=new ArrayList<MemberRefundInfo>();
            if(list!=null&&!list.isEmpty()){
            	for(MemberRefund memberRefund:list){
            		MemberRefundInfo refund=new MemberRefundInfo();
            		BeanUtils.copyProperties(refund, memberRefund);
            		refunds.add(refund);
            	}
            }
            result.setRefunds(refunds);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("queryRefunds:{}", e);
        }
        return result;
	}

	@Override
	public Result retryRefund(Long id, Long uid) {
		Result result = new DefaultResult();
        try {
        	if(id==null){
        		result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("退款ID 不能为空");
                return result;
        	}
        	if(uid==null){
        		result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("uid 不能为空");
                return result;
        	}
        	MemberRefund memberRefund=memberRefundService.getById(id);
        	if(memberRefund==null){
        		result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("退款记录不存在");
                return result;
        	}
        	if(memberRefund.getRefundStatus()!=MemberRefundStatus.FAIL.getValue()){
        		result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("当前退款记录不能重新发起退款申请");
                return result;
        	}
        	payService.refund(memberRefund);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("retryRefund:{}", e);
        }
        return result;	
    }

	@Override
	public Result checkRefundStatus(Long id, Long uid) {
		Result result = new DefaultResult();
        try {
        	if(id==null){
        		result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("退款ID 不能为空");
                return result;
        	}
        	if(uid==null){
        		result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("uid 不能为空");
                return result;
        	}
        	MemberRefund memberRefund=memberRefundService.getById(id);
        	if(memberRefund==null){
        		result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("退款记录不存在");
                return result;
        	}
        	if(memberRefund.getRefundStatus()!=MemberRefundStatus.PROCESSING.getValue()){
        		result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("只能处理退款中的记录");
                return result;
        	}
        	payService.checkRefundStatus(memberRefund);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("retryRefund:{}", e);
        }
        return result;	
	}
}
