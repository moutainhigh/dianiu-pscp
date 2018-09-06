package com.edianniu.pscp.sps.bean.workorder.chieforder;

import org.apache.commons.lang3.StringUtils;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户查询类型
 * @author zhoujianjian
 * @date 2018年4月25日 下午8:55:47
 */
public enum WorkOrderRequestTypeByCus {
    ONGOING("ongoing", "进行中") {
        @Override
        public Integer[] getStatus() {
        	Set<Integer> stateSet = new HashSet<>();
        	stateSet.add(WorkOrderStatus.UNCONFIRMED.getValue());
        	stateSet.add(WorkOrderStatus.CONFIRMED.getValue());
        	stateSet.add(WorkOrderStatus.EXECUTING.getValue());
        	stateSet.add(WorkOrderStatus.UN_EVALUATE.getValue());
            return stateSet.toArray(new Integer[]{});
        }
        
        @Override
        public WorkOrderStatus[] getWorkOrderStatus() {
        	Set<WorkOrderStatus> stateSet = new HashSet<>();
        	stateSet.add(WorkOrderStatus.UNCONFIRMED);
        	stateSet.add(WorkOrderStatus.CONFIRMED);
        	stateSet.add(WorkOrderStatus.EXECUTING);
        	stateSet.add(WorkOrderStatus.UN_EVALUATE);
            return stateSet.toArray(new WorkOrderStatus[]{});
        }
    },
    
    FINISHED("finished", "已结束") {
        @Override
        public Integer[] getStatus() {
            Set<Integer> stateSet = new HashSet<Integer>();
            stateSet.add(WorkOrderStatus.EVALUATED.getValue());
            return stateSet.toArray(new Integer[]{});
        }
        
        @Override
        public WorkOrderStatus[] getWorkOrderStatus() {
        	Set<WorkOrderStatus> stateSet = new HashSet<>();
        	stateSet.add(WorkOrderStatus.EVALUATED);
            return stateSet.toArray(new WorkOrderStatus[]{});
        }
    };

    private final String value;
    private final String name;

    WorkOrderRequestTypeByCus(String value, String name) {
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

    public WorkOrderStatus[] getWorkOrderStatus() {
		return new WorkOrderStatus[]{};
	}
    
    /**
     * 判断当前分组是否存在某一WokOrderStatus
     * @param workOrderStatusName
     * @return
     */
    public boolean isWokOrderStatusExist(String workOrderStatusName){
    	if (StringUtils.isBlank(workOrderStatusName)) {
			return false;
		}
    	WorkOrderStatus[] workOrderStatusArray = getWorkOrderStatus();
    	for (WorkOrderStatus workOrderStatus : workOrderStatusArray) {
			if (workOrderStatus.getName().equals(workOrderStatusName)) {
				return true;
			}
		}
    	return false;
    }
    
    /**
     * 根据值获取Enum对象
     * @param value
     * @return
     */
    public static WorkOrderRequestTypeByCus get(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        WorkOrderRequestTypeByCus[] values = WorkOrderRequestTypeByCus.values();
        for (WorkOrderRequestTypeByCus workOrderRequestType : values) {
            if (value.equals(workOrderRequestType.getValue())) {
                return workOrderRequestType;
            }
        }
        return null;
    }

    /**
     * 判断值是否存在
     * @param value
     * @return
     */
    public static Boolean isExist(String value) {
        for (WorkOrderRequestTypeByCus workOrderRequestType : WorkOrderRequestTypeByCus.values()) {
            if (value.equals(workOrderRequestType.getValue())) {
                return true;
            }
        }
        return false;
    }
    
}
