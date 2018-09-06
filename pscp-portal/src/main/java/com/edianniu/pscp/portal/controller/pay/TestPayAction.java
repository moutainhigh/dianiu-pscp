package com.edianniu.pscp.portal.controller.pay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.pay.ConfirmPayReq;
import com.edianniu.pscp.mis.bean.pay.ConfirmPayResult;
import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.pay.PayMethod;
import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.bean.pay.PreparePayReq;
import com.edianniu.pscp.mis.bean.pay.PreparePayResult;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.utils.R;
/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月22日 上午11:28:32 
 * @version V1.0
 */
@Controller
@RequestMapping("/pay")
public class TestPayAction extends AbstractController {
	private static final Logger logger = LoggerFactory
			.getLogger(TestPayAction.class);

	@Autowired
	@Qualifier("payInfoService")
	private PayInfoService payInfoService;
	
	@RequestMapping(value = "/alipay_return", method = RequestMethod.GET)
	@ResponseBody
	public R alipayReturn(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> requestParams = request.getParameterMap();
		logger.info(String.format("AliPay confirm notify [alipush - param] : %s", JSON.toJSONString(requestParams)));
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
			String name = (String) iter.next();
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
		StringBuffer paramsLink=new StringBuffer(128);
		int i=0;
		for(Iterator iter = params.keySet().iterator(); iter.hasNext();){
			String key = (String) iter.next();
			String value=params.get(key);
			if (i == params.size() - 1) {
				paramsLink.append(key).append("=").append(value);
            } else {
            	paramsLink.append(key).append("=").append(value).append("&");
            }
			i++;
		}
		ConfirmPayReq confirmPayReq=new ConfirmPayReq();
		confirmPayReq.setUid(this.getUserId());
		confirmPayReq.setOrderId(params.get("out_trade_no"));
		confirmPayReq.setPayMethod(PayMethod.PC.getValue());
		confirmPayReq.setOrderType(OrderType.RECHARGE.getValue());
		confirmPayReq.setPayType(PayType.ALIPAY.getValue());
		confirmPayReq.setResult(paramsLink.toString());
		confirmPayReq.setResultStatus("success");
		ConfirmPayResult result=payInfoService.confirmPay(confirmPayReq);
		if(!result.isSuccess()){
			return R.error(result.getResultCode(),result.getResultMessage());
		}
		return R.ok();
	}
	@RequestMapping(value = "/alipay_return_order", method = RequestMethod.GET)
	@ResponseBody
	public R alipayReturnOrder(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> requestParams = request.getParameterMap();
		logger.info(String.format("AliPay confirm notify [alipush - param] : %s", JSON.toJSONString(requestParams)));
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
			String name = (String) iter.next();
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
		StringBuffer paramsLink=new StringBuffer(128);
		int i=0;
		for(Iterator iter = params.keySet().iterator(); iter.hasNext();){
			String key = (String) iter.next();
			String value=params.get(key);
			if (i == params.size() - 1) {
				paramsLink.append(key).append("=").append(value);
            } else {
            	paramsLink.append(key).append("=").append(value).append("&");
            }
			i++;
		}
		ConfirmPayReq confirmPayReq=new ConfirmPayReq();
		confirmPayReq.setUid(this.getUserId());
		confirmPayReq.setOrderId(params.get("out_trade_no"));
		confirmPayReq.setPayMethod(PayMethod.PC.getValue());
		confirmPayReq.setOrderType(OrderType.SOCIAL_WORK_ORDER_PAY.getValue());
		confirmPayReq.setPayType(PayType.ALIPAY.getValue());
		confirmPayReq.setResult(paramsLink.toString());
		confirmPayReq.setResultStatus("200");
		logger.info("PC网站支付成功返回参数:{}",paramsLink.toString());
		ConfirmPayResult result=payInfoService.confirmPay(confirmPayReq);
		if(!result.isSuccess()){
			return R.error(result.getResultCode(),result.getResultMessage());
		}
		return R.ok();
	}
	@RequestMapping(value = "/wxpay_return", method = RequestMethod.GET)
	public void wxpayReturn(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> requestParams = request.getParameterMap();
		logger.info(String.format("WxPay confirm notify [alipush - param] : %s", JSON.toJSONString(requestParams)));
	}
	/**
	 * 同步确认
	 * @param confirmPayReq
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/confirm")
	public R save(@RequestBody ConfirmPayReq confirmPayReq){
		confirmPayReq.setUid(this.getUserId());
		ConfirmPayResult result=payInfoService.confirmPay(confirmPayReq);
		if(!result.isSuccess()){
			return R.error(result.getResultCode(),result.getResultMessage());
		}
		return R.ok();
	}
	/**
	 * 予支付
	 * @param payMethod
	 * @param amount
	 * @param request
	 * @param response
	 */
	@RequestMapping("/prepare")
	public String test(@ModelAttribute PreparePayReq req,HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			req.setIp(request.getRemoteAddr());
			req.setUid(this.getUserId());
			if(req.getOrderType()==OrderType.SOCIAL_WORK_ORDER_PAY.getValue()){
				req.setReturnUrl("http://portal.edianniu.com/pay/alipay_return_order");
			}
			PreparePayResult result = payInfoService.preparePay(req);
			if (result.isSuccess()) {
				
				if(result.getAlipay()!=null){//支付宝支付，需要用支付类型进行判断
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(result.getAlipay().getParams());
					response.getWriter().flush();
					response.getWriter().close();
				}
				else if(result.getWalletpay()!=null){//余额支付，需要用支付类型进行判断
					ConfirmPayReq confirmPayReq=new ConfirmPayReq();
					confirmPayReq.setUid(this.getUserId());
					confirmPayReq.setOrderId(result.getOrderId());
					confirmPayReq.setOrderType(req.getOrderType());
					confirmPayReq.setPayMethod(req.getPayMethod());
					confirmPayReq.setPayType(req.getPayType());
					confirmPayReq.setResult("");
					confirmPayReq.setResultStatus("success");//余额支付只支持success
					ConfirmPayResult confirmPayResult=payInfoService.confirmPay(confirmPayReq);
					if(confirmPayResult.isSuccess()){
						response.setContentType("text/html;charset=utf-8");
						response.getWriter().write("支付成功");
						response.getWriter().flush();
						response.getWriter().close();	
					}
					else{
						response.setContentType("text/html;charset=utf-8");
						response.getWriter().write(JSON.toJSONString(confirmPayResult));
						response.getWriter().flush();
						response.getWriter().close();
					}
					
				}
				else if(result.getWeixinpay()!=null){
					model.addAttribute("orderAmount", req.getAmount());
					model.addAttribute("orderId", result.getOrderId());
					model.addAttribute("codeUrl", result.getWeixinpay().getCodeUrl());
					return "pay/wxpay.html";
				}
				
			} else {
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(
						result.getResultCode() + ":"
								+ result.getResultMessage());
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			try {
				response.getWriter().print("系统异常");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        return null;
	}
}
