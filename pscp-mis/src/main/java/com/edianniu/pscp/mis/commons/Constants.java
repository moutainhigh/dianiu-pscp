package com.edianniu.pscp.mis.commons;

public class Constants {
    public static final String FSM_CONTEXT_REQ = "REQUEST";
    public static final String FSM_CONTEXT_REP = "RESPONSE";

    public static final int GET_MSG_CODE_TYPE_REGISTER = 0;
    public static final int GET_MSG_CODE_TYPE_RESETPWD = 1;
    public static final int GET_MSG_CODE_TYPE_WITH_DRAWALS = 2;
    public static final int GET_MSG_CODE_TYPE_ADD_BANKCARD = 3;
    public static final int GET_MSG_CODE_TYPE_DEL_BANKCARD = 4;
    public static final int GET_MSG_CODE_TYPE_CHANGE_MOBILE = 5;
    public static final int GET_MSG_CODE_TYPE_SET_SWITCH_PWD = 6;

    public static final int TAG_NEED_DEPOSIT = 1;
    public static final int TAG_UN_NEED_DEPOSIT = 0;

    public static final Long FACILITATOR_ROLE_ID = 1010L;
    public static final Long ELECTRICIAN_ROLE_ID = 1011L;
    public static final Long CUSTOMER_ROLE_ID = 1000L;

    public static final int TAG_YES = 1;//是

    public static final int TAG_NO = 0;//否

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final int ELECTRICIAN_TYPE_COMPANY_ELECTRICIAN = 1;  //企业电工

    public static final int ELECTRICIAN_TYPE_UNBUNG_ELECTRICIAN = 0;   //已解绑的企业电工-->普通用户

    public static final int ELECTRICIAN_TYPE_SOCIAL_ELECTRICIAN = -1;  //社会电工


    //0：未开 1：已申请 2：已开票
    public static final int INVOICE_NOT_STATUS = 0;
    public static final int INVOICE_APPLY_STATUS = 1;
    public static final int INVOICE_FINISHED_STATUS = 2;
    
    public static final  int PRE_PAY=1;//预付费
	public static final  int MONTH_SETTLE=2;//月结算

}
