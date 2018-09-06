package com.edianniu.pscp.sps.bean.socialworkorder;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: SocialWorkOrderStatus
 * Author: tandingbo
 * CreateTime: 2017-05-18 15:29
 */
public enum SocialWorkOrderStatus {
    UN_PUBLISH(0, "未发布"),
    RECRUITING(1, "招募中"),
    RECRUIT_END(2, "招募结束"),
    FINISHED(3, "已完成"),
    LIQUIDATED(4, "已结算"),
    CANCEL(-1, "取消");
    // FIXME 少社会工单超时状态

    private final Integer value;
    private final String name;

    SocialWorkOrderStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getNameByKey(String valueStr) {
        if (StringUtils.isBlank(valueStr)) {
            return "";
        }
        Integer value = Integer.valueOf(valueStr);
        SocialWorkOrderStatus[] values = SocialWorkOrderStatus.values();
        for (SocialWorkOrderStatus socialWorkOrderStatus : values) {
            if (value.equals(socialWorkOrderStatus.getValue())) {
                return socialWorkOrderStatus.getName();
            }
        }
        return "";
    }

    public static Map<Integer, String> getAllStatus() {
        Map<Integer, String> result = new HashMap<>();
        SocialWorkOrderStatus[] values = SocialWorkOrderStatus.values();
        for (SocialWorkOrderStatus socialWorkOrderStatus : values) {
            result.put(socialWorkOrderStatus.getValue(), socialWorkOrderStatus.getName());
        }
        return result;
    }

}
