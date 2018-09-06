/**
 * 
 */
package com.edianniu.pscp.mis.service.pay;

import java.util.List;

import org.apache.http.NameValuePair;

import com.edianniu.pscp.mis.bean.pay.WxPayRefundQueryReqData;
import com.edianniu.pscp.mis.bean.pay.WxPayRefundQueryRespData;
import com.edianniu.pscp.mis.bean.pay.WxPayRefundReqData;
import com.edianniu.pscp.mis.bean.pay.WxPayRefundRespData;
import com.edianniu.pscp.mis.bean.pay.WxpayPrepayReqData;
import com.edianniu.pscp.mis.bean.pay.WxpayPrepayRespData;

/**
 * @author cyl
 *
 */
public interface WxPayService {
	/**
	 * 退款申请接口
	 * @param reqData
	 * @return
	 */
     public WxPayRefundRespData refund(WxPayRefundReqData reqData);
     /**
      * 统一下单接口
      * @param reqData
      * @return
      */
     public WxpayPrepayRespData prepay(WxpayPrepayReqData reqData);
     /**
      * 查询退款状态接口
      * @param reqData
      * @return
      */
     public WxPayRefundQueryRespData refundQuery(WxPayRefundQueryReqData reqData);
     
     public boolean getSignVeryfy(List<NameValuePair> params, String sign,String payMethod,String payChannel);
     public boolean checkIsSignValidFromResponseString(String responseString,String payMethod,String appId);
     
}
