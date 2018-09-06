package com.edianniu.pscp.portal.controller.pay;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.pay.AlipayNotifyReq;
import com.edianniu.pscp.mis.bean.pay.AlipayNotifyResult;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝支付
 */
@Controller
@RequestMapping("/pay")
public class AliPayAction {
	private static final Logger logger = LoggerFactory.getLogger(AliPayAction.class);

	@Autowired
	@Qualifier("payInfoService")
	private PayInfoService payInfoService;

	/**
	 * 支付宝支付结果push
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "alipay_notify", method = RequestMethod.POST)
	public void payPushNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String[]> requestParams = request.getParameterMap();
			logger.info(String.format("AliPay push notify [alipush - param] : %s", requestParams));

            /*
			 * 获取推送参数
             */
			//获取支付宝POST过来反馈信息
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
			logger.info(String.format("AliPay push notify [alipush - json] : %s", JSON.toJSONString(params)));

			AlipayNotifyReq req = new AlipayNotifyReq();
			req.setAliPush(params);

			logger.info(String.format("AliPay push notify [callmis - param] : %s", req));
			logger.info(String.format("AliPay push notify [callmis - json] : %s", JSON.toJSONString(req)));

			// 调用dubbo接口
			AlipayNotifyResult result = payInfoService.alipayNotify(req);

			logger.info(String.format("AliPay push notify [alipush - dubbo return] : %s", JSON.toJSONString(result)));

			String msg;
			// 判断返回结果
			if (result.getResultCode() == 200) {
				msg = result.getResultMessage();
			} else {
				// 失败时, 拼接返回结果及状态码
				msg = String.format("%s [%s]", result.getResultMessage(), result.getResultCode());
			}
			logger.info(String.format("AliPay push notify [alipush - return] : %s", msg));
			// 打印结果, 反馈给微信
			response.getWriter().print(msg);
		} catch (Exception e) {
			logger.error("AliPayPushNotify:{}", e);
			try {
				// 打印结果, 反馈给支付宝
				response.getWriter().print(String.format("error : %s", e.getMessage()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
