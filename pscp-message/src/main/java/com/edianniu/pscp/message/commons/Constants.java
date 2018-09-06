package com.edianniu.pscp.message.commons;

public class Constants {
    public static final String FSM_CONTEXT_REQ = "REQUEST";
    public static final String FSM_CONTEXT_REP = "RESPONSE";

    public static final int GET_MSG_CODE_TYPE_REGISTER = 0;
    public static final int GET_MSG_CODE_TYPE_RESETPWD = 1;
    public static final int GET_MSG_CODE_TYPE_WITH_DRAWALS = 2;
    public static final int GET_MSG_CODE_TYPE_ADD_BANKCARD = 3;
    public static final int GET_MSG_CODE_TYPE_DEL_BANKCARD = 4;

    public static final int TAG_NEED_DEPOSIT = 1;
    public static final int TAG_UN_NEED_DEPOSIT = 0;

    public static final int TAG_YES = 1;//是

    public static final int TAG_NO = 0;//否

    public static final int DEFAULT_PAGE_SIZE = 10;
}
