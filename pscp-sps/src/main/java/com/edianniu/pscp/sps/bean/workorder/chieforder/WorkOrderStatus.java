package com.edianniu.pscp.sps.bean.workorder.chieforder;

/**
 * ClassName: WorkOrderStatus
 * Author: tandingbo
 * CreateTime: 2017-05-10 17:15
 */
public enum WorkOrderStatus {
    CANCELED(-1, "已取消"),
    UNCONFIRMED(0, "未确认"),
    CONFIRMED(1, "已确认"),
    EXECUTING(2, "进行中"),
    UN_EVALUATE(3, "未评价"),
    EVALUATED(4, "已评价");

    private final Integer value;
    private final String name;

    WorkOrderStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    
    public static WorkOrderStatus getWokOrderStatus(String name) {
    	WorkOrderStatus[] workOrderStatusArray = WorkOrderStatus.values();
    	for (WorkOrderStatus workOrderStatus : workOrderStatusArray) {
			if (workOrderStatus.getName().equals(name)) {
				return workOrderStatus;
			}
		}
    	return WorkOrderStatus.UNCONFIRMED;
    }
}
