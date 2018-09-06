package com.edianniu.pscp.mis.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 需求订单状态：
 * -3: 取消
 * -2：不合作
 * -1：不符合（企业资质未通过客户审核）
 * 0：逻辑处理状态(数据库并无此状态值)
 * 1：已响应（客户尚未审核企业资质）（默认）
 * 2：待报价（符合、企业资质通过客户审核但还未报价）
 * 3：已报价（客户尚未审核报价）
 * 4：已合作
 *
 * @author zhoujianjian 2017年9月19日下午12:54:13
 */
public enum NeedsOrderStatus {
    CANCEL(-3, "取消"), NOT_COOPERATE(-2, "不合作"), NOT_QUALIFIED(-1, "不符合"), NOT_RESPONDING(0, "未响应"), RESPONED(1, "已响应"), WAITING_QUOTE(
            2, "待报价"), QUOTED(3, "已报价"), COOPERATED(4, "已合作");
    private int value;
    private String desc;

    NeedsOrderStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据需求订单状态获取状态名称
     *
     * @param value
     * @return
     */
    public static String getDesc(Integer value) {
        if (value == null) {
            return "未知";
        }

        NeedsOrderStatus[] values = NeedsOrderStatus.values();
        for (NeedsOrderStatus needsOrderStatus : values) {
            if (value.equals(needsOrderStatus.getValue())) {
                return needsOrderStatus.getDesc();
            }
        }
        return "未知";
    }

    /**
     * 构建需求订单状态数据(id->name)
     *
     * @return
     */
    public static Map<Integer, String> buildNeedsOrderStatusMap() {
        Map<Integer, String> map = new HashMap<>();
        NeedsOrderStatus[] values = NeedsOrderStatus.values();
        for (NeedsOrderStatus needsOrderStatus : values) {
            map.put(needsOrderStatus.getValue(), needsOrderStatus.getDesc());
        }
        return map;
    }

    /*
     * //不合作 public static final Integer NOT_COOPERATE = -2; //不符合 public static
     * final Integer NOT_QUALIFIED = -1; //已响应 public static final Integer
     * RESPONED = 0; //待报价 public static final Integer WAITING_QUOTE = 1; //
     * public static final Integer QUOTED = 2; //已合作 public static final Integer
     * COOPERATED = 3;
     */
    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
