package com.edianniu.pscp.portal.controller.workorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.pay.StartPayReq;
import com.edianniu.pscp.mis.bean.pay.StartPayResult;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.portal.commons.ResultCode;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.utils.IPUtil;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.sps.bean.payment.PaymentReqData;
import com.edianniu.pscp.sps.bean.payment.PaymentResult;
import com.edianniu.pscp.sps.bean.payment.balance.FacilitatorBalanceReqData;
import com.edianniu.pscp.sps.bean.payment.balance.FacilitatorBalanceResult;
import com.edianniu.pscp.sps.bean.payment.paypage.AliPayCallbackResult;
import com.edianniu.pscp.sps.bean.payment.paypage.PayPageReqData;
import com.edianniu.pscp.sps.bean.payment.paypage.WxPayPollingResult;
import com.edianniu.pscp.sps.service.dubbo.PaymentInfoService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付
 * ClassName: PaymentController
 * Author: tandingbo
 * CreateTime: 2017-05-31 19:38
 */
@Controller
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PayInfoService payInfoService;
    @Autowired
    private PaymentInfoService paymentInfoService;

    /**
     * 支付宝支付页面
     *
     * @return
     */
    @RequestMapping("cp/payment/alipay")
    public ModelAndView alipay(@ModelAttribute PayPageReqData aliPayInfo) {
        ModelAndView modelAndView = new ModelAndView("/cp/alipay.html");
        modelAndView.addObject("orderType", aliPayInfo.getOrderType());
        String thirdPartyPaymentInfo = new String(Base64.decodeBase64(aliPayInfo.getThirdPartyPaymentInfo().getBytes()));
        modelAndView.addObject("thirdPartyPaymentInfo", thirdPartyPaymentInfo);
        return modelAndView;
    }

    /**
     * 微信支付页面
     * 
     * @returnd
     */
    @RequestMapping("cp/payment/wxpay")
    public ModelAndView wxpay(@ModelAttribute PayPageReqData wxPayInfo) {
        ModelAndView modelAndView = new ModelAndView("/cp/wechat_pay.html");
        modelAndView.addObject("amount", wxPayInfo.getAmount());
        modelAndView.addObject("orderId", wxPayInfo.getOrderId());
        modelAndView.addObject("orderType", wxPayInfo.getOrderType());
        modelAndView.addObject("thirdPartyPaymentInfo", wxPayInfo.getThirdPartyPaymentInfo());
        return modelAndView;
    }

    /**
     * 支付宝支付回调接口
     *
     * @param request
     * @return
     */
    @RequestMapping("cp/payment/alipay/callback")
    public String aliPayCallback(HttpServletRequest request, Model model) {
        Map<String, String[]> requestParams = request.getParameterMap();
        logger.info(String.format("AliPay confirm notify [alipush - param] : %s", JSON.toJSONString(requestParams)));
        Map<String, String> params = new HashMap<String, String>();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        AliPayCallbackResult result = paymentInfoService.aliPayCallback(params, ShiroUtils.getUserId());
        String url = result.getPageUrl();
        if (url.contains("cp/payment/failure")) {
            return failure(model);
        } else if (url.contains("cp/payment/success")) {
            return success(model);
        } else {
            return "redirect:" + url;
        }

    }

    /**
     * 支付失败页面
     *
     * @return
     */
    @RequestMapping("cp/payment/failure")
    public String failure(Model model) {

        model.addAttribute("msg", "支付失败");
        return "/cp/pay_failure.html";
    }

    /**
     * 支付成功页面
     *
     * @return
     */
    @RequestMapping("cp/payment/success")
    public String success(Model model) {

        model.addAttribute("msg", "支付成功");
        return "/cp/pay_success.html";
    }

    /**
     * 微信支付轮询接口
     *
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping("cp/payment/wxpay/polling/{orderId}")
    public R wxPayPolling(@PathVariable("orderId") String orderId) {
        WxPayPollingResult result = paymentInfoService.wxPayPolling(orderId, ShiroUtils.getUserId());
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

    /**
     * 登录服务商余额信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cp/workorder/payment/balance")
    @RequiresPermissions("cp:workorder:payment:balance")
    public R balance() {
        SysUserEntity userInfo = ShiroUtils.getUserEntity();

        FacilitatorBalanceReqData facilitatorBalanceReqData = new FacilitatorBalanceReqData();
        facilitatorBalanceReqData.setUid(userInfo.getUserId());
        FacilitatorBalanceResult result = paymentInfoService.getFacilitatorBalance(facilitatorBalanceReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

    /**
     * 发起支付
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cp/payment/start")
    public R startPayment(@RequestBody JSONObject jsonObject) {
        SysUserEntity userInfo = ShiroUtils.getUserEntity();

        if (jsonObject.getInteger("orderType") == null) {
            return R.error(ResultCode.ERROR, "请求参数错误");
        }
        // 订单类型
        Integer orderType = jsonObject.getInteger("orderType");
        // 订单编号
        String orderIds = jsonObject.getString("orderIds") == null ? "" : jsonObject.getString("orderIds");

        // 充值时：orderIds可为空
        if (!orderType.equals(1) && StringUtils.isBlank(orderIds)) {
            return R.error(ResultCode.ERROR, "请求参数错误");
        }

        StartPayReq startPayReq = new StartPayReq();
        startPayReq.setUid(userInfo.getUserId());
        startPayReq.setOrderIds(jsonObject.getString("orderIds"));
        startPayReq.setOrderType(jsonObject.getInteger("orderType"));

        StartPayResult result = payInfoService.startPay(startPayReq);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

    /**
     * 工单支付
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cp/workorder/chieforder/payment")
    @RequiresPermissions("cp:workorder:chieforder:payment")
    public R chieforderPayment(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String returnUrl = "main.html?t=" + System.nanoTime() + "#cp/workorder.html";
        PaymentResult result = payment(jsonObject, request, returnUrl);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

    /**
     * 社会工单支付
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cp/workorder/social/payment")
    @RequiresPermissions("cp:workorder:social:payment")
    public R socialPayment(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String returnUrl = "main.html?t=" + System.nanoTime() + "#cp/workorder/social/index.html";
        PaymentResult result = payment(jsonObject, request, returnUrl);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

    /**
     * 需求订单保证金支付
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "cp/needs/order/payment")
    @RequiresPermissions("cp:needs:order:payment")
    public R needsOrderPayment(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String returnUrl = "main.html?t=" + System.nanoTime() + "#cp/requirement.html";
        PaymentResult result = payment(jsonObject, request, returnUrl);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }
    
    /**
     * 项目支付
     * @return
     */
    @ResponseBody
    @RequestMapping(value="cp/project/payment")
    @RequiresPermissions("cp:project:payment")
    public R projectPayment(@RequestBody JSONObject jsonObject, HttpServletRequest request){
    	String returnUrl = "mian.html?t=" + System.nanoTime() + "#cp/engineeringproject.html";
    	PaymentResult result = payment(jsonObject, request, returnUrl);
    	if (result.getResultCode() != ResultCode.SUCCESS) {
    		 return R.error(result.getResultCode(), result.getResultMessage());
		}
    	return R.ok().put("result", result);
    }

    private PaymentResult payment(JSONObject jsonObject, HttpServletRequest request, String returnUrl) {
        PaymentReqData paymentReqData = JSON.parseObject(jsonObject.toJSONString(), PaymentReqData.class);
        paymentReqData.setIp(IPUtil.getIpAddress(request));
        paymentReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        paymentReqData.setReturnUrl(returnUrl);

        return paymentInfoService.payment(paymentReqData);
    }
}
