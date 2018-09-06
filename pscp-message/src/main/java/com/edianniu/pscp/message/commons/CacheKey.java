/**
 *
 */
package com.edianniu.pscp.message.commons;

/**
 * @author cyl
 *
 */
public class CacheKey {
	public static final String SPLIT = "#";
	public static final String CACHE_KEY_USER_LOGININFO = "user#logininfo#";
	public static final String CACHE_KEY_RESET_PWD_EMAIL_CHECK_CODE = "reset#pwd#email#check#code#";
	public static final String CACHE_KEY_MSG_CODE = "msg#code#";
	public static final String CACHE_KEY_SMS_USER_GET_MSG_CODE="sms#user#getMsgCode#";
	public static final String CACHE_KEY_USER_CANCLE_ORDER_DAILY_NUM="user#cancle#order#daily#num#";
	public static final String CACHE_KEY_ORDER_LOCK="order#";//订单锁
	public static final String CACHE_KEY_WITH_DRAWALS_LOCK="with#drawals#uid#";//提现操作锁
	public static final String CACHE_KEY_LATEST_DYNAMIC_MESSAGE_LIST="latest#dynamic#msg#list";
	public static final int LATEST_DYNAMIC_MESSAGE_LIST_MAX_COUNT=50;

	/** 正在执行的task的前缀 */
    public final static String TADK_EXECUTING_PREFIX = "task" + SPLIT + "doing" + SPLIT;
	/** 微信退款拉取队列 */
	public static final String WXPAY_REFOUND_LIST = "wxpay" + SPLIT + "refund" + SPLIT + "list";
	/** 微信退款 上一次拉取时间 */
	public static final String WXPAY_REFUND_LAST_TIME_PREFIX = "wxpay" + SPLIT + "refund" + SPLIT + "last" + SPLIT + "time" + SPLIT;
	/** 短信内容 */
	public static final String SMS_CONTEXT_HASH_KEY = "pscp"+SPLIT+"sms" + SPLIT + "context";
}
