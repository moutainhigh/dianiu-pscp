package com.edianniu.pscp.mis.dao;

import java.util.List;

import com.edianniu.pscp.mis.domain.AliPushLog;
import com.edianniu.pscp.mis.domain.PayPushError;
import com.edianniu.pscp.mis.domain.UnionpayPushLog;
import com.edianniu.pscp.mis.domain.WxPushLog;

/**
 * @author AbnerELk
 */
public interface PayLogDao {
	void saveAliLog(AliPushLog pushLog);

	void saveWxLog(WxPushLog pushLog);
	
	public AliPushLog getAliPushLogByOrderId(String orderId);
	
	public WxPushLog getWxPushLogByOrderId(String orderId);

	void insertPayPushError(PayPushError error);
	
	void saveUniondfLog(UnionpayPushLog unionLog);
	
	void saveUnionLog(UnionpayPushLog unionLog);
	
	public UnionpayPushLog getUnionpayPushLog(Long id);
	
	void updateUnionpayPushLog(UnionpayPushLog unionLog);
	
	public List<UnionpayPushLog> getUnionpayPushLogs(UnionpayPushLog unionLog);
	
	public List<UnionpayPushLog> getUnionpayPushLogByOrderIds(List<String>list);
	
	public UnionpayPushLog getUnionpayPushLogByOrderId(String orderId);
}
