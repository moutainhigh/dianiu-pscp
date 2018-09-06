/**
 *
 */
package com.edianniu.pscp.mis.commons;

/**
 * @author cyl
 */
public class CacheKey {
    public static final String SPLIT = "#";
    public static final String CACHE_KEY_USER_LOGININFO = "operate#user#logininfo#";
    public static final String CACHE_KEY_RESET_PWD_EMAIL_CHECK_CODE = "reset#pwd#email#check#code#";
    public static final String CACHE_KEY_MSG_CODE = "msg#code#";
    public static final String CACHE_KEY_QA_CHECK_CODE = "qa#check#code#";
    public static final String CACHE_KEY_SMS_USER_GET_MSG_CODE = "sms#user#getMsgCode#";
    public static final String CACHE_KEY_AREA_PROVINCE_LIST = "area#province#list#";
    public static final String CACHE_KEY_AREA_CITY_LIST = "area#city#list#";
    public static final String CACHE_KEY_AREA_AREA_LIST = "area#area#list#";
    public static final String CACHE_KEY_WITH_DRAWALS_LOCK = "with#drawals#uid#";
    public static final String CACHE_KEY_BANK_CARD_LOCK = "bank#card#uid#";
    public static final String CACHE_KEY_COMPANY_INVITATION = "company#invitation#";
    public static final String CACHE_KEY_CONFIRM_COMPANY_INVITATION = "confirm#company#invitation#";
    public static final String CACHE_KEY_ELECTRICIAN_INVITATION = "electrician#invitation#";
    public static final String CACHE_KEY_AGREE_ELECTRICIAN_INVITATION = "agree#electrician#invitation#";
    public static final String CACHE_KEY_REJECT_ELECTRICIAN_INVITATION = "reject#electrician#invitation#";
    public static final String CACHE_KEY_CONFIRM_ELECTRICIAN_UNBIND = "confirm#electrician#unbind#";
    public static final String CACHE_KEY_APPLY_ELECTRICIAN_UNBIND = "apply#electrician#unbind#";
    public static final String CACHE_KEY_REFUND_NEEDS_ORDER_DEPOSIT = "refund#needsorderdeposit#";
    public static final String CACHE_KEY_REFUND_SOCIAL_WORK_ORDER_DEPOSIT = "refund#socialworkorderdeposit#";
    public static final String CACHE_KEY_SETTLEMENT_PREPAY_RENTER_CHARGE_ORDER = "settlement#prepay#renter#chargeorder#";
    public static final String CACHE_KEY_INVOIC_TITLE_ID = "invoice#title#id#";
    public static final String CACHE_KEY_INVOIC_INFO_CONFIRM_ID = "invoice#info#confirm#orderid#";
    public static final String CACHE_KEY_INVOIC_INFO_APPLY_ID = "invoice#info#apply#id#";
    public static final String CACHE_KEY_NETDAU_CONTROL_SWITCH = "netdau#control#switch#";


    /**
     * 短信内容
     */
    public static final String SMS_CONTEXT_HASH_KEY = "sms" + SPLIT + "context";
}
