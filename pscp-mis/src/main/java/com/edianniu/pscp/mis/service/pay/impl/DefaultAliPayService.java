/**
 *
 */
package com.edianniu.pscp.mis.service.pay.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.edianniu.pscp.mis.bean.pay.AliPayPrepayReq;
import com.edianniu.pscp.mis.bean.pay.AliPayPrepayResult;
import com.edianniu.pscp.mis.bean.pay.AliPayRefundQueryReqData;
import com.edianniu.pscp.mis.bean.pay.AliPayRefundQueryRespData;
import com.edianniu.pscp.mis.bean.pay.AliPayRefundReqData;
import com.edianniu.pscp.mis.bean.pay.AliPayRefundRespData;
import com.edianniu.pscp.mis.bean.pay.AlipayPrepayInfo;
import com.edianniu.pscp.mis.bean.pay.AlipayTradePagePayData;
import com.edianniu.pscp.mis.bean.pay.AlipayTradePrecreateData;
import com.edianniu.pscp.mis.bean.pay.PayMethod;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.pay.AliPayService;
import com.edianniu.pscp.mis.util.alipay.AlipayConfig;

/**
 * @author cyl
 */
@Service
@Repository("aliPayService")
public class DefaultAliPayService implements AliPayService {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultAliPayService.class);
	private AlipayClient alipayClient = null;
	@Autowired
	private AlipayConfig alipayConfig;
	public DefaultAliPayService() {
		
	}
	@PostConstruct
	public void init(){
		alipayClient = new DefaultAlipayClient(
				alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getAppPrivateKey(),
				alipayConfig.getFormat(), alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType()); // 获得初始化的AlipayClient
	}
	
	@Override
	public AliPayRefundRespData refund(AliPayRefundReqData reqData) {
		AliPayRefundRespData respData = new AliPayRefundRespData();
		try {
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			AlipayTradeRefundModel  bizModel=new AlipayTradeRefundModel();
			bizModel.setOutRequestNo(reqData.getOutRefundNo());
			bizModel.setOutTradeNo(reqData.getOutTradeNo());
			bizModel.setRefundAmount(reqData.getRefundFee());
			bizModel.setRefundReason(reqData.getRefundReason());
			bizModel.setTradeNo(reqData.getTradeNo());
			//bizModel.setOperatorId("op00001");
			//bizModel.setStoreId(storeId);
			//bizModel.setTerminalId(terminalId);
			request.setBizModel(bizModel);
			AlipayTradeRefundResponse response=alipayClient.execute(request);
			if(response.isSuccess()){
				logger.info("支付宝退款接口:{}", "调用成功");
				respData.setBuyerLogonId(response.getBuyerLogonId());
				respData.setBuyerUserId(response.getBuyerUserId());
				respData.setFundChange(response.getFundChange());
				respData.setOutTradeNo(response.getOutTradeNo());
				respData.setRefundFee(response.getRefundFee());
				respData.setRefundTime(response.getGmtRefundPay());
				respData.setStoreName(response.getStoreName());
				respData.setTradeNo(response.getTradeNo());
			}
			else{
				logger.error("支付宝退款接口:{}", "调用失败");
				StringBuffer errs=new StringBuffer(128);
				errs.append("code:").append(response.getCode()).append(",msg:").append(response.getMsg());
				errs.append("subCode:").append(response.getSubCode()).append(",subMsg:").append(response.getSubMsg());
				respData.set(ResultCode.ERROR_401, errs.toString());
			}
		} catch (AlipayApiException e) {
			logger.error("支付宝退款接口:{}", e);
			respData.set(ResultCode.ERROR_500, e.getMessage());
		}

		return respData;
	}

	@Override
	public AliPayRefundQueryRespData refundQuery(AliPayRefundQueryReqData reqData) {
		AliPayRefundQueryRespData respData=new AliPayRefundQueryRespData();
		try {
			AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
			AlipayTradeFastpayRefundQueryModel bizModel=new AlipayTradeFastpayRefundQueryModel();
			bizModel.setOutRequestNo(reqData.getOutRefundNo());
			bizModel.setOutTradeNo(reqData.getOutTradeNo());
			bizModel.setTradeNo(reqData.getTradeNo());
			request.setBizModel(bizModel);
			AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				logger.info("支付宝退款查询接口:{}", "调用成功");
				respData.setOutRefundNo(response.getOutRequestNo());
				respData.setOutTradeNo(response.getOutTradeNo());
				respData.setRefundAmount(response.getRefundAmount());
				respData.setRefundReason(response.getRefundReason());
				respData.setTotalAmount(response.getTotalAmount());
				respData.setTradeNo(response.getTradeNo());
			} else {
				logger.error("支付宝退款查询接口:{}", "调用失败");
				StringBuffer errs=new StringBuffer(128);
				errs.append("code:").append(response.getCode()).append(",msg:").append(response.getMsg());
				errs.append("subCode:").append(response.getSubCode()).append(",subMsg:").append(response.getSubMsg());
				respData.set(ResultCode.ERROR_401, errs.toString());
			}
		} catch (AlipayApiException e) {
			logger.error("支付宝退款查询接口:{}", e);
			respData.set(ResultCode.ERROR_500, e.getMessage());
		}
		return respData;
	}

	@Override
	public AliPayPrepayResult prepay(AliPayPrepayReq req) {
		AliPayPrepayResult result = new AliPayPrepayResult();
		AlipayPrepayInfo alipay = new AlipayPrepayInfo();
		String payMethod=req.getPayMethod();
		if(payMethod.equals(PayMethod.APP.getValue())){
			AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();// 创建API对应的request
			String notifyUrl = alipayConfig.getAlipayNotifyUrl();
			String out_trade_no = req.getOrderId();
			//String seller_id = "hzdntec@edianniu.com";
			AlipayTradeAppPayModel  bizModel=new AlipayTradeAppPayModel();
			bizModel.setBody(req.getBody());
			bizModel.setOutTradeNo(out_trade_no);
			//bizModel.setExtendParams(extendParams);
			//bizModel.setPassbackParams(passbackParams);
			bizModel.setTimeoutExpress("30m");
			//bizModel.setSellerId(seller_id);
			bizModel.setSubject(req.getSubject());
			bizModel.setTotalAmount(req.getTotalAmount());
			bizModel.setProductCode("QUICK_MSECURITY_PAY");
			alipayRequest.setBizModel(bizModel);
			alipayRequest.setNotifyUrl(notifyUrl);
			
			try {
				AlipayTradeAppPayResponse response= alipayClient.sdkExecute(alipayRequest);
				alipay.setParams(response.getBody());
				result.setAlipayPrepayInfo(alipay);
			} catch (AlipayApiException e) {
				logger.error("支付宝统一下单接口:{}", e);
				result.set(ResultCode.ERROR_218, "AlipayTradeAppPayRequest error:"+e.getErrMsg());
			}
		}
		else if(payMethod.equals(PayMethod.PC.getValue())){
			AlipayTradePagePayData pagePayData=new AlipayTradePagePayData();
			pagePayData.setOut_trade_no(req.getOrderId());
			pagePayData.setSubject(req.getSubject());
			pagePayData.setBody(req.getBody());
			pagePayData.setTotal_amount(req.getTotalAmount());
			pagePayData.setPassback_params(req.getOrderId());
			// 统一下单接口调用
			try {
				AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();// 创建API对应的request
				if(StringUtils.isNoneBlank(req.getReturnUrl())){
					alipayRequest.setReturnUrl(req.getReturnUrl());
				}
				else{
					alipayRequest
					.setReturnUrl(alipayConfig.getAlipayReturnUrl());
				}
				alipayRequest
						.setNotifyUrl(alipayConfig.getAlipayNotifyUrl());// 在公共参数中设置回跳和通知地址
				alipayRequest.setBizContent(JSON.toJSONString(pagePayData));// 填充业务参数
				String form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
				alipay.setParams(form);
				result.setAlipayPrepayInfo(alipay);
			} catch (AlipayApiException e) {
				logger.error("支付宝统一下单接口:{}", e);
				result.set(ResultCode.ERROR_218, "AlipayTradePagePay error:"+e.getErrMsg());
			}
			
		}
		else if(payMethod.equals(PayMethod.PC_SCAN_CODE.getValue())){
			AlipayTradePrecreateData pagePayData=new AlipayTradePrecreateData();
			pagePayData.setOut_trade_no(req.getOrderId());
			pagePayData.setSubject(req.getSubject());
			pagePayData.setBody(req.getBody());
			pagePayData.setTotal_amount(req.getTotalAmount());
			// 统一下单接口调用
			try {
				AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();// 创建API对应的request
				//returnUrl 无效
				if(StringUtils.isNoneBlank(req.getReturnUrl())){
					alipayRequest.setReturnUrl(req.getReturnUrl());
				}
				else{
					alipayRequest
					.setReturnUrl(alipayConfig.getAlipayReturnUrl());
				}
				alipayRequest
						.setNotifyUrl(alipayConfig.getAlipayNotifyUrl());// 在公共参数中设置回跳和通知地址
				alipayRequest.setBizContent(JSON.toJSONString(pagePayData));// 填充业务参数			
				AlipayTradePrecreateResponse response = alipayClient.execute(alipayRequest); // 调用SDK生成表单
				if(response.getCode().equals("10000")){
					alipay.setParams(response.getQrCode());
					result.setAlipayPrepayInfo(alipay);
				}
				else if(response.getCode().equals("40004")){
					logger.error("扫码支付予下单接口失败:{}", JSON.toJSONString(response));
					result.set(ResultCode.ERROR_218, "AlipayTradePrecreate 失败："+JSON.toJSONString(response));
				}
				else{
					logger.error("扫码支付予下单接口失败:{}", JSON.toJSONString(response));
					result.set(ResultCode.ERROR_218,"AlipayTradePrecreate 失败："+JSON.toJSONString(response));
				}
				
			} catch (AlipayApiException e) {
				logger.error("扫码支付予下单接口:{}", e);
				result.set(ResultCode.ERROR_218, "AlipayTradePrecreate error:"+e.getErrMsg());
			}
		}
		else if(payMethod.equals(PayMethod.WAP.getValue())){//手机网站支付接入
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
			try {
				AlipayTradeWapPayModel bizModel=new AlipayTradeWapPayModel(); 
				bizModel.setOutTradeNo(req.getOrderId());
				bizModel.setSubject(req.getSubject());
				bizModel.setTotalAmount(req.getTotalAmount());
				bizModel.setBody(req.getBody());
				bizModel.setProductCode("QUICK_WAP_WAY");
				alipayRequest.setBizModel(bizModel);
				//returnUrl 无效
				if(StringUtils.isNoneBlank(req.getReturnUrl())){
					alipayRequest.setReturnUrl(req.getReturnUrl());
				}
				else{
					alipayRequest
					.setReturnUrl(alipayConfig.getAlipayReturnUrl());
				}
				alipayRequest
						.setNotifyUrl(alipayConfig.getAlipayNotifyUrl());// 在公共参数中设置回跳和通知地址
				AlipayTradeWapPayResponse response= alipayClient.pageExecute(alipayRequest); //调用SDK生成表单
				alipay.setParams(response.getBody());
				result.setAlipayPrepayInfo(alipay);
		    } catch (AlipayApiException e) {
		    	result.set(ResultCode.ERROR_218, "AlipayTradePrecreate error:"+e.getErrMsg());
		    }
		}
		return result;
	}
	@Override
	public boolean rsaCheck(Map<String, String> paramsMap) {
		
		try {
			return AlipaySignature.rsaCheckV1(paramsMap,this.alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(),alipayConfig.getSignType());
		} catch (AlipayApiException e) {
			logger.error("签名异常:{}",e);
		}
		
		return false;
		
	}
	@Override
	public boolean rsaCheck(String result, String sign) {
		try {
			JSONObject jsonObject= JSONObject.fromObject(result);
			String content=jsonObject.getString("alipay_trade_app_pay_response");
			return AlipaySignature.rsaCheck(content,sign,this.alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(),alipayConfig.getSignType());
		} catch (AlipayApiException e) {
			logger.error("签名异常:{}",e);
		}
		
		return false;
	}
	public static void  main(String args[]) throws UnsupportedEncodingException{
		/*result={"alipay_trade_app_pay_response":{"code":"10000","msg":"Success","app_id":"2017051607253335","auth_app_id":"2017051607253335",
			"charset":"utf-8","timestamp":"2017-07-25 17:46:23","total_amount":"0.01",
			"trade_no":"2017072521001004390235452960","seller_id":"2088021780335478",
			"out_trade_no":"CD795786397434720"},
			"sign":"yvESSKdgc1X8PnBVBIPefyilqZ+8tJYvGCOvIG4bSAWHN5VbHECYb6wz7ojgWqagvKsmUCpVRhB6NnCLroTPI042ucYdRvhVHOY5DhmY2ThvRm5EO53C8L4jFYGR7EMIU5f4V4TEKVdKVVPLVoZpcaoy+qFmGUj1j4rYS45vndWZIHEnXRS2/YGRaR/w+yKje9MezW675hTn8vkVTM2jPttCAvDiMn1OEsEMmmlERerg2t+B7LTHqXDvSbOyA0y7FXoKBR+DArhZRHYOh2F9LEv7NMOq62pJoAhqglyD71OzTI/XEd2hXPysbocJpJhMTIEpIGSV2Bc7ACzVF2btrg==",
			"sign_type":"RSA2"}
		}*/
		/*String payResult="total_amount=1.00&timestamp=2017-05-23+15%3A16%3A39&sign=cDcfqeHC0EQaOUSczFWtQNQsJ%2FJrv0K7DXjp8sTqrrRlmkS6esmXAEAuoRLpbIDsZ0sGLsGgViNZkU46w7y1d1zEWgR4goEO1EGUHiBRXmdjmTFBoxrCtDBh%2FdwmBuy9sj%2BlLlwVvaF28dmfrhTq2RufEEtvukx7HQ8GNsPO8IToiFMUsTh9jaYoXSqTrkCj5jJVLiR4NSrNlsvbuEnoQCT0s5oVl6juodxgooXv7fTqcbs1v52CShWdCX%2BJWB%2FsTAcVG1dia6aU1NkmO8%2BpO%2Fqn2BuvAKg7K2WAqMgkPcKVzDaeBoV2jhBG1X2glLP9IYgQgy4sD%2BXrAEJJSBGkvg%3D%3D&trade_no=2017052321001004480200301876&sign_type=RSA2&auth_app_id=2016072900121147&charset=utf-8&seller_id=2088102169066643&method=alipay.trade.page.pay.return&app_id=2016072900121147&out_trade_no=CD57474871494936292&version=1.0";
		payResult = URLDecoder.decode(payResult, "UTF-8");
		Map<String, String> params = AlipayCore.parseParams(payResult);
		
		AlipayConfig alipayConfig=new AlipayConfig();
		try {
			boolean rs=AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
			System.out.println(rs);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// 临时关闭签名校验
		com.edianniu.pscp.mis.util.alipay.AlipayTradeAppPayResponse alipayTradeAppPayResponse=new com.edianniu.pscp.mis.util.alipay.AlipayTradeAppPayResponse();
		alipayTradeAppPayResponse.setCode("10000");
		alipayTradeAppPayResponse.setMsg("Success");
		alipayTradeAppPayResponse.setApp_id("2017051607253335");
		alipayTradeAppPayResponse.setAuth_app_id("2017051607253335");
		alipayTradeAppPayResponse.setCharset("utf-8");
		alipayTradeAppPayResponse.setOut_trade_no("CD795786397434720");
		alipayTradeAppPayResponse.setSeller_id("2088021780335478");
		alipayTradeAppPayResponse.setTimestamp("2017-07-25 17:46:23");
		alipayTradeAppPayResponse.setTotal_amount("0.01");
		alipayTradeAppPayResponse.setTrade_no("2017072521001004390235452960");
        //Map<String,String> params1=alipayTradeAppPayResponse.getParamsMap();
       //params1.put("sign","yvESSKdgc1X8PnBVBIPefyilqZ+8tJYvGCOvIG4bSAWHN5VbHECYb6wz7ojgWqagvKsmUCpVRhB6NnCLroTPI042ucYdRvhVHOY5DhmY2ThvRm5EO53C8L4jFYGR7EMIU5f4V4TEKVdKVVPLVoZpcaoy+qFmGUj1j4rYS45vndWZIHEnXRS2/YGRaR/w+yKje9MezW675hTn8vkVTM2jPttCAvDiMn1OEsEMmmlERerg2t+B7LTHqXDvSbOyA0y7FXoKBR+DArhZRHYOh2F9LEv7NMOq62pJoAhqglyD71OzTI/XEd2hXPysbocJpJhMTIEpIGSV2Bc7ACzVF2btrg==");
       // params1.put("sign_type", "RSA2");
        /*String sign="yvESSKdgc1X8PnBVBIPefyilqZ+8tJYvGCOvIG4bSAWHN5VbHECYb6wz7ojgWqagvKsmUCpVRhB6NnCLroTPI042ucYdRvhVHOY5DhmY2ThvRm5EO53C8L4jFYGR7EMIU5f4V4TEKVdKVVPLVoZpcaoy+qFmGUj1j4rYS45vndWZIHEnXRS2/YGRaR/w+yKje9MezW675hTn8vkVTM2jPttCAvDiMn1OEsEMmmlERerg2t+B7LTHqXDvSbOyA0y7FXoKBR+DArhZRHYOh2F9LEv7NMOq62pJoAhqglyD71OzTI/XEd2hXPysbocJpJhMTIEpIGSV2Bc7ACzVF2btrg==";
        AlipayConfig alipayConfig=new AlipayConfig();
        try {
        	String s=JSON.toJSONString(alipayTradeAppPayResponse);
			boolean rs=AlipaySignature.rsaCheck(s, sign,alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
			boolean rs1=AlipaySignature.rsaCheck("{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2017051607253335\",\"auth_app_id\":\"2017051607253335\",\"charset\":\"utf-8\",\"timestamp\":\"2017-07-26 18:36:30\",\"total_amount\":\"0.01\",\"trade_no\":\"2017072621001004390237309421\",\"seller_id\":\"2088021780335478\",\"out_trade_no\":\"CD885196445790720\"}", 
					"MxHDsgRKwazY7tsReiCYHVC1RyFwvMpo+z//MreVzIK7bTejp9kVudBtYqgWXv2sVQQwVJVp9H3VQwx2ChRk3j2BMYebkwIQ2L0hMSt5yFPmyaNCpM9pljevD1bMmJOSroorLv8uaJxiuEKUmmjza7B7AAxrBfLgGPhhCy3+HWfqrFvN/25JPCWLiMktbSWqKQUw2KLbHGizfU5vQdQwaeWSrgDShvGd0AsRW4gWbX8sbUxlmDMEp+JD1qtldRf8Dr4iaqu+cjL/2fmV1buIc83abWPyGbQMakf+KT28TpVrDYsApsPPLZpwhoV1sxCOgOibivaskNbvQmt1PDBItg==", alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
			System.out.println(rs);
			System.out.println(rs1);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        String result="{\"alipay_trade_app_pay_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2017051607253335\",\"auth_app_id\":\"2017051607253335\",\"charset\":\"utf-8\",\"timestamp\":\"2017-07-26 18:36:30\",\"total_amount\":\"0.01\",\"trade_no\":\"2017072621001004390237309421\",\"seller_id\":\"2088021780335478\",\"out_trade_no\":\"CD885196445790720\"},\"sign\":\"MxHDsgRKwazY7tsReiCYHVC1RyFwvMpo+z//MreVzIK7bTejp9kVudBtYqgWXv2sVQQwVJVp9H3VQwx2ChRk3j2BMYebkwIQ2L0hMSt5yFPmyaNCpM9pljevD1bMmJOSroorLv8uaJxiuEKUmmjza7B7AAxrBfLgGPhhCy3+HWfqrFvN/25JPCWLiMktbSWqKQUw2KLbHGizfU5vQdQwaeWSrgDShvGd0AsRW4gWbX8sbUxlmDMEp+JD1qtldRf8Dr4iaqu+cjL/2fmV1buIc83abWPyGbQMakf+KT28TpVrDYsApsPPLZpwhoV1sxCOgOibivaskNbvQmt1PDBItg==\",\"sign_type\":\"RSA2\"}";
        //JSONObject jsonObject=new JSONObject(true);
        JSONObject jsonObject= JSONObject.fromObject(result);
        
        
        //jsonObject.parseObject(text, clazz)
        //AlipayResult alipayResult=jsonObject.parseObject(result,AlipayResult.class,Feature.AllowArbitraryCommas);
        //LinkedHashMap<String,String> params=JSON.parseObject(result,new TypeReference<LinkedHashMap<String,String>>(){});
        System.out.println(jsonObject.getString("alipay_trade_app_pay_response"));
		
	}
	

}
