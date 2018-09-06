/**
 *
 */
package com.edianniu.pscp.renter.mis.commons;

/**
 * @author cyl
 *
 */
public class CacheKey {
	public static final String SPLIT = "#";
	public static final String CACHE_KEY_USER_LOGININFO = "operate#user#logininfo#";
	public static final String CACHE_KEY_RESET_PWD_EMAIL_CHECK_CODE = "reset#pwd#email#check#code#";
	public static final String CACHE_KEY_MSG_CODE = "msg#code#";
	public static final String CACHE_KEY_QA_CHECK_CODE = "qa#check#code#";
	public static final String CACHE_KEY_SMS_USER_GET_MSG_CODE="sms#user#getMsgCode#";
	public static final String CACHE_KEY_SOCIAL_WORK_ORDER_PAY_REMIND="social#work#order#pay#remind#";
	public static final String CACHE_KEY_NEEDSORDER_ORDERID = "needsorder#orderid#";
	public static final String CACHE_KEY_NEEDS_ORDERID = "needs#orderid#";
	public static final String CACHE_KEY_USER_UID = "user#uid#";
	public static final String CACHE_KEY_ROOM_ID = "room#id#";
	public static final String CACHE_KEY_PROJECT_ID = "project#id#";
	public static final String CACHE_KEY_RENTER_USER_MOBILE = "renter#user#mobile#";
	public static final String CACHE_KEY_RENTER_ID = "renter#id#";
	public static final String CACHE_KEY_SWITCH_RENTER_ID = "switch#renter_id#";
	
	/** 短信内容 */
	public static final String SMS_CONTEXT_HASH_KEY = "sms" + SPLIT + "context";
	
	
}
