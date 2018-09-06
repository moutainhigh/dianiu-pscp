package com.edianniu.pscp.sps.bean;

/**
 * orderId生成规则前缀
 * ClassName: OrderIdPrefix
 * Author: tandingbo
 * CreateTime: 2017-05-18 17:09
 */
public class OrderIdPrefix {
    /**
     * 工单orderId_前缀
     */
    public static final String WORK_ORDER_PREFIX = "GD";
    /**
     * 电工工单orderId_前缀
     */
    public static final String ELECTRICIAN_WORK_ORDER_PREFIX = "EGD";
    /**
     * 电工工单checkOptionId_前缀
     */
    public static final String ELECTRICIAN_CHECK_OPTION_ID_PREFIX = "COI";
    /**
     * 社会工单orderId_前缀
     */
    public static final String SOCIAL_WORK_ORDER_PREFIX = "SGD";
    /**
     * 工单负责人电工工单checkOptionId
     */
    public static final String LEADER_ELECTRICIAN_CHECK_OPTION_ID = "LGD0000000001";
}
