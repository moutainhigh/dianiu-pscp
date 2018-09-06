package com.edianniu.pscp.renter.mis.util;

public class ConstantUtils {
	public static String SKY_CACHE_USERAUTH_PROFIX = "a_";
	public static String SKY_CACHE_USERONLINE_PROFIX = "o_";
	public static String SKY_CACHE_PWDERRORCOUNT_PROFIX = "pec_";
	public static String SKY_CACHE_AUTHCODE = "code_";
	public static String SKY_CACHE_RESETPWDLIMIT = "rpl_";
	public static String SKY_CACHE_MODIFYSECRETQLIMIT = "msl_";
	public static String SKY_CACHE_IMSI_PROFIX = "imsi_";
	public static int ONLINEINFO_EXPIRE;
	public static int PASSWORDERRORCOUNT_EXPIRE;
	public static int REGISTER_IMSI_EXPIRE;
	public static int AUTHCODE_EXPIRE;
	public static int RESETPWDLIMIT_HOUR;
	public static int RESETPWDLIMIT_NUMBER;
	public static int MODIFYSECRETQLIMIT_HOUR;
	public static int MODIFYSECRETQLIMIT_NUMBER;
	public static int LOGIN_MAXERRORCOUNT1;
	public static int LOGIN_MAXERRORCOUNT2;
	public static int AUTHCODE_WIDTH;
	public static int AUTHCODE_HEIGHT;
	public static int REGISTER_IMSI_COUNT;

	static {
		try {
			ONLINEINFO_EXPIRE = Integer.parseInt(ConfigFileReader.p
					.getProperty("biz_mao_onlineinfo_expire"));
		} catch (Exception e) {
			ONLINEINFO_EXPIRE = 86400;
		}
		try {
			PASSWORDERRORCOUNT_EXPIRE = Integer.parseInt(ConfigFileReader.p
					.getProperty("biz_mao_passworderrorcount_expire"));
		} catch (Exception e) {
			PASSWORDERRORCOUNT_EXPIRE = 86400;
		}
		try {
			AUTHCODE_EXPIRE = Integer.parseInt(ConfigFileReader.p
					.getProperty("biz_mao_authcode_expire"));
		} catch (Exception e) {
			AUTHCODE_EXPIRE = 1200;
		}
		try {
			REGISTER_IMSI_EXPIRE = Integer.parseInt(ConfigFileReader.p
					.getProperty("biz_mao_register_imsi_expire"));
		} catch (Exception e) {
			REGISTER_IMSI_EXPIRE = 86400;
		}
		try {
			LOGIN_MAXERRORCOUNT1 = Integer.parseInt(ConfigFileReader.p
					.getProperty("login_maxerrorCount1"));
		} catch (Exception e) {
			LOGIN_MAXERRORCOUNT1 = 5;
		}
		try {
			LOGIN_MAXERRORCOUNT2 = Integer.parseInt(ConfigFileReader.p
					.getProperty("login_maxerrorCount2"));
		} catch (Exception e) {
			LOGIN_MAXERRORCOUNT2 = 5;
		}
		try {
			RESETPWDLIMIT_HOUR = Integer.parseInt(ConfigFileReader.p
					.getProperty("resetpwdlimit_hour"));
		} catch (Exception e) {
			RESETPWDLIMIT_HOUR = 1;
		}
		try {
			RESETPWDLIMIT_NUMBER = Integer.parseInt(ConfigFileReader.p
					.getProperty("resetpwdlimit_number"));
		} catch (Exception e) {
			RESETPWDLIMIT_NUMBER = 5;
		}
		try {
			MODIFYSECRETQLIMIT_HOUR = Integer.parseInt(ConfigFileReader.p
					.getProperty("modifysecretqlimit_hour"));
		} catch (Exception e) {
			MODIFYSECRETQLIMIT_HOUR = 24;
		}
		try {
			MODIFYSECRETQLIMIT_NUMBER = Integer.parseInt(ConfigFileReader.p
					.getProperty("modifysecretqlimit_number"));
		} catch (Exception e) {
			MODIFYSECRETQLIMIT_NUMBER = 3;
		}
		try {
			AUTHCODE_WIDTH = Integer.parseInt(ConfigFileReader.p
					.getProperty("authcode_width"));
		} catch (Exception e) {
			AUTHCODE_WIDTH = 60;
		}
		try {
			AUTHCODE_HEIGHT = Integer.parseInt(ConfigFileReader.p
					.getProperty("authcode_height"));
		} catch (Exception e) {
			AUTHCODE_HEIGHT = 20;
		}
		try {
			REGISTER_IMSI_COUNT = Integer.parseInt(ConfigFileReader.p
					.getProperty("register_imsi_count"));
		} catch (Exception e) {
			REGISTER_IMSI_COUNT = 5;
		}
	}

