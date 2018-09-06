package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.payment.PaymentReqData;
import com.edianniu.pscp.sps.bean.payment.PaymentResult;
import com.edianniu.pscp.sps.bean.payment.balance.FacilitatorBalanceReqData;
import com.edianniu.pscp.sps.bean.payment.balance.FacilitatorBalanceResult;
import com.edianniu.pscp.sps.bean.payment.paypage.AliPayCallbackResult;
import com.edianniu.pscp.sps.bean.payment.paypage.WxPayPollingResult;

import java.util.Map;

/**
 * ClassName: PaymentInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-31 19:43
 */
public interface PaymentInfoService {
    FacilitatorBalanceResult getFacilitatorBalance(FacilitatorBalanceReqData facilitatorBalanceReqData);

    /**
     * 支付宝支付回调
     *
     * @param params
     * @param uid
     * @return
     */
    AliPayCallbackResult aliPayCallback(Map<String, String> params, Long uid);

    /**
     * 微信支付轮询
     *
     * @param orderId
     * @param uid
     * @return
     */
    WxPayPollingResult wxPayPolling(String orderId, Long uid);

    /**
     * 支付
     *
     * @param paymentReqData
     * @return
     */
    PaymentResult payment(PaymentReqData paymentReqData);
}
