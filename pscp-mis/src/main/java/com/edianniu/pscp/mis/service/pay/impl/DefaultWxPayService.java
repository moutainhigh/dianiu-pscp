/**
 *
 */
package com.edianniu.pscp.mis.service.pay.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.pay.WxPayRefundInfo;
import com.edianniu.pscp.mis.bean.pay.WxPayRefundQueryReqData;
import com.edianniu.pscp.mis.bean.pay.WxPayRefundQueryRespData;
import com.edianniu.pscp.mis.bean.pay.WxPayRefundReqData;
import com.edianniu.pscp.mis.bean.pay.WxPayRefundRespData;
import com.edianniu.pscp.mis.bean.pay.WxpayPrepayInfo;
import com.edianniu.pscp.mis.bean.pay.WxpayPrepayReqData;
import com.edianniu.pscp.mis.bean.pay.WxpayPrepayRespData;
import com.edianniu.pscp.mis.service.pay.WxPayService;
import com.edianniu.pscp.mis.util.wxpay.Signature;
import com.edianniu.pscp.mis.util.wxpay.WxPayConfig;
import com.edianniu.pscp.mis.util.wxpay.WxPayUtil;

/**
 * @author cyl
 */
@Service
@Repository("wxPayService")
public class DefaultWxPayService implements WxPayService {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultWxPayService.class);
	@Autowired
    @Qualifier("wxPayConfig")
	private WxPayConfig wxPayConfig;
	@Override
	public WxPayRefundRespData refund(WxPayRefundReqData reqData) {
		WxPayRefundRespData respData = new WxPayRefundRespData();
		try {
			String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("appid", reqData.getAppId()));
			params.add(new BasicNameValuePair("mch_id", reqData.getMchId()));
			params.add(new BasicNameValuePair("nonce_str", WxPayUtil
					.genNonceStr()));
			/*params.add(new BasicNameValuePair("op_user_id", reqData
					.getOpUserId()));*/
			params.add(new BasicNameValuePair("out_refund_no", reqData
					.getOutRefundNo()));
			params.add(new BasicNameValuePair("out_trade_no", reqData
					.getOutTradeNo()));
			params.add(new BasicNameValuePair("refund_desc", reqData.getRefundReason()));
			params.add(new BasicNameValuePair("refund_fee", reqData
					.getRefundFee()));
			params.add(new BasicNameValuePair("refund_fee_type", reqData
					.getRefundFeeType()));
			params.add(new BasicNameValuePair("total_fee", reqData
					.getTotalFee()));
			params.add(new BasicNameValuePair("transaction_id", reqData.getTradeNo()));
			String apiKey=wxPayConfig.getConfigByAppId(reqData.getAppId()).getApiKey();
			String sign = WxPayUtil.genPackageSign(params,apiKey);
			params.add(new BasicNameValuePair("sign", sign));
			Map<String, String> xmlResult = WxPayUtil.sendRequest(url, params,
					reqData.getMchId(),wxPayConfig.getWxpayCertDir());

			respData.setAppid(xmlResult.get("appid"));
			respData.setCash_fee(xmlResult.get("cash_fee"));
			respData.setDevice_info(xmlResult.get("device_info"));
			respData.setErr_code(xmlResult.get("err_code"));
			respData.setErr_code_des(xmlResult.get("err_code_des"));
			respData.setFee_type(xmlResult.get("fee_type"));
			respData.setMch_id(xmlResult.get("mch_id"));
			respData.setNonce_str(xmlResult.get("nonce_str"));
			respData.setOut_refund_no(xmlResult.get("out_refund_no"));
			respData.setOut_trade_no(xmlResult.get("out_trade_no"));
			respData.setRefund_channel(xmlResult.get("refund_channel"));
			respData.setRefund_fee(xmlResult.get("refund_fee"));
			respData.setRefund_id(xmlResult.get("refund_id"));
			respData.setResult_code(xmlResult.get("result_code"));
			respData.setReturn_msg(xmlResult.get("return_msg"));
			respData.setReturn_code(xmlResult.get("return_code"));
			respData.setSign(xmlResult.get("sign"));
			respData.setTotal_fee(xmlResult.get("total_fee"));
			respData.setTransaction_id(xmlResult.get("transaction_id"));
		} catch (Exception e) {
			logger.error("refund:{}", e);
		}
		if (StringUtils.isNoneBlank(respData.getReturn_code())
				&& StringUtils.isNoneBlank(respData.getResult_code())) {
			if (respData.getReturn_code().equals("SUCCESS")
					&& respData.getResult_code().equals("SUCCESS")) {// 提交退款申请成功
				respData.setSuccess(true);
			}
		}
		return respData;
	}

	@Override
	public WxPayRefundQueryRespData refundQuery(WxPayRefundQueryReqData reqData) {
		try {
			String url = "https://api.mch.weixin.qq.com/pay/refundquery";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 公众账号ID
			params.add(new BasicNameValuePair("appid", reqData.getAppId()));
			// 商户号
			params.add(new BasicNameValuePair("mch_id", reqData.getMchId()));
			// 设备号
			// params.add(new
			// BasicNameValuePair("device_info",reqData.getDevice_info()));
			// 随机字符串
			params.add(new BasicNameValuePair("nonce_str", WxPayUtil
					.genNonceStr()));
			// 商户退款单号
			params.add(new BasicNameValuePair("out_refund_no", reqData.getOutRefundNo()));
			// 商户订单号
			params.add(new BasicNameValuePair("out_trade_no",reqData.getOutTradeNo()));
			// 微信退款单号
			if(StringUtils.isNoneBlank(reqData.getRefundNo())){
				params.add(new BasicNameValuePair("refund_id",reqData.getRefundNo()));	
			}
			// 微信订单号
			params.add(new BasicNameValuePair("transaction_id",reqData.getTradeNo()));
			// 签名
			logger.info("refund 签名");
			logger.info("refund 签名 appId:{}",reqData.getAppId());
			String apiKey=wxPayConfig.getConfigByAppId(reqData.getAppId()).getApiKey();
			logger.info("refund 签名 apiKe:{}",apiKey);
			String sign = WxPayUtil.genPackageSign(params, apiKey);
			logger.info("refund 签名 sign:{}",sign);
			params.add(new BasicNameValuePair("sign", sign));
			Map<String, String> result = WxPayUtil.sendRequest(url, params);
			// 处理返回结果
			WxPayRefundQueryRespData respData = new WxPayRefundQueryRespData();
			BeanUtils.populate(respData, result);
			// 处理$n(ps 封装成对象 ) ;
			List<WxPayRefundInfo> refundInfoList = new ArrayList<WxPayRefundInfo>();
			for (int i = 0; i < respData.getRefund_count(); i++) {
				WxPayRefundInfo refundInfo = new WxPayRefundInfo();

				refundInfo.setOut_refund_no(result.get("out_refund_no_" + i));
				refundInfo.setRefund_id(result.get("refund_id_" + i));
				refundInfo.setRefund_channel(result.get("refund_channel_" + i));
				refundInfo.setRefund_fee(NumberUtils.toInt(result
						.get("refund_fee_" + i)));
				refundInfo.setCoupon_refund_count(NumberUtils.toInt(result
						.get("coupon_refund_count_" + i)));
				refundInfo.setCoupon_refund_batch_id(result
						.get("coupon_refund_batch_id_" + i));
				refundInfo.setCoupon_refund_id(result.get("coupon_refund_id_"
						+ i));
				refundInfo.setCoupon_refund_fee(NumberUtils.toInt(result
						.get("coupon_refund_fee_" + i)));
				refundInfo.setRefund_status(result.get("refund_status_" + i));
				refundInfo.setRefund_recv_accout(result
						.get("refund_recv_accout_" + i));
				refundInfoList.add(refundInfo);
			}
			respData.setRefundInfos(refundInfoList);

			return respData;
		} catch (Exception e) {
			logger.error("refund:{}", e);
		}
		return null;
	}
	@Override
	public WxpayPrepayRespData prepay(WxpayPrepayReqData reqData) {
		WxpayPrepayRespData respData = new WxpayPrepayRespData();
		try {
			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("appid", wxPayConfig.getAppId(reqData.getTrade_type(),reqData.getAppType())));
			params.add(new BasicNameValuePair("attach", reqData.getAttach()));
			params.add(new BasicNameValuePair("body", reqData.getBody()));
			params.add(new BasicNameValuePair("detail", reqData.getDetail()));
			if(StringUtils.isNoneBlank(reqData.getDeviceInfo())){//PC网页或公众号内支付请传"WEB"
				params.add(new BasicNameValuePair("device_info", reqData.getDeviceInfo()));
			}
			params.add(new BasicNameValuePair("mch_id", wxPayConfig.getMchId(reqData.getTrade_type(),reqData.getAppType())));
			params.add(new BasicNameValuePair("nonce_str", reqData
					.getNonce_str()));
			params.add(new BasicNameValuePair("notify_url", wxPayConfig.getWxpayNotifyUrl()));
			if (StringUtils.isNoneBlank(reqData.getOpenid())) {
				params.add(new BasicNameValuePair("openid", reqData.getOpenid()));
			}
			params.add(new BasicNameValuePair("out_trade_no", reqData
					.getOut_trade_no()));
			if(StringUtils.isNoneBlank(reqData.getSceneInfo())){//3，WAP网站应用
				//{"h5_info": //h5支付固定传"h5_info" 
				//   {"type": "Wap",  //场景类型
				//    "wap_url": "",//WAP网站URL地址
				//    "wap_name": ""  //WAP 网站名
				//    }
				//}
				params.add(new BasicNameValuePair("scene_info", reqData.getSceneInfo()));
			}
			params.add(new BasicNameValuePair("spbill_create_ip", reqData
					.getSpbill_create_ip()));
			
			
			params.add(new BasicNameValuePair("total_fee", reqData
					.getTotal_fee()));
			params.add(new BasicNameValuePair("trade_type", reqData
					.getTrade_type()));
			String sign = WxPayUtil.genPackageSign(params, wxPayConfig.getApiKey(reqData.getTrade_type(),reqData.getAppType()));
			params.add(new BasicNameValuePair("sign", sign));
			Map<String, String> result = WxPayUtil.sendRequest(url, params);
			if(result==null){
				respData.setSuccess(false);
				return respData;
			}
			respData.setErrCode(result.get("err_code"));
			respData.setErrCodeDes(result.get("err_code_des"));
			respData.setPrepayid(result.get("prepay_id"));
			respData.setCodeUrl(result.get("code_url"));
			respData.setMwebUrl(result.get("mweb_url"));
			respData.setResultCode(result.get("result_code"));
			respData.setReturnMsg(result.get("return_msg"));
			respData.setReturnCode(result.get("return_code"));
			logger.info("prepay resp:{}", JSON.toJSONString(respData));
			if (StringUtils.isBlank(respData.getReturnCode())) {
				respData.setSuccess(false);
				return respData;
			}
			if (!respData.getReturnCode().equals("SUCCESS")) {
				respData.setSuccess(false);
				return respData;
			}
			if (StringUtils.isBlank(respData.getResultCode())) {
				respData.setSuccess(false);
				return respData;
			}
			if (!respData.getResultCode().equals("SUCCESS")) {
				respData.setSuccess(false);
				return respData;
			}
			// 校验签名
			if (StringUtils.isBlank(respData.getPrepayid())) {
				respData.setSuccess(false);
				return respData;
			}
			WxpayPrepayInfo weixinpay = new WxpayPrepayInfo();
			weixinpay.setNoncestr(WxPayUtil.genNonceStr());
			weixinpay.setTimestamp(WxPayUtil.getTimeStamp());
			weixinpay.setPrepayid(respData.getPrepayid());
			weixinpay.setCodeUrl(respData.getCodeUrl());
			if(StringUtils.isNoneBlank(reqData.getReturnUrl())){
				weixinpay.setMwebUrl(respData.getMwebUrl()+"&redirect_url="+URLEncoder.encode(reqData.getReturnUrl()));
			}
			else{
				weixinpay.setMwebUrl(respData.getMwebUrl());
			}
			
			weixinpay.setAppid(wxPayConfig.getAppId(reqData.getTrade_type(),reqData.getAppType()));
			weixinpay.setPartnerid(wxPayConfig.getMchId(reqData.getTrade_type(),reqData.getAppType()));
			List<NameValuePair> signParams = new LinkedList<NameValuePair>();
			signParams
					.add(new BasicNameValuePair("appid", weixinpay.getAppid()));
			signParams.add(new BasicNameValuePair("noncestr", weixinpay
					.getNoncestr()));
			signParams
					.add(new BasicNameValuePair("package", weixinpay.getPkg()));
			signParams.add(new BasicNameValuePair("partnerid", weixinpay
					.getPartnerid()));
			signParams.add(new BasicNameValuePair("prepayid", weixinpay
					.getPrepayid()));
			signParams.add(new BasicNameValuePair("timestamp", weixinpay
					.getTimestamp()));
			weixinpay.setSign(WxPayUtil.genAppSign(signParams,
					wxPayConfig.getApiKey(reqData.getTrade_type(),reqData.getAppType())));
			respData.setWeixinpay(weixinpay);
			respData.setSuccess(true);
		} catch (ClientProtocolException e) {
			logger.error("微信统一下单接口:{}", e);
			respData.setSuccess(false);

		} catch (IOException e) {
			logger.error("微信统一下单接口:{}", e);
			respData.setSuccess(false);
		}
		catch (Exception e) {
			logger.error("微信统一下单接口:{}", e);
			respData.setSuccess(false);
		}
		return respData;
	}

	@Override
	public boolean getSignVeryfy(List<NameValuePair> params, String sign,
			String payMethod,String payChannel) {
		return WxPayUtil.getSignVeryfy(params, sign, wxPayConfig.getApiKey(payMethod,payChannel));
	}

	@Override
	public boolean checkIsSignValidFromResponseString(String responseString,
			String payMethod,String appId) {
		try {
			String payChannel=wxPayConfig.getPayChannel(appId);
			return Signature.checkIsSignValidFromResponseString(responseString, wxPayConfig.getApiKey(payMethod,payChannel));
		} catch (ParserConfigurationException e) {
			logger.error("checkIsSignValidFromResponseString:{}", e);
		} catch (IOException e) {
			logger.error("checkIsSignValidFromResponseString:{}", e);
		} catch (SAXException e) {
			logger.error("checkIsSignValidFromResponseString:{}", e);
		}
		return false;
		
	}
	/*private String getSandBoxKey(WxpayPrepayReqData reqData) {
	//
	CloseableHttpClient httpClient = HttpClients.createDefault();
	try {

		HttpPost post = new HttpPost(
				"https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mch_id", wxPayConfig.getAppId(reqData.getTrade_type(),reqData.getAppType())));
		String sign = WxPayUtil.genPackageSign(params, wxPayConfig.getApiKey(reqData.getTrade_type(),reqData.getAppType()));
		params.add(new BasicNameValuePair("sign", sign));
		String xmlstring = WxPayUtil.toXml(params);
		StringEntity stringEntity = new StringEntity(xmlstring,
				ContentType.create("text/plain", Charset.forName("utf-8")));
		post.setEntity(stringEntity);
		post.addHeader("Connection", "Keep-Alive");
		
		 * post.addHeader("Content-Type",
		 * "application/x-www-form-urlencoded;charset=utf-8");
		 
		post.addHeader("Accept", "application/json");
		post.addHeader("Content-type", "application/json");
		// post.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
		// false);
		CloseableHttpResponse res = httpClient.execute(post);
		InputStream input = res.getEntity().getContent();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int len = 0;
		byte[] by = new byte[1024];
		while ((len = input.read(by)) != -1) {
			output.write(by, 0, len);
		}
		Header[] headers = res.getAllHeaders();
		int statusCode = res.getStatusLine().getStatusCode();

		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String content = new String(output.toByteArray());
		Map<String, String> xmlResult = WxPayUtil.decodeXml(content);
		return xmlResult.get("sandbox_signkey");
		

	} catch (ClientProtocolException e) {
		logger.error("getSandBoxKey:{}", e);
		
	} catch (IOException e) {
		logger.error("getSandBoxKey:{}", e);
	}
	return "";
}*/

}
