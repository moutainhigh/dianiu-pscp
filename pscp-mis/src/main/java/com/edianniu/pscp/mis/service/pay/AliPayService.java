/**
 * 
 */
package com.edianniu.pscp.mis.service.pay;

import java.util.Map;

import com.edianniu.pscp.mis.bean.pay.AliPayPrepayReq;
import com.edianniu.pscp.mis.bean.pay.AliPayPrepayResult;
import com.edianniu.pscp.mis.bean.pay.AliPayRefundQueryReqData;
import com.edianniu.pscp.mis.bean.pay.AliPayRefundQueryRespData;
import com.edianniu.pscp.mis.bean.pay.AliPayRefundReqData;
import com.edianniu.pscp.mis.bean.pay.AliPayRefundRespData;

/**
 * @author cyl
 *
 */
public interface AliPayService {
	/**
	 * 退款申请接口
	 * @param reqData
	 * @return
	 */
     public AliPayRefundRespData refund(AliPayRefundReqData reqData);
     /**
      * 统一下单接口
      * @param reqData
      * @return
      */
     public AliPayPrepayResult prepay(AliPayPrepayReq reqData);
     /**
      * 退款查询接口
      * @param reqData
      * @return
      */
     public AliPayRefundQueryRespData refundQuery(AliPayRefundQueryReqData reqData);
     
     public boolean rsaCheck(Map<String,String> paramsMap);
     
     public boolean rsaCheck(String content,String sign);
     
}