	public static String FSM_CONTEXT_REQ = "REQUEST";
	public static String FSM_CONTEXT_REP = "RESPONSE";
	public static String SKY_AAA_ROLE_ADMIN = "admin";
	public static String SKY_AAA_ROLE_USER = "user";
	public static String SKY_AAA_KEY_ROLE = "role";
	public static String SKY_AAA_KEY_APPID = "appid";
	public static String SKY_AAA_KEY_IMSI = "imsi";
	public static String SKY_AAA_APP_APPLIST2 = "100";
	public static String SKY_AAA_APP_OSS = "120";
	public static String SKY_AAA_APP_READER3 = "200";
	public static String SKY_AAA_APP_GAMEHALL = "300";
	public static String SKY_AAA_APP_UNKNOWN = "999";
	public static String SKY_AAA_ACTION_LOGOUT = "logout";
	public static String SKY_AAA_ACTION_AUTH = "auth";
	public static String SKY_AAA_ACTION_REG = "reg";
	public static String SKY_AAA_ACTION_REGWITHMOBILE = "regwithmobile";
	public static String SKY_AAA_ACTION_CHECKNAME = "checkname";
	public static String SKY_AAA_ACTION_GETSECRETQ = "getsecretq";
	public static String SKY_AAA_ACTION_MODIFYPWD = "modifypwd";
	public static String SKY_AAA_ACTION_RESETPWD = "resetpwd";
	public static String SKY_AAA_ACTION_SETSECRETQ = "setsecretq";
	public static String SKY_AAA_ACTION_GETSKYID = "getskyid";
	public static String SKY_AAA_ACTION_GETUSERNAME = "getusername";
	public static String SKY_AAA_ACTION_SETPASSWD = "setpasswd";
	public static String SKY_AAA_ACTION_ACQUIRETOKEN = "acquiretoken";
	public static String SKY_AAA_ACTION_CHECKTOKEN = "checktoken";
	public static String SKY_AAA_ACTION_LOGIN = "login";
	public static String SKY_AAA_ACTION_GETAUTHCODE = "getauthcode";
	public static String SKY_AAA_ACTION_CHECKPWD = "checkpwd";
	public static String SKY_AAA_ACTION_GETUSERDOMAIN = "getuserdomain";
	public static String SKY_AAA_ACTION_APPJUMP = "appjump";
	public static String SKY_AAA_ACTION_MODIFYSECRETQ = "modifysecretq";
	public static String SKY_AAA_ACTION_GETONLINEIMSI = "getonlineimsi";
	public static String SKY_AAA_ACTION_GETONLINELIST = "getonlinelist";
	public static String SKY_AAA_ACTION_RESETPWDBYIMSI = "resetpwdbyimsi";
	public static String SKY_AAA_ACTION_JMX4LOCK = "jmx4lock";
	public static String SKY_AAA_ACTION_JMX4UNLOCK = "jmx4unlock";
	public static String SKY_AAA_ACTION_JMX4RESETPASSWD = "jmx4resetpasswd";
	public static String SKY_AAA_ACTION_JMX4CLEARPEC = "jmx4clearpec";
	public static String SKY_AAA_ACTION_JMX4DELSECRET = "jmx4delsecret";
	public static int SSIPEC_SUCCESS = 200;
	public static int SSIPEC_AAA_DB_BUSY = 110001;
	public static int SSIPEC_AAA_CACHE_BUSY = 110002;
	public static int SSIPEC_AAA_JMS_BUSY = 110003;
	public static int SSIPEC_AAA_SKYID_NOTEXISIT = 110101;
	public static int SSIPEC_AAA_USERNAME_NOTEXISIT = 110102;
	public static int SSIPEC_AAA_MOBILE_NOTEXISIT = 110103;
	public static int SSIPEC_AAA_EMAIL_NOTEXISIT = 110104;
	public static int SSIPEC_AAA_PASSWD_ERROR = 110105;
	public static int SSIPEC_AAA_MOBILE_ERROR = 110106;
	public static int SSIPEC_AAA_SECRET_ERROR = 110107;
	public static int SSIPEC_AAA_NOT_ONLINE = 110108;
	public static int SSIPEC_AAA_USERNAME_BLOCKED = 110109;
	public static int SSIPEC_AAA_USERNAME_EXISIT = 110110;
	public static int SSIPEC_AAA_PERMISSION_DENIED = 110111;
	public static int SSIPEC_AAA_USER_LOCKED = 110112;
	public static int SSIPEC_AAA_USERSTATE_ERROR = 110113;
	public static int SSIPEC_AAA_IMSIS_FREQUENT = 110114;
	public static int SSIPEC_AAA_TOKEN_ERROR = 110115;
	public static int SSIPEC_AAA_NOT_REGMOBILE = 110116;
	public static int SSIPEC_AAA_REGMOBILE_ERROR = 110117;
	public static int SSIPEC_AAA_SECRET_EMPTY = 110118;
	public static int SSIPEC_AAA_PASSWD_EMPTY = 110119;
	public static int SSIPEC_AAA_REGIMSI_ERROR = 110120;
	public static int SSIPEC_AAA_REGMOBILE_EXISIT = 110121;
	public static int SSIPEC_AAA_REGMOBILE_NOTEXISIT = 110116;
	public static int SSIPEC_AAA_SECRET_NOTEXISIT = 110123;
	public static int SSIPEC_AAA_SECRET_EXISIT = 110124;
	public static int SSIPEC_AAA_REGIMSI_NOTEXISIT = 110125;
	public static int SSIPEC_AAA_USERNAME_CANNOTUP = 110126;
	public static int SSIPEC_AAA_USERINFO_EMPTY = 110127;
	public static int SSIPEC_AAA_LOGIN_PROHIBIT = 110128;
	public static int SSIPEC_AAA_AUTHCODE_ERROR = 110129;
	public static int SSIPEC_AAA_RESETPWDLIMIT_EXTEND = 110130;
	public static int SSIPEC_AAA_SOURCE_DIFFERENT = 110131;
	public static int SSIPEC_AAA_REGISTER_IMSI_FULL = 110132;
	public static int SSIPEC_AAA_DOMAIN_NULL = 110133;
	public static int SSIPEC_AAA_IMSI_ILLEGALITY = 110134;
	public static int SSIPEC_AAA_MODIFYSECRETQLIMIT = 110135;
	public static int SSIPEC_AAA_MODIFYSECRETQ_ONLYMATCH = 110136;
	public static int SSIPEC_AAA_USERINFO_NOTRECEIVE = 110137;
	public static int SSIPEC_AAA_PARAMETER_INVALIDATION = 110201;
	public static int SSIPEC_AAA_DATA_INVALIDATION = 110202;
	public static int SSIPEC_AAA_BILLREG_ERROR = 110301;
	public static int SSIPEC_AAA_BILLREG_TIMEOUT = 110302;
	public static int SSIPEC_AAA_FUNCTION_UNIMPLEMENTED = 110998;
	public static int SSIPEC_AAA_UNKNOWN_ERROR = 110999;
	public static int SSIPEC_AAA_BILLREG_SUCCESS = 200;
	public static int SSIPEC_UPS_SUCCESS = 200;
	public static int SSIPEC_UPS_TIMEOUT = 110402;
	public static int SSIPEC_UPS_MOBILE_BINDED = 140104;
	public static int SSIPEC_AAA_REG_NOT_LOGIN = 0;
	public static int SSIPEC_AAA_REG_AND_LOGIN = 1;
	public static int SSIPEC_AAA_RECOMMEND_NO = 0;
	public static int SSIPEC_AAA_RECOMMEND_YES = 1;
	public static int SSIPEC_AAA_SECRETQTAG_NONE = 0;
	public static int SSIPEC_AAA_SECRETQTAG_ONLY_SECRETQ = 1;
	public static int SSIPEC_AAA_SECRETQTAG_ONLY_MOBILEBIND = 2;
	public static int SSIPEC_AAA_SECRETQTAG_BOTH = 3;
	public static final int SSIPEC_TYPE_SUCCESS = 0;
	public static final int SSIPEC_TYPE_SYSTEM = 1;
	public static final int SSIPEC_TYPE_LOGIC = 2;
	public static final int SSIPEC_TYPE_PARA = 3;
	public static final int SSIPEC_TYPE_NETWORK = 4;
	public static final int SSIPEC_TYPE_OTHER = 10;
	public static final int SSIPEC_TYPE = 16;
	public static final int SKY_BUSI_TYPE_REQUEST = 1;
	public static final int SKY_BUSI_TYPE_RESPONSE = 2;
	public static final int SKY_BUSI_TYPE_NOTIFIY = 3;
	public static final int SKY_BUSI_BASIC_VERSION = 1;
	public static final byte SKY_BUSI_TAG_EQUAL = 0;
	public static final byte SKY_BUSI_TAG_IMSI_DIFF = 1;
	public static final byte SKY_BUSI_TAG_IMEI_DIFF = 2;
	public static final byte SKY_BUSI_TAG_ALL_DIFF = 3;
	public static final int SKY_FSM_REGISTER = 1;
	public static final int SKY_FSM_CHECKNAME = 2;
	public static final int SKY_FSM_LOGIN = 3;
	public static final int SKY_FSM_AUTH = 4;
	public static final int SKY_FSM_LOGOUT = 5;
	public static final int SKY_FSM_GETSKYID = 15;
	public static final int SKY_FSM_GETUSERNAME = 16;
	public static final int SKY_FSM_DBACCESS = 30;
	public static final int SKY_FSM_LOGGER = 31;

	public static int GetECType(int ec) {
		if (ec == SSIPEC_SUCCESS) {
			return 0;
		}
		return (ec - 110000) / 100 + 1;
	}
}
