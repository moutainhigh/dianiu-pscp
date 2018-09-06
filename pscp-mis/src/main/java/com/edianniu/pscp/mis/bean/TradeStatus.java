package com.edianniu.pscp.mis.bean;

/**
 * 支付接口交易状态
 *
 * @author AbnerElk
 */
public enum TradeStatus {
	/**
	 * ali交易创建，等待买家付款。
	 */
	WAIT_BUYER_PAY,
	/**
	 * ali在指定时间段内未支付时关闭的交易;在交易完成全额退款成功时关闭的交易。
	 * wx业务结果FAIL
	 */
	TRADE_CLOSED,
	/**
	 * ali交易成功，且可对该交易做操作，如：多级分润、退款等。
	 */
	TRADE_SUCCESS,
	/**
	 * ali交易成功且结束，即不可再做任何操作。
	 * wx业务结果SUCCESS
	 */
	TRADE_FINISHED,
	/**
	 * unionpay交易已受理，正在支付中。
	 * 业务结果PAYING
	 */
	TRADE_PAYING,
	/**
	 * unionpay交易失败。
	 * 业务结果FALSE
	 */
	TRADE_FASLE
	
}
