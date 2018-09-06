package com.edianniu.pscp.mis.bean.workorder;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: WorkOrderType
 * Author: tandingbo
 * CreateTime: 2017-09-14 11:14
 */
public enum WorkOrderType {
    OVERHAUL(1, "检修工单"),
    INSPECTION(2, "巡检工单"),
    PROSPECTING(3, "现场勘查");

    WorkOrderType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据工单类型获取类型名称
     *
     * @param value
     * @return
     */
    public static String getDesc(Integer value) {
        if (value == null) {
            return "未知";
        }

        WorkOrderType[] values = WorkOrderType.values();
        for (WorkOrderType workOrderType : values) {
            if (value.equals(workOrderType.getValue())) {
                return workOrderType.getDesc();
            }
        }
        return "未知";
    }

    /**
     * 构建工单类型列表数据
     *
     * @return
     */
    public static List<WorkOrderTypeInfo> buildWorkOrderTypeList() {
        List<WorkOrderTypeInfo> list = new ArrayList<>();
        WorkOrderType[] values = WorkOrderType.values();
        for (WorkOrderType workOrderType : values) {
            WorkOrderTypeInfo workOrderTypeInfo = new WorkOrderTypeInfo();
            workOrderTypeInfo.setId(workOrderType.getValue());
            workOrderTypeInfo.setName(workOrderType.getDesc());
            list.add(workOrderTypeInfo);
        }
        return list;
    }

    private int value;
    private String desc;

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
