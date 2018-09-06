package com.edianniu.pscp.sps.bean.socialworkorder;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SocialWorkOrderRequestType
 * Author: tandingbo
 * CreateTime: 2017-05-23 09:36
 */
public enum SocialWorkOrderRequestType {
    UN_PUBLISH("unpublish", "未发布") {
        @Override
        public Integer[] getStatus() {
            return new Integer[]{SocialWorkOrderStatus.UN_PUBLISH.getValue()};
        }
    },
    RECRUITING("recruiting", "招募中") {
        @Override
        public Integer[] getStatus() {
            return new Integer[]{SocialWorkOrderStatus.RECRUITING.getValue()};
        }
    },
    RECRUIT_END("recruitend", "招募结束") {
        @Override
        public Integer[] getStatus() {
            return new Integer[]{SocialWorkOrderStatus.RECRUIT_END.getValue()};
        }
    },
    FINISHED("finished", "已完成") {
        @Override
        public Integer[] getStatus() {
            List<Integer> statusList = new ArrayList<>();
            statusList.add(SocialWorkOrderStatus.CANCEL.getValue());
            // FIXME 少社会工单超时状态
            statusList.add(SocialWorkOrderStatus.FINISHED.getValue());
            return statusList.toArray(new Integer[]{});
        }
    },
    LIQUIDATED("liquidated", "已结算") {
        @Override
        public Integer[] getStatus() {
            return new Integer[]{SocialWorkOrderStatus.LIQUIDATED.getValue()};
        }
    };

    private final String value;
    private final String name;

    SocialWorkOrderRequestType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Integer[] getStatus() {
        return new Integer[]{};
    }

    /**
     * 根据值获取Enum对象
     *
     * @param value
     * @return
     */
    public static SocialWorkOrderRequestType get(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        SocialWorkOrderRequestType[] values = SocialWorkOrderRequestType.values();
        for (SocialWorkOrderRequestType socialWorkOrderRequestType : values) {
            if (value.equals(socialWorkOrderRequestType.getValue())) {
                return socialWorkOrderRequestType;
            }
        }
        return null;
    }

    /**
     * 判断值是否存在
     *
     * @param value
     * @return
     */
    public static Boolean isExist(String value) {
        SocialWorkOrderRequestType[] values = SocialWorkOrderRequestType.values();
        for (SocialWorkOrderRequestType socialWorkOrderRequestType : values) {
            if (value.equals(socialWorkOrderRequestType.getValue())) {
                return true;
            }
        }
        return false;
    }

}
