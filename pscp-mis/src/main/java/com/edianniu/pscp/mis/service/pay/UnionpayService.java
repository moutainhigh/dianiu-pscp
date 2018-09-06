package com.edianniu.pscp.mis.service.pay;
import com.edianniu.pscp.mis.bean.pay.UnionpayPrepayReqData;
import com.edianniu.pscp.mis.bean.pay.UnionpayPrepayRespData;
import com.edianniu.pscp.mis.bean.pay.UnionpayQueryReqData;
import com.edianniu.pscp.mis.bean.pay.UnionpayQueryRespData;
import com.edianniu.pscp.mis.bean.pay.UnionpaydfData;
import com.edianniu.pscp.mis.bean.pay.UnionpaydfPayRespData;

public interface UnionpayService {
		
	public  UnionpayPrepayRespData prepay(UnionpayPrepayReqData data);
	
	public  UnionpaydfPayRespData unionpaydf(UnionpaydfData data);
	
	public UnionpayQueryRespData unionapyQuery(UnionpayQueryReqData data);
	
}
