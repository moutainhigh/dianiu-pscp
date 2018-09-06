package com.edianniu.pscp.cs.bean.workorder;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: WorkOrderRequestType
 * Author: tandingbo
 * CreateTime: 2017-05-10 17:14
 */
public enum WorkOrderRequestType {
    UNDERWAY("underway", "进行中") {
        @Override
        public Integer[] getStatus() {
            Set<Integer> stateSet = new HashSet<Integer>();
            stateSet.add(WorkOrderStatus.UNCONFIRMED.getValue());
            stateSet.add(WorkOrderStatus.CONFIRMED.getValue());
            stateSet.add(WorkOrderStatus.EXECUTING.getValue());
            stateSet.add(WorkOrderStatus.UN_EVALUATE.getValue());
            return stateSet.toArray(new Integer[]{});
        }
    },
    FINISHED("finished", "已完成") {
        @Override
        public Integer[] getStatus() {
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

    public Integer[] getStatus() {
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
