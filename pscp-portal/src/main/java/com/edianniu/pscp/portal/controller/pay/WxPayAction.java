package com.edianniu.pscp.portal.controller.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edianniu.pscp.mis.bean.pay.WxpayNotifyReq;
import com.edianniu.pscp.mis.bean.pay.WxpayNotifyResult;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 微信支付
 *
 * @author AbnerElk
 */
@Controller
@RequestMapping("/pay")
public class WxPayAction {
    private static final Logger logger = LoggerFactory.getLogger(WxPayAction.class);

    @Autowired
    @Qualifier("payInfoService")
    private PayInfoService payInfoService;

    /**
     * 微信支付结果push
     * 异步回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "wxpay_notify", method = RequestMethod.POST)
    public void payPushNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletInputStream in = request.getInputStream();
            // 转换微信post过来的xml内容
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1; ) {
                out.append(new String(b, 0, n, "UTF-8"));
            }
            String pushXml = out.toString();
            logger.info(String.format("WxPay push notify [wxpush - param] : %s", pushXml));

            /*
             * 生成请求对象
             */
            WxpayNotifyReq req = new WxpayNotifyReq();
            req.setPushXml(pushXml);

            // 调用dubbo接口
            WxpayNotifyResult result = payInfoService.wxpayNotify(req);
            logger.info(String.format("WxPay push notify [wxpush - dubbo return] : %s", result));

            StringBuffer sb = new StringBuffer("<xml>");
            sb.append("<return_code><![CDATA[");
            sb.append(result.getResultCode() == 200 ? "SUCCESS" : "FAIL");
            sb.append("]]></return_code>");
            sb.append("<return_msg><![CDATA[");
            sb.append(result.getResultMessage());
            sb.append("]]></return_msg>");
            sb.append("</xml>");

            String msg = sb.toString();

            logger.info(String.format("WxPay push notify [wxpush - return] : %s", msg));
            // 打印结果, 反馈给微信
            response.getWriter().print(msg);
        } catch (Exception e) {
            logger.error("WxPayPushNotify:{}", e);
            try {
                // 打印结果, 反馈给微信
                response.getWriter().print(String.format("error : %s", e.getMessage()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
