package com.edianniu.pscp.sps.bean.workorder.chieforder;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: WorkOrderRequestType
 * Author: tandingbo
 * CreateTime: 2017-05-10 17:14
 */
public enum WorkOrderRequestType {
    UN_CONFIRM("unconfirm", "未确认") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            return new Integer[]{WorkOrderStatus.UNCONFIRMED.getValue()};
        }
    },
    CONFIRMED("confirmed", "已确认") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            return new Integer[]{WorkOrderStatus.CONFIRMED.getValue()};
        }
    },
    STARTED("started", "已开始") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            Set<Integer> stateSet = new HashSet<Integer>();
            stateSet.add(WorkOrderStatus.EXECUTING.getValue());
            stateSet.add(WorkOrderStatus.UN_EVALUATE.getValue());
            return stateSet.toArray(new Integer[]{});
        }
    },
    COMPLETED("completed", "已完成") {
        @Override
        public Integer[] getStatus(boolean isCompany) {
            Set<Integer> stateSet = new HashSet<Integer>();
            stateSet.add(WorkOrderStatus.CANCELED.getValue());
            stateSet.add(WorkOrderStatus.EVALUATED.getValue());
            return stateSet.toArray(new Integer[]{});
        }
    };

    private final String value;
    private final String name;

    WorkOrderRequestType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Integer[] getStatus(boolean isCompany) {
        return new Integer[]{};
    }

    /**
     * 根据值获取Enum对象
     *
     * @param value
     * @return
     */
    public static WorkOrderRequestType get(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        WorkOrderRequestType[] values = WorkOrderRequestType.values();
        for (WorkOrderRequestType workOrderRequestType : values) {
            if (value.equals(workOrderRequestType.getValue())) {
                return workOrderRequestType;
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
        for (WorkOrderRequestType workOrderRequestType : WorkOrderRequestType.values()) {
            if (value.equals(workOrderRequestType.getValue())) {
                return true;
            }
        }
        return false;
    }
}
